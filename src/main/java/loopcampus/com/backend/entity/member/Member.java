package loopcampus.com.backend.entity.member;


import jakarta.persistence.*;
import lombok.*;
import loopcampus.com.backend.entity.item.UsedItem;

import java.util.List;

@Entity
@Table(name = "member")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member {

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



}
