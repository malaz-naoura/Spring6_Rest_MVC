package mezo.restmvc.spring_6_rest_mvc.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    protected UUID id;

    @Version
    protected Integer version;

    @CreationTimestamp
    @Column(updatable = false)
    protected LocalDateTime createdDate;

    @UpdateTimestamp
    protected LocalDateTime updateDate;

    protected BaseEntity(final BaseEntityBuilder<?, ?> b) {
        this.id = b.id;
        this.version = b.version;
        this.createdDate = b.createdDate;
        this.updateDate = b.updateDate;
    }



}
