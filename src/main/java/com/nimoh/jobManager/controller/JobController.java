package com.nimoh.jobManager.controller;

import com.nimoh.jobManager.data.dto.job.JobResponse;
import com.nimoh.jobManager.data.dto.job.JobRequest;
import com.nimoh.jobManager.data.dto.user.UserResponse;
import com.nimoh.jobManager.service.api.RestTemplateService;
import com.nimoh.jobManager.service.job.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 직무 지원 컨트롤러
 * @author nimoh
 */
@RestController
@RequestMapping("/api/v1/job")
public class JobController {
    private final JobService jobService;
    private final RestTemplateService restTemplateService;
    private final Logger LOGGER = LoggerFactory.getLogger(JobController.class);
    @Autowired
    public JobController(JobService jobService, RestTemplateService restTemplateService){
        this.jobService = jobService;
        this.restTemplateService = restTemplateService;
    }

    /**
     * 직무 지원 목록 리턴
     * @return
     */
    @Operation(summary = "직무 지원 목록 조회", description = "직무 지원 목록을 모두 조회합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "직무 지원 목록 조회 성공"),
            @ApiResponse(responseCode = "204", description = "직무 지원내역이 없습니다"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다")
    })
    @GetMapping("")
    public ResponseEntity<List<JobResponse>> getList(
            HttpServletRequest request
    ) {
        Long userId = getCurrentUserId(request);
        List<JobResponse> result = jobService.findByUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 직무 지원 등록
     * @param jobRequest
     */
    @Operation(summary = "직무 지원 등록",description = "직무 지원을 작성합니다.")
    @PostMapping("")
    public ResponseEntity<Void> save(
            @RequestBody final JobRequest jobRequest,
            @SessionAttribute(name = "sid", required = false) UserResponse loginUser
    ) {
        Map<String,String> geocode = restTemplateService.getGeocode(jobRequest.getLocation());
        jobRequest.setX(geocode.get("x"));
        jobRequest.setY(geocode.get("y"));
        jobService.save(jobRequest, loginUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 직무 지원 수정
     * @param jobId
     */
    @Operation(summary = "직무 지원 수정",description = "직무 지원 id로 직무 지원을 수정합니다")
    @PutMapping("/{jobId}")
    public ResponseEntity<JobResponse> update(
            @PathVariable Long jobId,
            @RequestBody JobRequest jobRequest,
            @SessionAttribute(name = "sid", required = false) UserResponse loginUser
    ) {
        JobResponse result = jobService.update(jobRequest,loginUser.getId(),jobId);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * 직무 지원 삭제
     * @param jobId
     */
    @Operation(summary = "직무 지원 삭제",description = "직무 지원 id로 직무 지원을 삭제합니다")
    @DeleteMapping("/{jobId}")
    public ResponseEntity<Void> delete(
            HttpServletRequest request,
            @PathVariable Long jobId
    ) {
        HttpSession session = request.getSession();
        UserResponse loginUser = (UserResponse) session.getAttribute("sid");
        jobService.delete(jobId,loginUser.getId());
        return ResponseEntity.noContent().build();
    }

    private Long getCurrentUserId(HttpServletRequest request){
        HttpSession session = request.getSession();
        UserResponse loginUser =(UserResponse) session.getAttribute("sid");
        return loginUser.getId();
    }
}