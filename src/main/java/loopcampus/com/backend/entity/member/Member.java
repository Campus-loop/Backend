package loopcampus.com.backend.entity.member;


import jakarta.persistence.*;
import lombok.*;
import loopcampus.com.backend.entity.audit.Period;
import loopcampus.com.backend.entity.item.ItemLike;
import loopcampus.com.backend.entity.item.UsedItem;

import java.util.List;

@Entity
@Table(name = "member")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member extends Period {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    private String state;
    private String city;
    private String zipcode;
    private String addressLine;

    private String temperature;

//    if user deleted account
    private boolean isDeleted = false;

    @OneToOne(cascade = CascadeType.ALL)
    private MemberProfile memberProfile;

    @OneToOne(cascade = CascadeType.ALL)
    private PasswordToken passwordToken;

    @OneToMany(fetch = FetchType.LAZY)
    private List<UsedItem> usedItems;

    @OneToMany(fetch = FetchType.LAZY)
    private List<ItemLike> itemLikes;

    @OneToOne(mappedBy = "member")
    private MemberProfile profileImage;


}
