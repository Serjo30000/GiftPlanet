package ru.shop.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.shop.entity.Catalog;

import java.util.List;

@Repository
public interface CatalogRepository extends CrudRepository<Catalog,Integer> {
    @Query("SELECT * FROM catalogs ORDER BY id ASC")
    List<Catalog> findCatalogOrderByIdAsc();
    @Query("SELECT * FROM catalogs ORDER BY id DESC")
    List<Catalog> findCatalogOrderByIdDesc();
    @Query("SELECT * FROM catalogs ORDER BY name ASC")
    List<Catalog> findCatalogOrderByNameAsc();
    @Query("SELECT * FROM catalogs ORDER BY name DESC")
    List<Catalog> findCatalogOrderByNameDesc();
    @Modifying
    @Query("UPDATE catalogs SET name=:name, icon=:icon, img=:img WHERE id=:id")
    void updateCatalog(@Param("id")int id, @Param("name")String name, @Param("icon")String icon, @Param("img")String img);
}
