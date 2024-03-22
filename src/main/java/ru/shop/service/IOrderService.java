package ru.shop.service;

import org.springframework.stereotype.Service;
import ru.shop.entity.Order;
import ru.shop.entity.OrderProduct;

import java.util.List;

@Service
public interface IOrderService {
    boolean updateOrder(Order order);
    List<OrderProduct> allOrderProducts(int orderId);
    int countOrderProducts(int orderId);
    double priceOrderProducts(int orderId);
    List<Order> sortOrder(List<Order>lstOr, String sortName, String sortBy);
}
