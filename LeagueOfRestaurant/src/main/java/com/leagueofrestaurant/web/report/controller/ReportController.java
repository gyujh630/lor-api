package com.leagueofrestaurant.web.report.controller;

import com.leagueofrestaurant.web.common.Status;
import com.leagueofrestaurant.web.exception.ErrorCode;
import com.leagueofrestaurant.web.exception.LORException;
import com.leagueofrestaurant.web.member.domain.Member;
import com.leagueofrestaurant.web.member.repository.MemberRepository;
import com.leagueofrestaurant.web.member.service.MemberService;
import com.leagueofrestaurant.web.report.dto.ReportDto;
import com.leagueofrestaurant.web.report.dto.UpdateReportDto;
import com.leagueofrestaurant.web.report.service.ReportService;
import com.leagueofrestaurant.web.review.domain.Review;
import com.leagueofrestaurant.web.review.repository.ReviewRepository;
import com.leagueofrestaurant.web.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ReportController {
    private final ReportService reportService;

    @Autowired
    ReviewService reviewService;

    // 신고 생성
    @PostMapping("/")
    public ResponseEntity<String> createReport(@RequestBody ReportDto reportDto, HttpSession session){
        reportService.createReport(reportDto,session);
        return ResponseEntity.ok("Report created successfully");
    }

    // 모든 신고내역 조회
    @GetMapping("/")
    public List<ReportDto> getAllReports() {
        return reportService.getAllReports();
    }

    // 특정 신고내역 조회
    @GetMapping("/{reportId}")
    public ReportDto getReportById(@PathVariable Long reportId) {
        return reportService.getReportById(reportId);
    }

    // 특정 신고 처리상태 변경
    @PutMapping("/{reportId}")
    public ResponseEntity<String> updateReportStatus(
            @RequestBody UpdateReportDto updateReportDto,
            @PathVariable Long reportId
    ) {
        reportService.updateReportStatus(updateReportDto, reportId);
        return ResponseEntity.ok("Report status updated successfully");
    }

    // 특정 회원의 신고내역 조회
    @GetMapping("/member")
    public List<ReportDto> getReportsByMemberId(HttpSession session) {
        return reportService.getReportsByMemberId(session);
    }

    // 처리상태로 신고내역 조회
    @GetMapping("/status/{status}")
    public List<ReportDto> getReportsByStatus(@PathVariable Status status) {
        return reportService.getReportsByStatus(status);
    }
}
