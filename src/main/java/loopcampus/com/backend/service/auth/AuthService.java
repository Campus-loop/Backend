package loopcampus.com.backend.service.auth;

import loopcampus.com.backend.domain.MemberRole;
import loopcampus.com.backend.dto.auth.request.LoginRequest;
import loopcampus.com.backend.dto.auth.request.RegisterRequest;
import loopcampus.com.backend.dto.auth.response.LoginResponse;
import loopcampus.com.backend.dto.auth.response.RefreshResponse;
import loopcampus.com.backend.dto.auth.response.RegisterResponse;
import loopcampus.com.backend.dto.auth.response.MemberSummary;
import loopcampus.com.backend.dto.common.ApiFieldError;
import loopcampus.com.backend.entity.member.Member;
import loopcampus.com.backend.entity.member.RefreshSession;
import loopcampus.com.backend.error.ApiException;
import loopcampus.com.backend.error.ErrorCode;
import loopcampus.com.backend.repository.MemberRepository;
import loopcampus.com.backend.repository.RefreshSessionRepository;
import loopcampus.com.backend.security.JwtProvider;
import loopcampus.com.backend.security.RefreshTokenManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class AuthService {

    private final MemberRepository mbmerRepo;
    private final RefreshSessionRepository sessionRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RefreshTokenManager refreshTokenManager;
    private final long refreshExpSeconds;
    private final MemberRepository memberRepository;

    public AuthService(
            MemberRepository memberRepo,
            RefreshSessionRepository sessionRepo,
            PasswordEncoder passwordEncoder,
            JwtProvider jwtProvider,
            RefreshTokenManager refreshTokenManager,
            @Value("${app.jwt.refresh-exp-seconds}") long refreshExpSeconds,
            MemberRepository memberRepository) {
        this.mbmerRepo = memberRepo;
        this.sessionRepo = sessionRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.refreshTokenManager = refreshTokenManager;
        this.refreshExpSeconds = refreshExpSeconds;
        this.memberRepository = memberRepository;
    }

    public RegisterResponse register(RegisterRequest req) throws ApiException {
        if (!req.password().equals(req.passwordConfirm())) {
            throw new ApiException(
                    ErrorCode.VALIDATION_FAILED,
                    "Invalid request.",
                    List.of(new ApiFieldError("passwordConfirm", "Does not match password"))
            );
        }

        if (memberRepository.findByEmail(req.email()).isPresent()) {
            throw new ApiException(ErrorCode.USER_EMAIL_ALREADY_EXISTS);
        }

        Member member = new Member();
        member.setEmail(req.email());
        member.setPassword(passwordEncoder.encode(req.password()));
        member.setRole(MemberRole.Role_Member);
        memberRepository.save(member);

        return new RegisterResponse(member.getId(), member.getEmail(), member.isEmailVerified());
    }

    public LoginResult login(LoginRequest req) {
        Member member = memberRepository.findByEmail(req.email())
                .orElseThrow(() -> new ApiException(ErrorCode.AUTH_INVALID_CREDENTIALS));

        if (!passwordEncoder.matches(req.password(), member.getPassword())) {
            throw new ApiException(ErrorCode.AUTH_INVALID_CREDENTIALS);
        }

        String accessToken = jwtProvider.createAccessToken(member);

        String refreshRaw = refreshTokenManager.newToken();
        String refreshHash = refreshTokenManager.hash(refreshRaw);

        RefreshSession session = new RefreshSession();
        session.setMember(member);
        session.setRefreshTokenHash(refreshHash);
        session.setExpiresAt(Instant.now().plusSeconds(refreshExpSeconds));
        sessionRepo.save(session);

        var summary = new MemberSummary(member.getId(), member.getEmail(), member.getRole());
        return new LoginResult(new LoginResponse(accessToken, summary), refreshRaw);
    }

    public RefreshResponse refresh(String refreshRaw) {
        String hash = refreshTokenManager.hash(refreshRaw);

        RefreshSession session = sessionRepo.findByRefreshTokenHashAndRevokedAtIsNull(hash)
                .orElseThrow(() -> new ApiException(ErrorCode.AUTH_REFRESH_INVALID));

        if (session.isExpired()) {
            session.setRevokedAt(Instant.now());
            sessionRepo.save(session);
            throw new ApiException(ErrorCode.AUTH_REFRESH_EXPIRED);
        }

        Member member = session.getMember();
        String newAccess = jwtProvider.createAccessToken(member);

        // TODO (권장): refresh rotation
        // - session revoke + 새 refresh 발급 + 쿠키 교체

        return new RefreshResponse(newAccess);
    }

    public void logout(String refreshRaw) {
        String hash = refreshTokenManager.hash(refreshRaw);
        sessionRepo.findByRefreshTokenHashAndRevokedAtIsNull(hash).ifPresent(s -> {
            s.setRevokedAt(Instant.now());
            sessionRepo.save(s);
        });
    }

    public record LoginResult(LoginResponse body, String refreshRaw) {}
}
