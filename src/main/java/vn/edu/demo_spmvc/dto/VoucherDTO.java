package vn.edu.demo_spmvc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoucherDTO {
    private Long id;

    @NotBlank(message = "Mã voucher không được để trống")
    private String code;

    @NotBlank(message = "Loại phải là PERCENT hoặc FIXED")
    private String type;

    @NotNull(message = "Giá trị không được để trống")
    private BigDecimal value;

    @NotNull(message = "Ngày hết hạn không được để trống")
    private LocalDate expiryDate;
}