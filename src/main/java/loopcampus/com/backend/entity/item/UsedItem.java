package loopcampus.com.backend.entity.item;


import jakarta.persistence.*;
import loopcampus.com.backend.domain.Category;
import loopcampus.com.backend.entity.audit.Period;
import loopcampus.com.backend.entity.file.File;

import java.util.List;

@Entity
@Table(name = "usedItem")
public class UsedItem extends Period {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    stars for people
    private Double temperature;

//    default - etc
    @Enumerated(EnumType.STRING)
    private Category category = Category.ETC;

    private String title;

    @Lob
    private String description;

    private Integer price;

    private Long views;

    private Long likes;
    private Long chatCount;

    private String state;
    private String city;
    private String zipcode;
    private String addressLine;

    @OneToMany(fetch = FetchType.LAZY)
    private List<ItemPicture> itemPictures;



}
