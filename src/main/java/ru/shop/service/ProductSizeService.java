package ru.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shop.entity.Product;
import ru.shop.entity.ProductSize;
import ru.shop.repository.ProductSizeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductSizeService implements IProductSizeService {
    @Autowired
    ProductSizeRepository productSizeRepository;
    public ProductSize findProductSizeById(int productSizeId){
        Optional<ProductSize> productSizeFromDb = productSizeRepository.findById(productSizeId);
        return productSizeFromDb.orElse(new ProductSize());
    }
    public List<ProductSize> allProductSizes() {
        return (List<ProductSize>) productSizeRepository.findAll();
    }
    public boolean deleteProductSize(int productSizeId) {
        if (productSizeRepository.findById(productSizeId).isPresent()) {
            productSizeRepository.deleteById(productSizeId);
            return true;
        }
        return false;
    }
    public boolean saveProductSize(ProductSize productSize) {
        if (productSize == null) {
            return false;
        }
        productSizeRepository.save(productSize);
        return true;
    }
    @Override
    public boolean updateProductSize(ProductSize productSize) {
        if (productSize == null) {
            return true;
        }
        ProductSize productSizeToUpdate = productSizeRepository.findById(productSize.getId()).get();
        productSizeToUpdate.setPrice(productSize.getPrice());
        productSizeToUpdate.setSizeProduct(productSize.getSizeProduct());
        productSizeToUpdate.setProductId(productSize.getProductId());
        productSizeToUpdate.setDiscount(productSize.getDiscount());
        productSizeRepository.updateProductSize(productSizeToUpdate.getId(),productSizeToUpdate.getPrice(),productSizeToUpdate.getSizeProduct(),productSizeToUpdate.getProductId(),productSizeToUpdate.getDiscount());
        return false;
    }
    @Override
    public Product findProductByProductSizeId(int productSizeId) {
        Optional<Product> productFromDb = productSizeRepository.findProductById(productSizeId);
        return productFromDb.orElse(new Product());
    }

    @Override
    public List<ProductSize> sortProductSize(List<ProductSize>lstPrSz,String sortName,String sortBy){
        List<ProductSize> lstProductSize = new ArrayList<>();
        if (sortName.equals("Отсутствует")){
            return lstPrSz;
        }
        if (sortName.equals("По номеру")){
            if (sortBy.equals("По возрастанию")){
                lstProductSize=productSizeRepository.findProductSizeOrderByIdAsc();
            }
            else{
                lstProductSize=productSizeRepository.findProductSizeOrderByIdDesc();
            }
        }
        if (sortName.equals("По цене")){
            if (sortBy.equals("По возрастанию")){
                lstProductSize=productSizeRepository.findProductSizeOrderByPriceAsc();
            }
            else{
                lstProductSize=productSizeRepository.findProductSizeOrderByPriceDesc();
            }
        }
        if (sortName.equals("По скидке")){
            if (sortBy.equals("По возрастанию")){
                lstProductSize=productSizeRepository.findProductSizeOrderByDiscountAsc();
            }
            else{
                lstProductSize=productSizeRepository.findProductSizeOrderByDiscountDesc();
            }
        }
        if (sortName.equals("По размеру")){
            if (sortBy.equals("По возрастанию")){
                lstProductSize=productSizeRepository.findProductSizeOrderBySizeAsc();
            }
            else{
                lstProductSize=productSizeRepository.findProductSizeOrderBySizeDesc();
            }
        }
        if (sortName.equals("По номеру товара")){
            if (sortBy.equals("По возрастанию")){
                lstProductSize=productSizeRepository.findProductSizeOrderByProductIdAsc();
            }
            else{
                lstProductSize=productSizeRepository.findProductSizeOrderByProductIdDesc();
            }
        }
        List<ProductSize>lstCopyProductSize=new ArrayList<>();
        for (int i=0;i<lstProductSize.size();i++){
            for (int j=0;j<lstPrSz.size();j++){
                if(lstProductSize.get(i).getId()==lstPrSz.get(j).getId()){
                    lstCopyProductSize.add(lstPrSz.get(j));
                    break;
                }
            }
        }
        return lstCopyProductSize;
    }
}
