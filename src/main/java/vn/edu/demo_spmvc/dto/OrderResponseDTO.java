package vn.edu.demo_spmvc.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class OrderResponseDTO {
    private Long id;
    private String customerName;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
}
