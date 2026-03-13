package vn.edu.demo_spmvc.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.List;

@Data
public class OrderRequestDTO {
    @NotBlank(message = "Tên khách hàng không được để trống")
    private String customerName;

    @NotEmpty(message = "Đơn hàng phải có ít nhất 1 sản phẩm")
    @Valid
    private List<OrderDetailDTO> item;

    private String voucherCode;
    private Long customerId;
}