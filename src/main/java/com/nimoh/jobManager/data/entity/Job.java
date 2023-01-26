package com.nimoh.jobManager.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicUpdate
public class Job extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "company_name")
    private String companyName;

    private String huntingSite;

    private String position;

    @Column(name = "employees_number")
    private Integer employeesNumber;

    private String location;

    private String x;

    private String y;

    private String salary;

    @Column(name = "apply_date")
    private LocalDate applyDate;

    private String link;

    @Column(name = "required_career")
    private String requiredCareer;

    private String result;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "primary_skill_id")
    private List<Skill> primarySkill = new ArrayList<>();

    private String note;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
