package loopcampus.com.backend.entity.rent;


import jakarta.persistence.*;

@Entity
@Table(name = "rent")
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


}
