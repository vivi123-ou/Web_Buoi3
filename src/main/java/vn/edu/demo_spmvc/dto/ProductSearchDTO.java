package vn.edu.demo_spmvc.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductSearchDTO {
    private String name;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Boolean inStock;
}