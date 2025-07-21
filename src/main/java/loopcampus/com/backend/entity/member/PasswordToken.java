package loopcampus.com.backend.entity.member;


import jakarta.persistence.*;
import loopcampus.com.backend.entity.audit.Period;

import java.time.LocalDateTime;


@Table(name = "passwordToken")
@Entity
public class PasswordToken extends Period {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;
    private LocalDateTime expiresAt;
    private LocalDateTime createdAt;

    @OneToOne
    private Member member;
}
