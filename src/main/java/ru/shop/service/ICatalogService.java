package ru.shop.service;

import org.springframework.stereotype.Service;
import ru.shop.entity.Catalog;

import java.util.List;

@Service
public interface ICatalogService {
    boolean updateCatalog(Catalog catalog);
    List<Catalog> sortCatalog(List<Catalog>lstCt, String sortName, String sortBy);
}
