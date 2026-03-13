package vn.edu.demo_spmvc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.demo_spmvc.dto.RevenueByDayDTO;
import vn.edu.demo_spmvc.dto.TopProductDTO;
import vn.edu.demo_spmvc.repository.OrderRepository;
import vn.edu.demo_spmvc.service.ReportService;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportServiceImpl implements ReportService {

    private final OrderRepository orderRepository;

    @Override
    public List<RevenueByDayDTO> revenueByDay(String date) {
        return orderRepository.revenueByDayRaw(date)
                .stream()
                .map(row -> new RevenueByDayDTO(
                        (String) row[0],
                        new BigDecimal(row[1].toString())))
                .toList();
    }

    @Override
    public List<RevenueByDayDTO> revenueByMonth(String month) {
        return orderRepository.revenueByMonthRaw(month)
                .stream()
                .map(row -> new RevenueByDayDTO(
                        (String) row[0],
                        new BigDecimal(row[1].toString())))
                .toList();
    }

    @Override
    public List<TopProductDTO> top5Products() {
        return orderRepository.top5ProductsRaw()
                .stream()
                .map(row -> new TopProductDTO(
                        ((Number) row[0]).longValue(),
                        (String) row[1],
                        ((Number) row[2]).longValue(),
                        new BigDecimal(row[3].toString())))
                .toList();
    }
}