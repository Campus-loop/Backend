package loopcampus.com.backend.entity.member;


import jakarta.persistence.*;

@Entity
public class RefreshSession {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Member member;

    @Column(nullable = false)
    private String refreshToken;

    @Column(nullable = false)
    private String refreshTokenExpiresAt;



}
