package vn.edu.demo_spmvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.demo_spmvc.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
