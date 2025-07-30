package loopcampus.com.backend.entity.chat;


import jakarta.persistence.*;
import loopcampus.com.backend.entity.audit.Period;

import java.util.List;

@Table(name = "chatRoom")
@Entity
public class ChatRoom extends Period {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    room name
    private String name;

    @OneToMany(fetch = FetchType.LAZY)
    private List<ChatMessage> messages;

    @OneToMany(fetch = FetchType.LAZY)
    private List<ChatParticipant> participants;
}
