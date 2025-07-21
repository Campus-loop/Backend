package loopcampus.com.backend.entity.chat;


import jakarta.persistence.*;
import loopcampus.com.backend.entity.audit.Period;

@Table(name = "chatRoom")
@Entity
public class ChatRoom extends Period {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
