package vn.edu.demo_spmvc.service;

import vn.edu.demo_spmvc.dto.RevenueByDayDTO;
import vn.edu.demo_spmvc.dto.TopProductDTO;
import java.util.List;

public interface ReportService {
    List<RevenueByDayDTO> revenueByDay(String date);
    List<RevenueByDayDTO> revenueByMonth(String month);
    List<TopProductDTO> top5Products();
}