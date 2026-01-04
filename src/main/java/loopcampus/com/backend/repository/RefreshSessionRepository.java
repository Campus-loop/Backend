package loopcampus.com.backend.repository;

import loopcampus.com.backend.entity.member.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshSessionRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByRefreshTokenHashAndRevokedAtIsNull(String refreshTokenHash);

}
