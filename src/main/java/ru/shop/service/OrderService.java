package ru.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shop.entity.Order;
import ru.shop.entity.OrderProduct;
import ru.shop.entity.Product;
import ru.shop.repository.OrderRepository;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IOrderService{
    @Autowired
    OrderRepository orderRepository;
    public Order findOrderById(int orderId){
        Optional<Order> orderFromDb = orderRepository.findById(orderId);
        return orderFromDb.orElse(new Order());
    }
    public List<Order> allOrders() {
        return (List<Order>) orderRepository.findAll();
    }
    public boolean deleteOrder(int orderId) {
        if (orderRepository.findById(orderId).isPresent()) {
            orderRepository.deleteById(orderId);
            return true;
        }
        return false;
    }
    public boolean saveOrder(Order order) {
        if (order == null) {
            return false;
        }
        orderRepository.save(order);
        return true;
    }
    @Override
    public boolean updateOrder(Order order) {
        if (order == null) {
            return true;
        }
        Order orderToUpdate = orderRepository.findById(order.getId()).get();
        orderToUpdate.setPhone(order.getPhone());
        orderToUpdate.setNameUser(order.getNameUser());
        orderToUpdate.setSurnameUser(order.getSurnameUser());
        orderToUpdate.setOrderStatus(order.getOrderStatus());
        orderToUpdate.setCreateDate(order.getCreateDate());
        orderToUpdate.setMail(order.getMail());
        orderToUpdate.setPayment(order.getPayment());
        orderToUpdate.setCoordinate(order.getCoordinate());
        orderRepository.updateOrder(orderToUpdate.getId(),orderToUpdate.getPhone(),orderToUpdate.getNameUser(),
                orderToUpdate.getSurnameUser(),orderToUpdate.getOrderStatus(),orderToUpdate.getCreateDate(),orderToUpdate.getMail(),orderToUpdate.getPayment(),orderToUpdate.getCoordinate());
        return false;
    }
    @Override
    public List<OrderProduct> allOrderProducts(int orderId) {
        return orderRepository.findOrderProductByAll(orderId);
    }
    @Override
    public int countOrderProducts(int orderId){
        int sumCount=0;
        for (OrderProduct ordPr:orderRepository.findOrderProductByAll(orderId)){
            sumCount+=ordPr.getCount();
        }
        return sumCount;
    }
    @Override
    public double priceOrderProducts(int orderId){
        double sumPrice=0;
        for (OrderProduct ordPr:orderRepository.findOrderProductByAll(orderId)){
            if (ordPr.getDiscount()!=0){
                sumPrice+=ordPr.getPrice()*ordPr.getCount()*(100-ordPr.getDiscount())/100;
            }
            else{
                sumPrice+=ordPr.getPrice()*ordPr.getCount();
            }
        }
        DecimalFormat decimalFormat = new DecimalFormat( "#.##" );
        String resultFormat = decimalFormat.format(sumPrice);
        resultFormat = resultFormat.replaceAll(",",".");
        double resultDouble = Double.parseDouble(resultFormat);
        return resultDouble;
    }

    @Override
    public List<Order> sortOrder(List<Order>lstOr, String sortName, String sortBy){
        List<Order> lstOrder = new ArrayList<>();
        if (sortName.equals("Отсутствует")){
            return lstOr;
        }
        if (sortName.equals("По номеру")){
            if (sortBy.equals("По возрастанию")){
                lstOrder=orderRepository.findOrderOrderByIdAsc();
            }
            else{
                lstOrder=orderRepository.findOrderOrderByIdDesc();
            }
        }
        if (sortName.equals("По телефону")){
            if (sortBy.equals("По возрастанию")){
                lstOrder=orderRepository.findOrderOrderByPhoneAsc();
            }
            else{
                lstOrder=orderRepository.findOrderOrderByPhoneDesc();
            }
        }
        if (sortName.equals("По имени")){
            if (sortBy.equals("По возрастанию")){
                lstOrder=orderRepository.findOrderOrderByNameAsc();
            }
            else{
                lstOrder=orderRepository.findOrderOrderByNameDesc();
            }
        }
        if (sortName.equals("По фамилии")){
            if (sortBy.equals("По возрастанию")){
                lstOrder=orderRepository.findOrderOrderBySurnameAsc();
            }
            else{
                lstOrder=orderRepository.findOrderOrderBySurnameDesc();
            }
        }
        if (sortName.equals("По статусу")){
            if (sortBy.equals("По возрастанию")){
                lstOrder=orderRepository.findOrderOrderByStatusAsc();
            }
            else{
                lstOrder=orderRepository.findOrderOrderByStatusDesc();
            }
        }
        if (sortName.equals("По дате")){
            if (sortBy.equals("По возрастанию")){
                lstOrder=orderRepository.findOrderOrderByDateAsc();
            }
            else{
                lstOrder=orderRepository.findOrderOrderByDateDesc();
            }
        }
        if (sortName.equals("По цене")){
            if (sortBy.equals("По возрастанию")){
                lstOrder=orderRepository.findOrderOrderByPriceAsc();
            }
            else{
                lstOrder=orderRepository.findOrderOrderByPriceDesc();
            }
        }
        if (sortName.equals("По количеству")){
            if (sortBy.equals("По возрастанию")){
                lstOrder=orderRepository.findOrderOrderByCountAsc();
            }
            else{
                lstOrder=orderRepository.findOrderOrderByCountDesc();
            }
        }
        if (sortName.equals("По почте")){
            if (sortBy.equals("По возрастанию")){
                lstOrder=orderRepository.findOrderOrderByMailAsc();
            }
            else{
                lstOrder=orderRepository.findOrderOrderByMailDesc();
            }
        }
        if (sortName.equals("По оплате")){
            if (sortBy.equals("По возрастанию")){
                lstOrder=orderRepository.findOrderOrderByPaymentAsc();
            }
            else{
                lstOrder=orderRepository.findOrderOrderByPaymentDesc();
            }
        }
        List<Order>lstCopyOrder=new ArrayList<>();
        for (int i=0;i<lstOrder.size();i++){
            for (int j=0;j<lstOr.size();j++){
                if(lstOrder.get(i).getId()==lstOr.get(j).getId()){
                    lstCopyOrder.add(lstOr.get(j));
                    break;
                }
            }
        }
        return lstCopyOrder;
    }
}
