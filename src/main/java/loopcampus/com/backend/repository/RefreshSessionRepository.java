package loopcampus.com.backend.repository;

import loopcampus.com.backend.entity.member.RefreshSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshSessionRepository extends JpaRepository<RefreshSession, Long> {
    Optional<RefreshSession> findByRefreshTokenHashAndRevokedAtIsNull(String refreshTokenHash);

}
