package ru.shop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/howToOrder").setViewName("howToOrder");
        registry.addViewController("/company").setViewName("company");
        registry.addViewController("/contacts").setViewName("contacts");
        registry.addViewController("/catalog").setViewName("catalog");
        registry.addViewController("/catalog/catalogProduct").setViewName("catalogProduct");
        registry.addViewController("/catalog/catalogProduct/viewCatalogProduct").setViewName("viewCatalogProduct");
        registry.addViewController("/basket").setViewName("basket");
        registry.addViewController("/basket/buyOrder").setViewName("buyOrder");
        registry.addViewController("/adminPanel").setViewName("adminPanel");
        registry.addViewController("/adminPanel/adminUser").setViewName("adminUser");
        registry.addViewController("/adminPanel/adminUser/registration").setViewName("registration");
        registry.addViewController("/adminPanel/adminUser/viewAdminUser").setViewName("viewAdminUser");
        registry.addViewController("/adminPanel/adminStatistics").setViewName("adminStatistics");
        registry.addViewController("/adminPanel/adminProduct").setViewName("adminProduct");
        registry.addViewController("/adminPanel/adminProduct/createAdminProduct").setViewName("createAdminProduct");
        registry.addViewController("/adminPanel/adminProduct/editAdminProduct").setViewName("editAdminProduct");
        registry.addViewController("/adminPanel/adminProduct/viewAdminProduct").setViewName("viewAdminProduct");
        registry.addViewController("/adminPanel/adminOrder").setViewName("adminOrder");
        registry.addViewController("/adminPanel/adminOrder/viewAdminOrder").setViewName("viewAdminOrder");
        registry.addViewController("/adminPanel/adminOrder/editAdminOrder").setViewName("editAdminOrder");
        registry.addViewController("/adminPanel/adminOrder/adminOrderProduct").setViewName("adminOrderProduct");
        registry.addViewController("/adminPanel/adminOrder/adminOrderProduct/viewAdminOrderProduct").setViewName("viewAdminOrderProduct");
        registry.addViewController("/adminPanel/adminProduct/adminProductSize").setViewName("adminProductSize");
        registry.addViewController("/adminPanel/adminProduct/adminProductSize/viewAdminProductSize").setViewName("viewAdminProductSize");
        registry.addViewController("/adminPanel/adminProduct/adminProductSize/createAdminProductSize").setViewName("createAdminProductSize");
        registry.addViewController("/adminPanel/adminProduct/adminProductSize/editAdminProductSize").setViewName("editAdminProductSize");
        registry.addViewController("/adminPanel/adminBanner").setViewName("adminBanner");
        registry.addViewController("/adminPanel/adminBanner/createAdminBanner").setViewName("createAdminBanner");
        registry.addViewController("/adminPanel/adminBanner/viewAdminBanner").setViewName("viewAdminBanner");
        registry.addViewController("/adminPanel/adminCatalog").setViewName("adminCatalog");
        registry.addViewController("/adminPanel/adminCatalog/createAdminCatalog").setViewName("createAdminCatalog");
        registry.addViewController("/adminPanel/adminCatalog/viewAdminCatalog").setViewName("viewAdminCatalog");
    }
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**").addResourceLocations("file:uploads/");
    }
}
