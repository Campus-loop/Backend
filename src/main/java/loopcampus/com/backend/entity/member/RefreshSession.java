package loopcampus.com.backend.entity.member;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;


@Getter
@Setter
@Entity
public class RefreshSession {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false, fetch = FetchType.LAZY)
    private Member member;

    @Column(nullable=false)
    private String refreshTokenHash;

    @Column(nullable=false)
    private Instant expiresAt;

    private Instant revokedAt;

    public boolean isRevoked() { return revokedAt != null; }
    public boolean isExpired() { return Instant.now().isAfter(expiresAt); }


}
