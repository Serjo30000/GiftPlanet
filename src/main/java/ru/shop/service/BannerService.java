package ru.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shop.entity.Banner;
import ru.shop.entity.Order;
import ru.shop.repository.BannerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BannerService implements IBannerService {
    @Autowired
    BannerRepository bannerRepository;
    public Banner findBannerById(int bannerId){
        Optional<Banner> bannerFromDb = bannerRepository.findById(bannerId);
        return bannerFromDb.orElse(new Banner());
    }
    public List<Banner> allBanners() {
        return (List<Banner>) bannerRepository.findAll();
    }
    public boolean deleteBanner(int bannerId) {
        if (bannerRepository.findById(bannerId).isPresent()) {
            bannerRepository.deleteById(bannerId);
            return true;
        }
        return false;
    }
    public boolean saveBanner(Banner banner) {
        if (banner == null) {
            return false;
        }
        bannerRepository.save(banner);
        return true;
    }
    @Override
    public boolean updateBanner(Banner banner) {
        if (banner == null) {
            return true;
        }
        Banner bannerToUpdate = bannerRepository.findById(banner.getId()).get();
        bannerToUpdate.setName(banner.getName());
        bannerToUpdate.setLink(banner.getLink());
        bannerRepository.updateBanner(bannerToUpdate.getId(),bannerToUpdate.getName(),bannerToUpdate.getLink());
        return false;
    }

    @Override
    public List<Banner> sortBanner(List<Banner>lstBn, String sortName, String sortBy){
        List<Banner> lstBanner = new ArrayList<>();
        if (sortName.equals("Отсутствует")){
            return lstBn;
        }
        if (sortName.equals("По номеру")){
            if (sortBy.equals("По возрастанию")){
                lstBanner=bannerRepository.findBannerOrderByIdAsc();
            }
            else{
                lstBanner=bannerRepository.findBannerOrderByIdDesc();
            }
        }
        if (sortName.equals("По названию")){
            if (sortBy.equals("По возрастанию")){
                lstBanner=bannerRepository.findBannerOrderByNameAsc();
            }
            else{
                lstBanner=bannerRepository.findBannerOrderByNameDesc();
            }
        }
        List<Banner>lstCopyBanner=new ArrayList<>();
        for (int i=0;i<lstBanner.size();i++){
            for (int j=0;j<lstBn.size();j++){
                if(lstBanner.get(i).getId()==lstBn.get(j).getId()){
                    lstCopyBanner.add(lstBn.get(j));
                    break;
                }
            }
        }
        return lstCopyBanner;
    }
}
