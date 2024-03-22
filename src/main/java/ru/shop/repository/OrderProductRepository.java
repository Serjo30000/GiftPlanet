package ru.shop.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.shop.entity.Order;
import ru.shop.entity.OrderProduct;
import ru.shop.entity.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderProductRepository extends CrudRepository<OrderProduct,Integer> {
    @Query("SELECT orders.id,orders.phone,orders.name_user,orders.surname_user,orders.order_status,orders.create_date,orders.mail,orders.payment,orders.coordinate FROM orders JOIN orders_products " +
            "ON orders_products.order_id=orders.id WHERE orders_products.id=:id")
    Optional<Order> findOrderById(@Param("id")int orderProductId);
    @Query("SELECT * FROM orders_products ORDER BY id ASC")
    List<OrderProduct> findOrderProductOrderByIdAsc();
    @Query("SELECT * FROM orders_products ORDER BY id DESC")
    List<OrderProduct> findOrderProductOrderByIdDesc();
    @Query("SELECT * FROM orders_products ORDER BY name_product ASC")
    List<OrderProduct> findOrderProductOrderByNameAsc();
    @Query("SELECT * FROM orders_products ORDER BY name_product DESC")
    List<OrderProduct> findOrderProductOrderByNameDesc();
    @Query("SELECT * FROM orders_products ORDER BY price ASC")
    List<OrderProduct> findOrderProductOrderByPriceAsc();
    @Query("SELECT * FROM orders_products ORDER BY price DESC")
    List<OrderProduct> findOrderProductOrderByPriceDesc();
    @Query("SELECT * FROM orders_products ORDER BY discount ASC")
    List<OrderProduct> findOrderProductOrderByDiscountAsc();
    @Query("SELECT * FROM orders_products ORDER BY discount DESC")
    List<OrderProduct> findOrderProductOrderByDiscountDesc();
    @Query("SELECT * FROM orders_products ORDER BY group_product ASC")
    List<OrderProduct> findOrderProductOrderByGroupAsc();
    @Query("SELECT * FROM orders_products ORDER BY group_product DESC")
    List<OrderProduct> findOrderProductOrderByGroupDesc();
    @Query("SELECT * FROM orders_products ORDER BY size_product ASC")
    List<OrderProduct> findOrderProductOrderBySizeAsc();
    @Query("SELECT * FROM orders_products ORDER BY size_product DESC")
    List<OrderProduct> findOrderProductOrderBySizeDesc();
    @Query("SELECT * FROM orders_products ORDER BY material ASC")
    List<OrderProduct> findOrderProductOrderByMaterialAsc();
    @Query("SELECT * FROM orders_products ORDER BY material DESC")
    List<OrderProduct> findOrderProductOrderByMaterialDesc();
    @Query("SELECT * FROM orders_products ORDER BY count ASC")
    List<OrderProduct> findOrderProductOrderByCountAsc();
    @Query("SELECT * FROM orders_products ORDER BY count DESC")
    List<OrderProduct> findOrderProductOrderByCountDesc();
    @Query("SELECT * FROM orders_products ORDER BY order_id ASC")
    List<OrderProduct> findOrderProductOrderByOrderIdAsc();
    @Query("SELECT * FROM orders_products ORDER BY order_id DESC")
    List<OrderProduct> findOrderProductOrderByOrderIdDesc();
    @Modifying
    @Query("UPDATE orders_products SET name_product=:nameProduct, description=:description, photo_product=:photoProduct, price=:price, discount=:discount, group_product=:groupProduct, size_product=:sizeProduct, material=:material, count=:count, order_id=:orderId WHERE id=:id")
    void updateOrderProduct(@Param("id")int id, @Param("nameProduct")String nameProduct, @Param("description")String description, @Param("photoProduct")String photoProduct, @Param("price")double price, @Param("discount")double discount, @Param("groupProduct")String groupProduct, @Param("sizeProduct")String sizeProduct, @Param("material")String material, @Param("count")int count, @Param("orderId")int orderId);
}
