package vn.edu.demo_spmvc.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.demo_spmvc.dto.OrderRequestDTO;
import vn.edu.demo_spmvc.dto.OrderResponseDTO;
import vn.edu.demo_spmvc.service.OrderService;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;

    @PostMapping
    public OrderResponseDTO create (@Valid @RequestBody OrderRequestDTO request){
        return service.createOrder(request);
    }
}
