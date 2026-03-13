package vn.edu.demo_spmvc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.demo_spmvc.dto.RevenueByDayDTO;
import vn.edu.demo_spmvc.dto.TopProductDTO;
import vn.edu.demo_spmvc.service.ReportService;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class ReportController {

    private final ReportService reportService;

    // VD: GET /api/reports/revenue/day?date=2025-03-13
    @GetMapping("/revenue/day")
    public List<RevenueByDayDTO> byDay(@RequestParam String date) {
        return reportService.revenueByDay(date);
    }

    // VD: GET /api/reports/revenue/month?month=2025-03
    @GetMapping("/revenue/month")
    public List<RevenueByDayDTO> byMonth(@RequestParam String month) {
        return reportService.revenueByMonth(month);
    }

    // GET /api/reports/top-products
    @GetMapping("/top-products")
    public List<TopProductDTO> topProducts() {
        return reportService.top5Products();
    }
}