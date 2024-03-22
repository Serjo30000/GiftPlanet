package ru.shop.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.shop.entity.Order;
import ru.shop.entity.Product;
import ru.shop.entity.ProductSize;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductSizeRepository extends CrudRepository<ProductSize,Integer> {
    @Query("SELECT products.id, products.name_product, products.description, products.photo_product, products.group_product, products.material FROM products JOIN products_sizes " +
            "ON products_sizes.product_id=products.id WHERE products_sizes.id=:id")
    Optional<Product> findProductById(@Param("id")int productSizeId);
    @Query("SELECT * FROM products_sizes ORDER BY id ASC")
    List<ProductSize> findProductSizeOrderByIdAsc();
    @Query("SELECT * FROM products_sizes ORDER BY id DESC")
    List<ProductSize> findProductSizeOrderByIdDesc();
    @Query("SELECT * FROM products_sizes ORDER BY price ASC")
    List<ProductSize> findProductSizeOrderByPriceAsc();
    @Query("SELECT * FROM products_sizes ORDER BY price DESC")
    List<ProductSize> findProductSizeOrderByPriceDesc();
    @Query("SELECT * FROM products_sizes ORDER BY discount ASC")
    List<ProductSize> findProductSizeOrderByDiscountAsc();
    @Query("SELECT * FROM products_sizes ORDER BY discount DESC")
    List<ProductSize> findProductSizeOrderByDiscountDesc();
    @Query("SELECT * FROM products_sizes ORDER BY size_product ASC")
    List<ProductSize> findProductSizeOrderBySizeAsc();
    @Query("SELECT * FROM products_sizes ORDER BY size_product DESC")
    List<ProductSize> findProductSizeOrderBySizeDesc();
    @Query("SELECT * FROM products_sizes ORDER BY product_id ASC")
    List<ProductSize> findProductSizeOrderByProductIdAsc();
    @Query("SELECT * FROM products_sizes ORDER BY product_id DESC")
    List<ProductSize> findProductSizeOrderByProductIdDesc();
    @Modifying
    @Query("UPDATE products_sizes SET price=:price, size_product=:sizeProduct, product_id=:productId, discount=:discount WHERE id=:id")
    void updateProductSize(@Param("id")int id, @Param("price")double price, @Param("sizeProduct")String sizeProduct, @Param("productId")int productId, @Param("discount")double discount);
}
