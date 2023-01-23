package com.nimoh.jobManager.data.dto.job;

import com.nimoh.jobManager.data.entity.Skill;
import com.nimoh.jobManager.data.entity.User;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobResponse {

    private Long id;

    private String companyName;

    private Integer employeesNumber;

    private String huntingSite;

    private String location;

    private String position;

    private String requiredCareer;

    private List<Skill> primarySkill = new ArrayList<>();

    private LocalDate applyDate;

    private String result;

    private String note;

}
