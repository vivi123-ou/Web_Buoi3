package vn.edu.demo_spmvc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.demo_spmvc.dto.OrderDetailDTO;
import vn.edu.demo_spmvc.dto.OrderRequestDTO;
import vn.edu.demo_spmvc.dto.OrderResponseDTO;
import vn.edu.demo_spmvc.entity.*;
import vn.edu.demo_spmvc.enums.OrderStatus;
import vn.edu.demo_spmvc.repository.*;
import vn.edu.demo_spmvc.service.OrderService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final VoucherRepository voucherRepository;
    private final CustomerRepository customerRepository;

    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO request) {

        // 1. Gộp product trùng
        Map<Long, Integer> merged = new HashMap<>();
        for (OrderDetailDTO item : request.getItem()) {
            merged.merge(item.getProductId(), item.getQuantity(), Integer::sum);
        }

        // 2. Khởi tạo Order
        Order order = new Order();
        order.setCustomerName(request.getCustomerName());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);

        // 3. Gắn Customer nếu có
        if (request.getCustomerId() != null) {
            Customer customer = customerRepository.findById(request.getCustomerId())
                    .orElseThrow(() -> new RuntimeException("Customer not found"));
            order.setCustomer(customer);
        }

        // 4. Xử lý từng sản phẩm
        List<OrderDetail> details = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (Map.Entry<Long, Integer> entry : merged.entrySet()) {
            Product product = productRepository.findById(entry.getKey())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + entry.getKey()));

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

        // 5. Áp dụng Voucher nếu có
        if (request.getVoucherCode() != null && !request.getVoucherCode().isBlank()) {
            Voucher voucher = voucherRepository.findByCode(request.getVoucherCode())
                    .orElseThrow(() -> new RuntimeException("Voucher không tồn tại: " + request.getVoucherCode()));

            if (voucher.isUsed()) {
                throw new RuntimeException("Voucher đã được sử dụng");
            }
            if (voucher.getExpiryDate().isBefore(LocalDate.now())) {
                throw new RuntimeException("Voucher đã hết hạn");
            }

            if ("PERCENT".equals(voucher.getType())) {
                BigDecimal discount = total.multiply(voucher.getValue())
                        .divide(BigDecimal.valueOf(100));
                total = total.subtract(discount);
            } else if ("FIXED".equals(voucher.getType())) {
                total = total.subtract(voucher.getValue());
            }

            // Không cho tổng tiền âm
            if (total.compareTo(BigDecimal.ZERO) < 0) {
                total = BigDecimal.ZERO;
            }

            voucher.setUsed(true);
            voucherRepository.save(voucher);
        }

        // 6. Lưu Order
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
            throw new RuntimeException("Không thể hủy đơn hàng đã thanh toán hoặc hoàn thành");
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