package vn.edu.demo_spmvc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.demo_spmvc.dto.OrderDetailDTO;
import vn.edu.demo_spmvc.dto.OrderRequestDTO;
import vn.edu.demo_spmvc.dto.OrderResponseDTO;
import vn.edu.demo_spmvc.entity.Order;
import vn.edu.demo_spmvc.entity.OrderDetail;
import vn.edu.demo_spmvc.entity.Product;
import vn.edu.demo_spmvc.enums.OrderStatus;
import vn.edu.demo_spmvc.repository.OrderRepository;
import vn.edu.demo_spmvc.repository.ProductRepository;
import vn.edu.demo_spmvc.service.OrderService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO request) {
        // Gộp product trùng
        Map<Long, Integer> merged = new HashMap<>();
        for (OrderDetailDTO item : request.getItem()) {
            merged.merge(item.getProductId(), item.getQuantity(), Integer::sum);
        }

        Order order = new Order();
        order.setCustomerName(request.getCustomerName());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);

        List<OrderDetail> details = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (Map.Entry<Long, Integer> entry : merged.entrySet()) {
            Product product = productRepository.findById(entry.getKey())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            if (product.getQuantity() < entry.getValue()) {
                throw new RuntimeException("Not enough stock for: " + product.getName());
            }

            BigDecimal subTotal = product.getPrice().multiply(BigDecimal.valueOf(entry.getValue()));
            OrderDetail detail = OrderDetail.builder()
                    .orders(order)
                    .product(product)
                    .quantity(entry.getValue())
                    .price(product.getPrice())
                    .subTotal(subTotal)
                    .build();

            total = total.add(subTotal);
            product.setQuantity(product.getQuantity() - entry.getValue());
            details.add(detail);
        }

        order.setTotalAmount(total);
        order.setOrderDetails(details);
        return toResponseDTO(orderRepository.save(order));
    }

    @Override
    public OrderResponseDTO updateStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        return toResponseDTO(orderRepository.save(order));
    }

    @Override
    public OrderResponseDTO cancel(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() == OrderStatus.PAID || order.getStatus() == OrderStatus.COMPLETED) {
            throw new RuntimeException("Khong the huy don hang da thanh toan hoac hoan thanh");
        }

        // Hoàn trả stock
        for (OrderDetail d : order.getOrderDetails()) {
            Product p = d.getProduct();
            p.setQuantity(p.getQuantity() + d.getQuantity());
        }

        order.setStatus(OrderStatus.CANCELLED);
        return toResponseDTO(orderRepository.save(order));
    }

    private OrderResponseDTO toResponseDTO(Order o) {
        return OrderResponseDTO.builder()
                .id(o.getId())
                .customerName(o.getCustomerName())
                .orderDate(o.getOrderDate())
                .totalAmount(o.getTotalAmount())
                .build();
    }
}