package vn.edu.demo_spmvc.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.demo_spmvc.dto.OrderRequestDTO;
import vn.edu.demo_spmvc.dto.OrderResponseDTO;
import vn.edu.demo_spmvc.enums.OrderStatus;
import vn.edu.demo_spmvc.service.OrderService;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public OrderResponseDTO create(@Valid @RequestBody OrderRequestDTO request) {
        return orderService.createOrder(request);
    }

    @PatchMapping("/{id}/pay")
    @PreAuthorize("hasRole('ADMIN')")
    public OrderResponseDTO pay(@PathVariable Long id) {
        return orderService.updateStatus(id, OrderStatus.PAID);
    }

    @PatchMapping("/{id}/cancel")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public OrderResponseDTO cancel(@PathVariable Long id) {
        return orderService.cancel(id);
    }

    @GetMapping("/customer/{customerId}")
    public List<OrderResponseDTO> getByCustomer(@PathVariable Long customerId) {
        return orderService.getOrdersByCustomer(customerId);
    }
}