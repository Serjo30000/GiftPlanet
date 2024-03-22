package ru.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shop.entity.Catalog;
import ru.shop.repository.CatalogRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CatalogService implements ICatalogService {
    @Autowired
    CatalogRepository catalogRepository;
    public Catalog findCatalogById(int catalogId){
        Optional<Catalog> catalogFromDb = catalogRepository.findById(catalogId);
        return catalogFromDb.orElse(new Catalog());
    }
    public List<Catalog> allCatalogs() {
        return (List<Catalog>) catalogRepository.findAll();
    }
    public boolean deleteCatalog(int catalogId) {
        if (catalogRepository.findById(catalogId).isPresent()) {
            catalogRepository.deleteById(catalogId);
            return true;
        }
        return false;
    }
    public boolean saveCatalog(Catalog catalog) {
        if (catalog == null) {
            return false;
        }
        catalogRepository.save(catalog);
        return true;
    }
    @Override
    public boolean updateCatalog(Catalog catalog) {
        if (catalog == null) {
            return true;
        }
        Catalog catalogToUpdate = catalogRepository.findById(catalog.getId()).get();
        catalogToUpdate.setName(catalog.getName());
        catalogToUpdate.setIcon(catalog.getIcon());
        catalogToUpdate.setImg(catalog.getImg());
        catalogRepository.updateCatalog(catalogToUpdate.getId(),catalogToUpdate.getName(),catalogToUpdate.getIcon(),catalog.getImg());
        return false;
    }

    @Override
    public List<Catalog> sortCatalog(List<Catalog>lstCt, String sortName, String sortBy){
        List<Catalog> lstCatalog = new ArrayList<>();
        if (sortName.equals("Отсутствует")){
            return lstCt;
        }
        if (sortName.equals("По номеру")){
            if (sortBy.equals("По возрастанию")){
                lstCatalog=catalogRepository.findCatalogOrderByIdAsc();
            }
            else{
                lstCatalog=catalogRepository.findCatalogOrderByIdDesc();
            }
        }
        if (sortName.equals("По названию")){
            if (sortBy.equals("По возрастанию")){
                lstCatalog=catalogRepository.findCatalogOrderByNameAsc();
            }
            else{
                lstCatalog=catalogRepository.findCatalogOrderByNameDesc();
            }
        }
        List<Catalog>lstCopyCatalog=new ArrayList<>();
        for (int i=0;i<lstCatalog.size();i++){
            for (int j=0;j<lstCt.size();j++){
                if(lstCatalog.get(i).getId()==lstCt.get(j).getId()){
                    lstCopyCatalog.add(lstCt.get(j));
                    break;
                }
            }
        }
        return lstCopyCatalog;
    }
}
