package loopcampus.com.backend.entity.chat;


import jakarta.persistence.*;
import loopcampus.com.backend.entity.audit.Period;

@Entity
@Table(name = "chatParticipant")
public class ChatParticipant extends Period {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
