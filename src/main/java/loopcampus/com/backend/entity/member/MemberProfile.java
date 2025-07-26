package loopcampus.com.backend.entity.member;


import jakarta.persistence.*;
import loopcampus.com.backend.domain.FileType;
import loopcampus.com.backend.entity.audit.Period;


@Table(name = "memberProfile")
@Entity
public class MemberProfile extends Period {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileOriginalName;
    private String fileUUID;
    private String filePath;

    @Enumerated(EnumType.STRING)
    private FileType fileType;

    @OneToOne(fetch = FetchType.LAZY)
    private Member member;

}
