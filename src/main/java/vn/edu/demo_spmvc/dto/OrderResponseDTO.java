package vn.edu.demo_spmvc.dto;

import lombok.Builder;
import lombok.Data;
import vn.edu.demo_spmvc.enums.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class OrderResponseDTO {
    private Long id;
    private String customerName;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private OrderStatus status;
}