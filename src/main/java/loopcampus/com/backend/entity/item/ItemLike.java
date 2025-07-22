package loopcampus.com.backend.entity.item;


import jakarta.persistence.*;
import loopcampus.com.backend.entity.audit.Period;
import loopcampus.com.backend.entity.member.Member;

@Entity
@Table(name = "itemLike", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"member_id", "used_item_id"})
})
public class ItemLike extends Period {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private UsedItem usedItem;


}
