package loopcampus.com.backend.entity.chat;

import jakarta.persistence.*;
import loopcampus.com.backend.entity.audit.Period;

@Table(name = "checkReadStatus")
@Entity
public class CheckReadStatus extends Period {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    private ChatMessage message;


}
