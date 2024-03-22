package ru.shop.service;

import org.springframework.stereotype.Service;
import ru.shop.entity.Banner;
import ru.shop.entity.OrderProduct;

import java.util.List;

@Service
public interface IBannerService {
    boolean updateBanner(Banner banner);
    List<Banner> sortBanner(List<Banner>lstBn, String sortName, String sortBy);
}
