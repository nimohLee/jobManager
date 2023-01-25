package com.nimoh.jobManager.data.dto.job;
import com.nimoh.jobManager.data.entity.Skill;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    private String salary;

    private LocalDate applyDate;

    private String link;

    private String requiredCareer;

    private String result;

    private List<Skill> primarySkill;

    private String note;
}