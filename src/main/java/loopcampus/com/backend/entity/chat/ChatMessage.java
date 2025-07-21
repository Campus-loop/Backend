package loopcampus.com.backend.entity.chat;


import jakarta.persistence.*;
import loopcampus.com.backend.entity.audit.Period;

@Entity
@Table(name = "chatMessage")
public class ChatMessage extends Period {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;
}
