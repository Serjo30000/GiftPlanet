package ru.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shop.entity.OrderProduct;
import ru.shop.entity.Product;
import ru.shop.entity.ProductSize;
import ru.shop.repository.OrderRepository;
import ru.shop.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService{
    @Autowired
    ProductRepository productRepository;
    public Product findProductById(int productId){
        Optional<Product> productFromDb = productRepository.findById(productId);
        return productFromDb.orElse(new Product());
    }
    @Override
    public Product findProductByName(String productName){
        Product productFromDb = productRepository.findByNameProduct(productName);
        return productFromDb;
    }
    public List<Product> allProducts() {
        return (List<Product>) productRepository.findAll();
    }
    public boolean deleteProduct(int productId) {
        if (productRepository.findById(productId).isPresent()) {
            productRepository.deleteById(productId);
            return true;
        }
        return false;
    }
    public boolean saveProduct(Product product) {
        Product productFromDB = productRepository.findByNameProduct(product.getNameProduct());
        if (productFromDB != null) {
            return false;
        }
        if (product == null) {
            return false;
        }
        productRepository.save(product);
        return true;
    }
    @Override
    public boolean updateProduct(Product product) {
        if (product == null) {
            return true;
        }
        Product productToUpdate = productRepository.findById(product.getId()).get();
        productToUpdate.setNameProduct(product.getNameProduct());
        productToUpdate.setDescription(product.getDescription());
        productToUpdate.setPhotoProduct(product.getPhotoProduct());
        productToUpdate.setGroupProduct(product.getGroupProduct());
        productToUpdate.setMaterial(product.getMaterial());
        productRepository.updateProduct(productToUpdate.getId(),productToUpdate.getNameProduct(),productToUpdate.getDescription(),productToUpdate.getPhotoProduct(),productToUpdate.getGroupProduct(),productToUpdate.getMaterial());
        return false;
    }
    @Override
    public List<ProductSize> allProductSizes(int productId) {
        return productRepository.findProductSizeByAll(productId);
    }
    @Override
    public List<Product> allProductsByGroup(String groupProduct) {
        return productRepository.findProductByGroup(groupProduct);
    }

    @Override
    public List<Product> sortProduct(List<Product>lstPr,String sortName,String sortBy){
        List<Product> lstProduct = new ArrayList<>();
        if (sortName.equals("Отсутствует")){
            return lstPr;
        }
        if (sortName.equals("По номеру")){
            if (sortBy.equals("По возрастанию")){
                lstProduct=productRepository.findProductOrderByIdAsc();
            }
            else{
                lstProduct=productRepository.findProductOrderByIdDesc();
            }
        }
        if (sortName.equals("По названию")){
            if (sortBy.equals("По возрастанию")){
                lstProduct=productRepository.findProductOrderByNameAsc();
            }
            else{
                lstProduct=productRepository.findProductOrderByNameDesc();
            }
        }
        if (sortName.equals("По группе")){
            if (sortBy.equals("По возрастанию")){
                lstProduct=productRepository.findProductOrderByGroupAsc();
            }
            else{
                lstProduct=productRepository.findProductOrderByGroupDesc();
            }
        }
        if (sortName.equals("По материалу")){
            if (sortBy.equals("По возрастанию")){
                lstProduct=productRepository.findProductOrderByMaterialAsc();
            }
            else{
                lstProduct=productRepository.findProductOrderByMaterialDesc();
            }
        }
        if (sortName.equals("По цене")){
            if (sortBy.equals("По возрастанию")){
                lstProduct=productRepository.findProductOrderByPriceAsc();
            }
            else{
                lstProduct=productRepository.findProductOrderByPriceDesc();
            }
        }
        if (sortName.equals("По скидке")){
            if (sortBy.equals("По возрастанию")){
                lstProduct=productRepository.findProductOrderByPriceAsc();
            }
            else{
                lstProduct=productRepository.findProductOrderByPriceDesc();
            }
        }
        List<Product>lstCopyProduct=new ArrayList<>();
        for (int i=0;i<lstProduct.size();i++){
            for (int j=0;j<lstPr.size();j++){
                if(lstProduct.get(i).getId()==lstPr.get(j).getId()){
                    lstCopyProduct.add(lstPr.get(j));
                    break;
                }
            }
        }
        return lstCopyProduct;
    }
    @Override
    public double minPriceProduct(int productId){
        double minPrice=99999999;
        for (ProductSize prSz:productRepository.findProductSizeByAll(productId)){
            if (minPrice>prSz.getPrice()){
                minPrice=prSz.getPrice();
            }
        }
        if (minPrice==99999999 && productRepository.findProductSizeByAll(productId).size()==0){
            minPrice=0;
        }
        return minPrice;
    }

    @Override
    public boolean isDiscountProduct(int productId){
        boolean fl=false;
        for (ProductSize prSz:productRepository.findProductSizeByAll(productId)){
            if (prSz.getDiscount()>0){
                fl=true;
                break;
            }
        }
        return fl;
    }
}
