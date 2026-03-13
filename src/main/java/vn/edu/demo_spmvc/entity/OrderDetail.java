package vn.edu.demo_spmvc.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tblOrderDetails")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //strategy cho tăng tự động
    private Long id;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal subTotal;

    @ManyToOne
    @JoinColumn(name = "order_id") //lien ket giua 2 bang, dat ten cot ket noi
    private Order orders;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
