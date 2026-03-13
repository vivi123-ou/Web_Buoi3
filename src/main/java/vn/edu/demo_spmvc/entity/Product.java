package vn.edu.demo_spmvc.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "tblProducts")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private BigDecimal price;
    private Integer quantity;

    @Builder.Default
    private boolean deleted = false;

    @Version
    private Long version;

    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orderDetails;
}