package vn.edu.demo_spmvc.service;

import vn.edu.demo_spmvc.dto.OrderRequestDTO;
import vn.edu.demo_spmvc.dto.OrderResponseDTO;

public interface OrderService {
    OrderResponseDTO createOrder(OrderRequestDTO request);

}
