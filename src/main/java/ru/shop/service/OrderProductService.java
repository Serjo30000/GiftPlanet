package ru.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shop.entity.Order;
import ru.shop.entity.OrderProduct;
import ru.shop.entity.ProductSize;
import ru.shop.repository.OrderProductRepository;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderProductService implements IOrderProductService {
    @Autowired
    OrderProductRepository orderProductRepository;
    public OrderProduct findOrderProductById(int orderProductId){
        Optional<OrderProduct> orderProductFromDb = orderProductRepository.findById(orderProductId);
        return orderProductFromDb.orElse(new OrderProduct());
    }
    public List<OrderProduct> allOrderProducts() {
        return (List<OrderProduct>) orderProductRepository.findAll();
    }
    public boolean deleteOrderProduct(int orderProductId) {
        if (orderProductRepository.findById(orderProductId).isPresent()) {
            orderProductRepository.deleteById(orderProductId);
            return true;
        }
        return false;
    }
    public boolean saveOrderProduct(OrderProduct orderProduct) {
        if (orderProduct == null) {
            return false;
        }
        orderProductRepository.save(orderProduct);
        return true;
    }
    @Override
    public boolean updateOrderProduct(OrderProduct orderProduct) {
        if (orderProduct == null) {
            return true;
        }
        OrderProduct orderProductToUpdate = orderProductRepository.findById(orderProduct.getId()).get();
        orderProductToUpdate.setNameProduct(orderProduct.getNameProduct());
        orderProductToUpdate.setDescription(orderProduct.getDescription());
        orderProductToUpdate.setPhotoProduct(orderProduct.getPhotoProduct());
        orderProductToUpdate.setPrice(orderProduct.getPrice());
        orderProductToUpdate.setDiscount(orderProduct.getDiscount());
        orderProductToUpdate.setGroupProduct(orderProduct.getGroupProduct());
        orderProductToUpdate.setSizeProduct(orderProduct.getSizeProduct());
        orderProductToUpdate.setMaterial(orderProduct.getMaterial());
        orderProductToUpdate.setCount(orderProduct.getCount());
        orderProductToUpdate.setOrderId(orderProduct.getOrderId());
        orderProductRepository.updateOrderProduct(orderProductToUpdate.getId(),orderProductToUpdate.getNameProduct(),orderProductToUpdate.getDescription(),orderProductToUpdate.getPhotoProduct(),orderProductToUpdate.getPrice(),orderProductToUpdate.getDiscount(),orderProductToUpdate.getGroupProduct(),orderProductToUpdate.getSizeProduct(),orderProductToUpdate.getMaterial(),orderProductToUpdate.getCount(),orderProductToUpdate.getOrderId());
        return false;
    }
    @Override
    public Order findOrderByOrderProductId(int orderProductId) {
        Optional<Order> orderFromDb = orderProductRepository.findOrderById(orderProductId);
        return orderFromDb.orElse(new Order());
    }

    @Override
    public List<OrderProduct> sortOrderProduct(List<OrderProduct>lstOrPr, String sortName, String sortBy){
        List<OrderProduct> lstOrderProduct = new ArrayList<>();
        if (sortName.equals("Отсутствует")){
            return lstOrPr;
        }
        if (sortName.equals("По номеру")){
            if (sortBy.equals("По возрастанию")){
                lstOrderProduct=orderProductRepository.findOrderProductOrderByIdAsc();
            }
            else{
                lstOrderProduct=orderProductRepository.findOrderProductOrderByIdDesc();
            }
        }
        if (sortName.equals("По названию")){
            if (sortBy.equals("По возрастанию")){
                lstOrderProduct=orderProductRepository.findOrderProductOrderByNameAsc();
            }
            else{
                lstOrderProduct=orderProductRepository.findOrderProductOrderByNameDesc();
            }
        }
        if (sortName.equals("По цене")){
            if (sortBy.equals("По возрастанию")){
                lstOrderProduct=orderProductRepository.findOrderProductOrderByPriceAsc();
            }
            else{
                lstOrderProduct=orderProductRepository.findOrderProductOrderByPriceDesc();
            }
        }
        if (sortName.equals("По скидке")){
            if (sortBy.equals("По возрастанию")){
                lstOrderProduct=orderProductRepository.findOrderProductOrderByDiscountAsc();
            }
            else{
                lstOrderProduct=orderProductRepository.findOrderProductOrderByDiscountDesc();
            }
        }
        if (sortName.equals("По группе")){
            if (sortBy.equals("По возрастанию")){
                lstOrderProduct=orderProductRepository.findOrderProductOrderByGroupAsc();
            }
            else{
                lstOrderProduct=orderProductRepository.findOrderProductOrderByGroupDesc();
            }
        }
        if (sortName.equals("По размеру")){
            if (sortBy.equals("По возрастанию")){
                lstOrderProduct=orderProductRepository.findOrderProductOrderBySizeAsc();
            }
            else{
                lstOrderProduct=orderProductRepository.findOrderProductOrderBySizeDesc();
            }
        }
        if (sortName.equals("По материалу")){
            if (sortBy.equals("По возрастанию")){
                lstOrderProduct=orderProductRepository.findOrderProductOrderByMaterialAsc();
            }
            else{
                lstOrderProduct=orderProductRepository.findOrderProductOrderByMaterialDesc();
            }
        }
        if (sortName.equals("По количеству")){
            if (sortBy.equals("По возрастанию")){
                lstOrderProduct=orderProductRepository.findOrderProductOrderByCountAsc();
            }
            else{
                lstOrderProduct=orderProductRepository.findOrderProductOrderByCountDesc();
            }
        }
        if (sortName.equals("По номеру заказа")){
            if (sortBy.equals("По возрастанию")){
                lstOrderProduct=orderProductRepository.findOrderProductOrderByOrderIdAsc();
            }
            else{
                lstOrderProduct=orderProductRepository.findOrderProductOrderByOrderIdDesc();
            }
        }
        List<OrderProduct>lstCopyOrderProduct=new ArrayList<>();
        for (int i=0;i<lstOrderProduct.size();i++){
            for (int j=0;j<lstOrPr.size();j++){
                if(lstOrderProduct.get(i).getId()==lstOrPr.get(j).getId()){
                    lstCopyOrderProduct.add(lstOrPr.get(j));
                    break;
                }
            }
        }
        return lstCopyOrderProduct;
    }
    @Override
    public double priceWithDiscount(double price, double discount){
        if (discount<=0){
            return price;
        }
        else{
            DecimalFormat decimalFormat = new DecimalFormat( "#.##" );
            String resultFormat = decimalFormat.format( price-(price*discount/100));
            resultFormat = resultFormat.replaceAll(",",".");
            double resultDouble = Double.parseDouble(resultFormat);
            return resultDouble;
        }
    }

    @Override
    public double allPriceWithDiscount(List<OrderProduct>lstOrPr){
        double srSum=0;
        for(int i=0;i<lstOrPr.size();i++){
            srSum+=(lstOrPr.get(i).getPrice()-(lstOrPr.get(i).getPrice()*lstOrPr.get(i).getDiscount()/100))*lstOrPr.get(i).getCount();
        }
        DecimalFormat decimalFormat = new DecimalFormat( "#.##" );
        String resultFormat = decimalFormat.format(srSum);
        resultFormat = resultFormat.replaceAll(",",".");
        double resultDouble = Double.parseDouble(resultFormat);
        return resultDouble;
    }
}
