package vn.edu.demo_spmvc.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class OrderDetailDTO {
    @NotNull
    private Long productId;
    @NotNull
    private Integer quantity;
}
