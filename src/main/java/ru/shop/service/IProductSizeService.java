package ru.shop.service;

import org.springframework.stereotype.Service;
import ru.shop.entity.Order;
import ru.shop.entity.OrderProduct;
import ru.shop.entity.Product;
import ru.shop.entity.ProductSize;

import java.util.List;

@Service
public interface IProductSizeService {
    boolean updateProductSize(ProductSize productSize);
    Product findProductByProductSizeId(int productSizeId);
    List<ProductSize> sortProductSize(List<ProductSize>lstPrSz, String sortName, String sortBy);
}
