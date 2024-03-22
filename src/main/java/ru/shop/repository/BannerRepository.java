package ru.shop.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.shop.entity.Banner;
import ru.shop.entity.Order;
import ru.shop.entity.OrderProduct;

import java.sql.Date;
import java.util.List;

@Repository
public interface BannerRepository extends CrudRepository<Banner,Integer> {
    @Query("SELECT * FROM banners ORDER BY id ASC")
    List<Banner> findBannerOrderByIdAsc();
    @Query("SELECT * FROM banners ORDER BY id DESC")
    List<Banner> findBannerOrderByIdDesc();
    @Query("SELECT * FROM banners ORDER BY name ASC")
    List<Banner> findBannerOrderByNameAsc();
    @Query("SELECT * FROM banners ORDER BY name DESC")
    List<Banner> findBannerOrderByNameDesc();
    @Modifying
    @Query("UPDATE banners SET name=:name, link=:link WHERE id=:id")
    void updateBanner(@Param("id")int id, @Param("name")String name, @Param("link")String link);
}
