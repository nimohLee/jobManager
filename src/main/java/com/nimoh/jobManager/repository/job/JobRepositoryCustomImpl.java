package com.nimoh.jobManager.repository.job;

import com.nimoh.jobManager.commons.enums.Result;
import com.nimoh.jobManager.data.dto.job.JobSearchCondition;
import com.nimoh.jobManager.data.entity.Job;
import com.nimoh.jobManager.data.entity.QJob;
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
    public List<Job> findByCond(JobSearchCondition cond) {
        return queryFactory.select(job)
                .from(job)
                .where(
                        likeJobName(cond.getName()),
                        resultIs(cond.getResult())
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
}
