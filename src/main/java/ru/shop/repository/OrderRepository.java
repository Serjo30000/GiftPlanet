package ru.shop.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.shop.entity.Order;
import ru.shop.entity.OrderProduct;
import ru.shop.entity.Product;

import java.sql.Date;
import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order,Integer> {
    @Query("SELECT orders_products.id, orders_products.name_product, orders_products.description, orders_products.photo_product, orders_products.price, orders_products.discount, orders_products.group_product, orders_products.size_product, orders_products.material, orders_products.count, orders_products.order_id FROM orders_products JOIN orders ON orders_products.order_id=orders.id WHERE" +
            " orders_products.order_id=:id")
    List<OrderProduct> findOrderProductByAll(@Param("id")int orderId);
    @Query("SELECT * FROM orders ORDER BY id ASC")
    List<Order> findOrderOrderByIdAsc();
    @Query("SELECT * FROM orders ORDER BY id DESC")
    List<Order> findOrderOrderByIdDesc();
    @Query("SELECT * FROM orders ORDER BY phone ASC")
    List<Order> findOrderOrderByPhoneAsc();
    @Query("SELECT * FROM orders ORDER BY phone DESC")
    List<Order> findOrderOrderByPhoneDesc();
    @Query("SELECT * FROM orders ORDER BY name_user ASC")
    List<Order> findOrderOrderByNameAsc();
    @Query("SELECT * FROM orders ORDER BY name_user DESC")
    List<Order> findOrderOrderByNameDesc();
    @Query("SELECT * FROM orders ORDER BY surname_user ASC")
    List<Order> findOrderOrderBySurnameAsc();
    @Query("SELECT * FROM orders ORDER BY surname_user DESC")
    List<Order> findOrderOrderBySurnameDesc();
    @Query("SELECT * FROM orders ORDER BY order_status ASC")
    List<Order> findOrderOrderByStatusAsc();
    @Query("SELECT * FROM orders ORDER BY order_status DESC")
    List<Order> findOrderOrderByStatusDesc();
    @Query("SELECT * FROM orders ORDER BY create_date ASC")
    List<Order> findOrderOrderByDateAsc();
    @Query("SELECT * FROM orders ORDER BY create_date DESC")
    List<Order> findOrderOrderByDateDesc();
    @Query("SELECT * FROM orders ORDER BY mail ASC")
    List<Order> findOrderOrderByMailAsc();
    @Query("SELECT * FROM orders ORDER BY mail DESC")
    List<Order> findOrderOrderByMailDesc();
    @Query("SELECT * FROM orders ORDER BY payment ASC")
    List<Order> findOrderOrderByPaymentAsc();
    @Query("SELECT * FROM orders ORDER BY payment DESC")
    List<Order> findOrderOrderByPaymentDesc();
    @Query("SELECT orders.id, orders.phone, orders.name_user, orders.surname_user, orders.order_status, orders.create_date,orders.mail,orders.payment,orders.coordinate FROM orders LEFT JOIN orders_products ON orders_products.order_id=orders.id GROUP BY orders.id, orders.phone, orders.name_user, orders.surname_user, orders.order_status, orders.create_date ORDER BY coalesce(SUM(orders_products.price*orders_products.count),0) ASC")
    List<Order> findOrderOrderByPriceAsc();
    @Query("SELECT orders.id, orders.phone, orders.name_user, orders.surname_user, orders.order_status, orders.create_date,orders.mail,orders.payment,orders.coordinate FROM orders LEFT JOIN orders_products ON orders_products.order_id=orders.id GROUP BY orders.id, orders.phone, orders.name_user, orders.surname_user, orders.order_status, orders.create_date ORDER BY coalesce(SUM(orders_products.price*orders_products.count),0) DESC")
    List<Order> findOrderOrderByPriceDesc();
    @Query("SELECT orders.id, orders.phone, orders.name_user, orders.surname_user, orders.order_status, orders.create_date,orders.mail,orders.payment,orders.coordinate FROM orders LEFT JOIN orders_products ON orders_products.order_id=orders.id GROUP BY orders.id, orders.phone, orders.name_user, orders.surname_user, orders.order_status, orders.create_date ORDER BY coalesce(SUM(orders_products.count),0) ASC")
    List<Order> findOrderOrderByCountAsc();
    @Query("SELECT orders.id, orders.phone, orders.name_user, orders.surname_user, orders.order_status, orders.create_date,orders.mail,orders.payment,orders.coordinate FROM orders LEFT JOIN orders_products ON orders_products.order_id=orders.id GROUP BY orders.id, orders.phone, orders.name_user, orders.surname_user, orders.order_status, orders.create_date ORDER BY coalesce(SUM(orders_products.count),0) DESC")
    List<Order> findOrderOrderByCountDesc();
    @Modifying
    @Query("UPDATE orders SET phone=:phone, name_user=:nameUser, surname_user=:surnameUser, order_status=:orderStatus, create_date=:createDate, mail=:mail, payment=:payment,coordinate=:coordinate WHERE id=:id")
    void updateOrder(@Param("id")int id, @Param("phone")String phone, @Param("nameUser")String nameUser, @Param("surnameUser")String surnameUser, @Param("orderStatus")String orderStatus, @Param("createDate") Date createDate, @Param("mail")String mail, @Param("payment")String payment, @Param("coordinate")String coordinate);
}
