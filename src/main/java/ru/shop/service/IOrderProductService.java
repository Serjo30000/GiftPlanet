package ru.shop.service;

import org.springframework.stereotype.Service;
import ru.shop.entity.Order;
import ru.shop.entity.OrderProduct;

import java.util.List;

@Service
public interface IOrderProductService {
    boolean updateOrderProduct(OrderProduct orderProduct);
    Order findOrderByOrderProductId(int orderProductId);
    List<OrderProduct> sortOrderProduct(List<OrderProduct>lstOrPr, String sortName, String sortBy);
    double priceWithDiscount(double price, double discount);
    double allPriceWithDiscount(List<OrderProduct>lstOrPr);
}
