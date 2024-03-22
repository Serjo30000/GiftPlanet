package ru.shop.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.shop.entity.OrderProduct;
import ru.shop.entity.Product;
import ru.shop.entity.ProductSize;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product,Integer> {
    @Query("SELECT * FROM products WHERE name_product = :nameProduct")
    Product findByNameProduct(@Param("nameProduct")String nameProduct);
    @Query("SELECT products.id, products.name_product, products.description, products.photo_product, products.group_product, products.material FROM products JOIN products_sizes ON products_sizes.product_id=products.id GROUP BY products.id, products.name_product, products.description, products.photo_product, products.group_product, products.material HAVING products.group_product = :groupProduct AND SUM(products_sizes.price)>0")
    List<Product> findProductByGroup(@Param("groupProduct")String groupProduct);
    @Query("SELECT products_sizes.id, products_sizes.price, products_sizes.size_product, products_sizes.product_id, products_sizes.discount FROM products_sizes JOIN products ON products_sizes.product_id=products.id WHERE" +
            " products_sizes.product_id=:id")
    List<ProductSize> findProductSizeByAll(@Param("id")int productId);

    @Query("SELECT * FROM products ORDER BY id ASC")
    List<Product> findProductOrderByIdAsc();

    @Query("SELECT * FROM products ORDER BY id DESC")
    List<Product> findProductOrderByIdDesc();

    @Query("SELECT * FROM products ORDER BY name_product ASC")
    List<Product> findProductOrderByNameAsc();

    @Query("SELECT * FROM products ORDER BY name_product DESC")
    List<Product> findProductOrderByNameDesc();

    @Query("SELECT * FROM products ORDER BY group_product ASC")
    List<Product> findProductOrderByGroupAsc();

    @Query("SELECT * FROM products ORDER BY group_product DESC")
    List<Product> findProductOrderByGroupDesc();

    @Query("SELECT * FROM products ORDER BY material ASC")
    List<Product> findProductOrderByMaterialAsc();

    @Query("SELECT * FROM products ORDER BY material Desc")
    List<Product> findProductOrderByMaterialDesc();
    @Query("SELECT products.id, products.name_product, products.description, products.photo_product, products.group_product, products.material FROM products JOIN products_sizes ON products_sizes.product_id=products.id GROUP BY products.id, products.name_product, products.description, products.photo_product, products.group_product, products.material ORDER BY coalesce(MIN(products_sizes.price),0) ASC")
    List<Product> findProductOrderByPriceAsc();
    @Query("SELECT products.id, products.name_product, products.description, products.photo_product, products.group_product, products.material FROM products JOIN products_sizes ON products_sizes.product_id=products.id GROUP BY products.id, products.name_product, products.description, products.photo_product, products.group_product, products.material ORDER BY coalesce(MIN(products_sizes.price),0) DESC")
    List<Product> findProductOrderByPriceDesc();
    @Query("SELECT products.id, products.name_product, products.description, products.photo_product, products.group_product, products.material FROM products JOIN products_sizes ON products_sizes.product_id=products.id GROUP BY products.id, products.name_product, products.description, products.photo_product, products.group_product, products.material ORDER BY coalesce(MIN(products_sizes.discount),0) ASC")
    List<Product> findProductOrderByDiscountAsc();
    @Query("SELECT products.id, products.name_product, products.description, products.photo_product, products.group_product, products.material FROM products JOIN products_sizes ON products_sizes.product_id=products.id GROUP BY products.id, products.name_product, products.description, products.photo_product, products.group_product, products.material ORDER BY coalesce(MIN(products_sizes.discount),0) DESC")
    List<Product> findProductOrderByDiscountDesc();
    @Modifying
    @Query("UPDATE products SET name_product=:nameProduct, description=:description, photo_product=:photoProduct, group_product=:groupProduct, material=:material WHERE id=:id")
    void updateProduct(@Param("id")int id, @Param("nameProduct")String nameProduct, @Param("description")String description, @Param("photoProduct")String photoProduct, @Param("groupProduct")String groupProduct, @Param("material")String material);
}
