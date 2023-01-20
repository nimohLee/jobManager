package com.nimoh.hotel.data.entity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table
@DynamicUpdate
public class Board extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title",nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @Column(name = "content",nullable = false)
    private String content;

    @Column(name = "category",nullable = false)
    private String category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}