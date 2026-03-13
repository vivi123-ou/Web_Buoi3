package vn.edu.demo_spmvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.demo_spmvc.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
