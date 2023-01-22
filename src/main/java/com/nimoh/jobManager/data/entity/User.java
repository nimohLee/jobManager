package com.nimoh.jobManager.data.entity;

import lombok.*;


import javax.persistence.*;


@Entity
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Table
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String uid;

    private String name;

    private String password;

    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}