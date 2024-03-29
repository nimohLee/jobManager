package com.nimoh.jobManager.data.entity;

import lombok.*;

import javax.persistence.*;

/**
 * 기술 관련 엔티티
 *
 * @author nimoh
 */
@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter(AccessLevel.NONE)
public class Skill{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

}
