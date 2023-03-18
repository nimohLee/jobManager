package com.nimoh.jobManager.data.dto.job;

import com.nimoh.jobManager.data.entity.Skill;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

/**
 * 지원 내역 등록 시 DTO
 *
 * @author nimoh
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobRequest {

    private String companyName;

    private String huntingSite;

    private String position;

    private Integer employeesNumber;

    private String location;

    private String x;

    private String y;

    private String salary;

    private LocalDate applyDate;

    private String link;

    private String requiredCareer;

    private String result;

    private List<Skill> primarySkill;

    private String note;

    public void setX(String x) {
        this.x = x;
    }

    public void setY(String y) {
        this.y = y;
    }
}