package vn.edu.demo_spmvc.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.edu.demo_spmvc.dto.RevenueByDayDTO;
import vn.edu.demo_spmvc.dto.TopProductDTO;
import vn.edu.demo_spmvc.entity.Order;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("""
        SELECT new vn.edu.demo_spmvc.dto.RevenueByDayDTO(
            FUNCTION('DATE_FORMAT', o.orderDate, '%Y-%m-%d'),
            SUM(o.totalAmount))
        FROM Order o
        WHERE FUNCTION('DATE_FORMAT', o.orderDate, '%Y-%m-%d') = :date
        GROUP BY FUNCTION('DATE_FORMAT', o.orderDate, '%Y-%m-%d')
    """)
    List<RevenueByDayDTO> revenueByDay(@Param("date") String date);

    @Query("""
        SELECT new vn.edu.demo_spmvc.dto.RevenueByDayDTO(
            FUNCTION('DATE_FORMAT', o.orderDate, '%Y-%m'),
            SUM(o.totalAmount))
        FROM Order o
        WHERE FUNCTION('DATE_FORMAT', o.orderDate, '%Y-%m') = :month
        GROUP BY FUNCTION('DATE_FORMAT', o.orderDate, '%Y-%m')
    """)
    List<RevenueByDayDTO> revenueByMonth(@Param("month") String month);

    @Query("""
        SELECT new vn.edu.demo_spmvc.dto.TopProductDTO(
            d.product.id,
            d.product.name,
            SUM(d.quantity),
            SUM(d.subTotal))
        FROM OrderDetail d
        GROUP BY d.product.id, d.product.name
        ORDER BY SUM(d.quantity) DESC
    """)
    List<TopProductDTO> top5Products(Pageable pageable);
}