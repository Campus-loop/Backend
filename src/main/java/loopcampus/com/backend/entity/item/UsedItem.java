package loopcampus.com.backend.entity.item;


import jakarta.persistence.*;
import loopcampus.com.backend.entity.audit.Period;
import loopcampus.com.backend.entity.file.File;

import java.util.List;

@Entity
@Table(name = "usedItem")
public class UsedItem extends Period {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY)
    private List<ItemPicture> itemPictures;

}
