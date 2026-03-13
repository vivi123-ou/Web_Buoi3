package vn.edu.demo_spmvc.service;

import vn.edu.demo_spmvc.dto.OrderRequestDTO;
import vn.edu.demo_spmvc.dto.OrderResponseDTO;
import vn.edu.demo_spmvc.enums.OrderStatus;
import java.util.List;

public interface OrderService {
    OrderResponseDTO createOrder(OrderRequestDTO request);
    OrderResponseDTO updateStatus(Long id, OrderStatus status);
    OrderResponseDTO cancel(Long id);
    List<OrderResponseDTO> getOrdersByCustomer(Long customerId);
}