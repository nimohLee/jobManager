package com.nimoh.jobManager.controller;

import com.nimoh.jobManager.commons.enums.JobSearchSite;
import com.nimoh.jobManager.commons.enums.RequiredExperience;
import com.nimoh.jobManager.commons.enums.Result;
import com.nimoh.jobManager.data.dto.job.JobResponse;
import com.nimoh.jobManager.data.dto.job.JobRequest;
import com.nimoh.jobManager.data.dto.job.JobSearchCondition;
import com.nimoh.jobManager.data.entity.User;
import com.nimoh.jobManager.service.api.RestTemplateService;
import com.nimoh.jobManager.service.job.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 직무 지원 컨트롤러
 *
 * @author nimoh
 */
@RestController
@RequestMapping("/api/v1/job")
@RequiredArgsConstructor
public class JobController {
    private final JobService jobService;
    private final RestTemplateService restTemplateService;

    /**
     * 직무 지원 목록 리턴
     *
     * @return 현재 유저의 직무 지원 목록
     */
    @Operation(summary = "직무 지원 목록 조회", description = "직무 지원 목록을 모두 조회합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "직무 지원 목록 조회 성공"),
            @ApiResponse(responseCode = "204", description = "직무 지원내역이 없습니다"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다")
    })
    @GetMapping("")
    public ResponseEntity<List<JobResponse>> getList(
            @AuthenticationPrincipal User user
    ) {
        Long userId = user.getId();
        List<JobResponse> result = jobService.findByUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     *
     * @param user 현재 사용자
     * @param name 검색어 (회사명)
     * @param applyResult 검색 조건 (지원결과)
     * @param requiredExperience 검색 조건 (요구 경력)
     * @param minSalary 검색 조건 (최소 연봉)
     * @param maxSalary 검색 조건 (최대 연봉)
     * @param location 검색 조건 (회사 위치)
     * @param jobSearchSite 검색 조건 (구직 사이트)
     * @return 검색 조건에 따른 지원 내역 반환
     */
    @Operation(summary = "직무 지원 목록 조건 별 조회", description = "직무 지원 목록을 조건 별로 모두 조회합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "직무 지원 목록 조회 성공"),
            @ApiResponse(responseCode = "204", description = "직무 지원내역이 없습니다"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다"),
            @ApiResponse(responseCode = "500", description = "알 수 없는 에러가 발생했습니다.")
    })
    @GetMapping("/cond")
    public ResponseEntity<List<JobResponse>> getListByCond(
            @AuthenticationPrincipal User user,
            @RequestParam(value = "name",required = false) String name,
            @RequestParam(value = "result",required = false) String applyResult,
            @RequestParam(value = "requiredExperience",required = false) String requiredExperience,
            @RequestParam(value = "minSalary",required = false) Integer minSalary,
            @RequestParam(value = "maxSalary",required = false) Integer maxSalary,
            @RequestParam(value = "location",required = false) String location,
            @RequestParam(value = "jobSearchSite",required = false) String jobSearchSite
    ) {
        JobSearchCondition cond = JobSearchCondition.builder()
                .name(name)
                .result(applyResult!=null?Result.valueOf(applyResult):null)
                .requiredExperience(requiredExperience!=null?RequiredExperience.valueOf(requiredExperience):null)
                .minSalary(minSalary)
                .maxSalary(maxSalary)
                .location(location)
                .jobSearchSite(jobSearchSite!=null?JobSearchSite.valueOf(jobSearchSite):null)
                .build();
        Long userId = user.getId();
        List<JobResponse> findResult = jobService.findByCond(userId, cond);
        return ResponseEntity.status(HttpStatus.OK).body(findResult);
    }


    /**
     * 직무 지원 등록
     *
     * @param jobRequest 지원 내역 등록 요청 DTO
     * @return 성공 시 Http Status code 201
     */
    @Operation(summary = "직무 지원 등록", description = "직무 지원을 작성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "직무 지원 등록 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다")
    })
    @PostMapping("")
    public ResponseEntity<Void> save(
            @RequestBody final JobRequest jobRequest,
            @AuthenticationPrincipal User user
    ) {
        Map<String, String> geocode = restTemplateService.getGeocode(jobRequest.getLocation());
        jobRequest.setX(geocode.get("x"));
        jobRequest.setY(geocode.get("y"));
        jobService.save(jobRequest, user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 직무 지원 수정
     *
     * @param jobId 수정할 지원 내역 PK
     * @param jobRequest 수정할 내용
     */
    @Operation(summary = "직무 지원 수정", description = "직무 지원 id로 직무 지원을 수정합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "직무 지원 수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다")
    })
    @PutMapping("/{jobId}")
    public ResponseEntity<JobResponse> update(
            @PathVariable Long jobId,
            @RequestBody JobRequest jobRequest,
            @AuthenticationPrincipal User user
    ) {
        JobResponse result = jobService.update(jobRequest, user.getId(), jobId);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * 직무 지원 삭제
     *
     * @param jobId 삭제할 지원 내역 PK
     */
    @Operation(summary = "직무 지원 삭제", description = "직무 지원 id로 직무 지원을 삭제합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "직무 지원 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다"),
            @ApiResponse(responseCode = "403", description = "권한이 없습니다")
    })
    @DeleteMapping("/{jobId}")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal User user,
            @PathVariable Long jobId
    ) {
        jobService.delete(jobId, user.getId());
        return ResponseEntity.noContent().build();
    }
}