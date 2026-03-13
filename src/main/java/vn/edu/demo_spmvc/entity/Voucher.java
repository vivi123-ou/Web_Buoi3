package vn.edu.demo_spmvc.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tblVouchers")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    // "PERCENT" hoặc "FIXED"
    private String type;

    private BigDecimal value;

    private LocalDate expiryDate;

    private boolean used = false;
}