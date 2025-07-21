package loopcampus.com.backend.entity.item;


import jakarta.persistence.*;
import loopcampus.com.backend.entity.file.File;

@Entity
@Table(name = "itemPicture")
public class ItemPicture extends File{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
