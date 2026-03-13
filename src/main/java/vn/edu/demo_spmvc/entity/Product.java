package vn.edu.demo_spmvc.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
@Entity //sd nhu entity co table
@Table(name="tblProducts")
@Getter
@Setter
@NoArgsConstructor //ko co tham so vao
@AllArgsConstructor //co day du tham so vao
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal price;
    private Integer quantity;

}
