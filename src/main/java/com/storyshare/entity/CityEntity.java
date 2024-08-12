package com.storyshare.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cities")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    String name;
    Boolean parentCity;
    Integer storyCount;
    Integer subCityCount;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    CityEntity parent;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    List<Translation> translations;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @PrePersist
    protected void autoFill() {
        this.storyCount = 0;
        this.subCityCount = 0;
        this.parent = this.parent.getId() == null ? null : this.parent;
        this.createdAt = LocalDateTime.now();
    }
}
