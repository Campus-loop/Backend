package loopcampus.com.backend.entity.audit;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class Period {
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private LocalDate deletedAt;
    private boolean isDeleted;

}
