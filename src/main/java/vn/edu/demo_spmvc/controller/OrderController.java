package vn.edu.demo_spmvc.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vn.edu.demo_spmvc.dto.OrderRequestDTO;
import vn.edu.demo_spmvc.dto.OrderResponseDTO;
import vn.edu.demo_spmvc.enums.OrderStatus;
import vn.edu.demo_spmvc.service.OrderService;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;

    @PostMapping
    public OrderResponseDTO create(@Valid @RequestBody OrderRequestDTO request) {
        return service.createOrder(request);
    }

    @PatchMapping("/{id}/pay")
    public OrderResponseDTO pay(@PathVariable Long id) {
        return service.updateStatus(id, OrderStatus.PAID);
    }

    @PatchMapping("/{id}/cancel")
    public OrderResponseDTO cancel(@PathVariable Long id) {
        return service.cancel(id);
    }
}