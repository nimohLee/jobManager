package com.nimoh.jobManager.repository.job;

import com.nimoh.jobManager.commons.enums.JobSearchSite;
import com.nimoh.jobManager.commons.enums.RequiredExperience;
import com.nimoh.jobManager.commons.enums.Result;
import com.nimoh.jobManager.data.dto.job.JobSearchCondition;
import com.nimoh.jobManager.data.entity.Job;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.nimoh.jobManager.data.entity.QJob.*;

@Repository
@RequiredArgsConstructor
public class JobRepositoryCustomImpl implements JobRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Job> findByCond(Long userId, JobSearchCondition cond) {
        return queryFactory.select(job)
                .from(job)
                .where(
                        job.user.id.eq(userId),
                        likeJobName(cond.getName()),
                        resultIs(cond.getResult()),
                        requiredExperienceIs(cond.getRequiredExperience()),
                        salaryBetween(cond.getMinSalary(), cond.getMaxSalary()),
                        locationIs(cond.getLocation()),
                        jobSearchSiteIs(cond.getJobSearchSite())
                ).fetch();
    }

    private BooleanExpression likeJobName(String name) {
        if (name != null) {
            return job.companyName.contains(name);
        }
        return null;
    }

    private BooleanExpression resultIs(Result result) {
        if (result != null) {
            return job.result.eq(result.getResultName());
        }
        return null;
    }

    private BooleanExpression requiredExperienceIs(RequiredExperience requiredExperience) {
        if (requiredExperience != null) {
            return job.requiredCareer.eq(requiredExperience.getCareer());
        }
        return null;
    }

    private BooleanExpression salaryBetween(Integer minSalary, Integer maxSalary) {
        if (minSalary != null && maxSalary != null) {
            return job.salary.between(minSalary, maxSalary);
        }
        return null;
    }

    private BooleanExpression locationIs(String location) {
        if (location != null) {
            return job.location.contains(location);
        }
        return null;
    }

    private BooleanExpression jobSearchSiteIs(JobSearchSite jobSearchSite) {
        if (jobSearchSite != null) {
            return job.huntingSite.eq(jobSearchSite.getName());
        }
        return null;
    }
}
