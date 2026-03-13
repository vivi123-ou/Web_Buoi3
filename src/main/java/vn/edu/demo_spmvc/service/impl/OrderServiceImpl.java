package vn.edu.demo_spmvc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vn.edu.demo_spmvc.dto.OrderDetailDTO;
import vn.edu.demo_spmvc.dto.OrderRequestDTO;
import vn.edu.demo_spmvc.dto.OrderResponseDTO;
import vn.edu.demo_spmvc.entity.Order;
import vn.edu.demo_spmvc.entity.OrderDetail;
import vn.edu.demo_spmvc.entity.Product;
import vn.edu.demo_spmvc.repository.OrderRepository;
import vn.edu.demo_spmvc.repository.ProductRepository;
import vn.edu.demo_spmvc.service.OrderService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO request) {
        Order order = new Order();
        order.setCustomerName(request.getCustomerName());
        order.setOrderDate(LocalDateTime.now());

        List<OrderDetail> details = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (OrderDetailDTO item : request.getItem()){
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            if(product.getQuantity() < item.getQuantity()){
                throw new RuntimeException("Not enough stock");
            }

            BigDecimal subTotal = product.getPrice().multiply (BigDecimal.valueOf(item.getQuantity()));
            OrderDetail detail = OrderDetail.builder()
                    .orders(order)
                    .product(product)
                    .quantity(item.getQuantity())
                    .price(product.getPrice())
                    .subTotal(subTotal)
                    .build();

            total = total.add(subTotal);
            product.setQuantity ((product.getQuantity() - item.getQuantity()));
            details.add(detail);
        }
        order.setTotalAmount(total);
        order.setOrderDetails (details);
        Order saved = orderRepository.save((order));
        return OrderResponseDTO.builder()
                .id(saved.getId())
                .customerName(saved.getCustomerName())
                .orderDate(saved.getOrderDate())
                .totalAmount(saved.getTotalAmount())
                .build();
    }
}
