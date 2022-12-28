package com.nimoh.hotel.domain;
import lombok.Builder;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)

    private Long id;
    private String title;
    private String writer;
    private String content;
    private String category;

    @Column(name = "reg_date")
    private Date regDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}