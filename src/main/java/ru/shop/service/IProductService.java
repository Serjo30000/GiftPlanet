package ru.shop.service;

import org.springframework.stereotype.Service;
import ru.shop.entity.OrderProduct;
import ru.shop.entity.Product;
import ru.shop.entity.ProductSize;

import java.util.List;

@Service
public interface IProductService {
    boolean updateProduct(Product product);
    List<ProductSize> allProductSizes(int productId);
    List<Product> sortProduct(List<Product>lstPr,String sortName,String sortBy);
    List<Product> allProductsByGroup(String groupProduct);
    double minPriceProduct(int productId);
    boolean isDiscountProduct(int productId);
    Product findProductByName(String productName);
}
