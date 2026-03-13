package vn.edu.demo_spmvc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.edu.demo_spmvc.entity.Product;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByDeletedFalse();

    Optional<Product> findByIdAndDeletedFalse(Long id);

    @Query("""
        SELECT p FROM Product p
        WHERE p.deleted = false
          AND (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%',:name,'%')))
          AND (:min IS NULL OR p.price >= :min)
          AND (:max IS NULL OR p.price <= :max)
          AND (:inStock IS NULL
               OR (:inStock = true AND p.quantity > 0)
               OR (:inStock = false AND p.quantity = 0))
    """)
    Page<Product> search(
            @Param("name") String name,
            @Param("min") BigDecimal minPrice,
            @Param("max") BigDecimal maxPrice,
            @Param("inStock") Boolean inStock,
            Pageable pageable
    );
}