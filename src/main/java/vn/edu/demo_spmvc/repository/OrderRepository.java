package vn.edu.demo_spmvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.edu.demo_spmvc.entity.Order;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCustomerId(Long customerId);

    @Query(value = """
        SELECT DATE_FORMAT(o.orderDate, '%Y-%m-%d') AS date,
               SUM(o.totalAmount) AS totalRevenue
        FROM tblOrders o
        WHERE DATE_FORMAT(o.orderDate, '%Y-%m-%d') = :date
        GROUP BY DATE_FORMAT(o.orderDate, '%Y-%m-%d')
    """, nativeQuery = true)
    List<Object[]> revenueByDayRaw(@Param("date") String date);

    @Query(value = """
       SELECT DATE_FORMAT(o.orderDate, '%Y-%m') AS date,
        SUM(o.totalAmount) AS totalRevenue
        FROM tblOrders o
        WHERE DATE_FORMAT(o.orderDate, '%Y-%m') = :month
        GROUP BY DATE_FORMAT(o.orderDate, '%Y-%m')
    """, nativeQuery = true)
    List<Object[]> revenueByMonthRaw(@Param("month") String month);

    @Query(value = """
      SELECT d.product_id AS productId,
                      p.name AS productName,
                      SUM(d.quantity) AS totalSold,
                      SUM(d.subTotal) AS totalRevenue
               FROM tblOrderDetails d
               JOIN tblProducts p ON p.id = d.product_id
               GROUP BY d.product_id, p.name
               ORDER BY SUM(d.quantity) DESC
               LIMIT 5
    """, nativeQuery = true)
    List<Object[]> top5ProductsRaw();
}