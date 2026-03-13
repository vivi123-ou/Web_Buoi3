package vn.edu.demo_spmvc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data//class nay dung de luu du lieu
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    @NotBlank //rang buoc, ko cho name rong!
    private String name;
    @NotNull
    private BigDecimal price;
    @NotNull
    private Integer quantity;
}