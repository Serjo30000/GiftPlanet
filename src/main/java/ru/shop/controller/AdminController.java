package ru.shop.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.shop.entity.*;
import ru.shop.service.*;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderProductService orderProductService;
    @Autowired
    private ProductSizeService productSizeService;
    @Autowired
    private BannerService bannerService;
    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private CatalogService catalogService;
    @GetMapping("/adminPanel")
    public String adminPanelMenu(Model model) {
        return "adminPanel";
    }
    @GetMapping("/adminPanel/adminUser")
    public String adminUserList(Model model) {
        model.addAttribute("allUsers", userService.allUsers());
        return "adminUser";
    }
    @PostMapping(value = "/adminPanel/adminUser", params = { "sortName", "sortBy" })
    public String adminSortUserList(@RequestParam(required = true, defaultValue = "",value="sortName") String sortName,
                                       @RequestParam(required = true, defaultValue = "",value="sortBy") String sortBy,
                                       @RequestParam(required = false, value="lstUs") List<User> lstUs, Model model) {
        if (lstUs==null){
            lstUs=new ArrayList<>();
        }
        List<User>lstT=userService.sortUser(lstUs,sortName,sortBy);
        model.addAttribute("allUsers", lstT);
        return "adminUser";
    }
    @PostMapping(value = "/adminPanel/adminUser")
    public String adminResetUserList( Model model) {
        return "redirect:adminUser";
    }
    @GetMapping("/adminPanel/adminUser/registration")
    public String adminGetCreateUser(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }
    @PostMapping("/adminPanel/adminUser/registration")
    public String adminPostCreateUser(@ModelAttribute("userForm") @Valid User userForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())){
            return "redirect:/adminPanel/adminUser/registration?error=true";
        }
        if (!userService.saveUser(userForm)){
            return "redirect:/adminPanel/adminUser/registration?error=true";
        }

        return "redirect:/adminPanel/adminUser";
    }
    @PostMapping(value="/adminPanel/adminUser", params = { "userId" })
    public String  adminDeleteUser(@RequestParam(required = true ) int userId,
                              @RequestParam(required = true, defaultValue = "" ) String action,
                              Model model) {
        if (action.equals("delete")){
            userService.deleteUser(userId);
        }
        return "redirect:/adminPanel/adminUser";
    }
    @GetMapping("/adminPanel/adminUser/viewAdminUser")
    public String adminGetViewUser(@RequestParam(required = true ) int userId,Model model) {
        model.addAttribute("userForm", userService.findUserById(userId));
        return "viewAdminUser";
    }
    @GetMapping("/adminPanel/adminProduct")
    public String adminProductList(Model model) {
        model.addAttribute("allProducts", productService.allProducts());
        return "adminProduct";
    }
    @PostMapping(value = "/adminPanel/adminProduct", params = { "sortName", "sortBy" })
    public String adminSortProductList(@RequestParam(required = true, defaultValue = "",value="sortName") String sortName,
                                       @RequestParam(required = true, defaultValue = "",value="sortBy") String sortBy,
                                       @RequestParam(required = false, value="lstPr") List<Product> lstPr, Model model) {
        if (lstPr==null){
            lstPr=new ArrayList<>();
        }
        List<Product>lstT=productService.sortProduct(lstPr,sortName,sortBy);
        model.addAttribute("allProducts", lstT);
        return "adminProduct";
    }
    @PostMapping(value = "/adminPanel/adminProduct")
    public String adminResetProductList( Model model) {
        return "redirect:adminProduct";
    }
    @GetMapping("/adminPanel/adminProduct/createAdminProduct")
    public String adminGetCreateProduct(Model model) {
        model.addAttribute("productForm", new Product());
        model.addAttribute("AllCatalogs", catalogService.allCatalogs());
        return "createAdminProduct";
    }
    @PostMapping("/adminPanel/adminProduct/createAdminProduct")
    public String adminPostCreateProduct(@ModelAttribute("productForm") @Valid Product productForm, @RequestParam(required = true ) MultipartFile imgFile, BindingResult bindingResult, Model model) throws IOException {
        model.addAttribute("AllCatalogs", catalogService.allCatalogs());
        if (bindingResult.hasErrors()) {
            return "createAdminProduct";
        }
        productForm.setPhotoProduct(imgFile.getOriginalFilename());
        InputStream is = new ByteArrayInputStream(imgFile.getBytes());
        BufferedImage img = ImageIO.read(is);
        for(Product pr:productService.allProducts()){
            if (pr.getPhotoProduct().equals(imgFile.getOriginalFilename())&&pr.getId()!=productForm.getId()){
                return "redirect:/adminPanel/adminProduct/createAdminProduct?error=true";
            }
        }
        for(Banner bn:bannerService.allBanners()){
            if (bn.getName().equals(imgFile.getOriginalFilename())){
                return "redirect:/adminPanel/adminProduct/createAdminProduct?error=true";
            }
        }
        for(Catalog ct:catalogService.allCatalogs()){
            if (ct.getImg().equals(imgFile.getOriginalFilename())||ct.getIcon().equals(imgFile.getOriginalFilename())){
                return "redirect:/adminPanel/adminProduct/createAdminProduct?error=true";
            }
        }
        if (!productService.saveProduct(productForm)){
            return "redirect:/adminPanel/adminProduct/createAdminProduct?error=true";
        }
        ImageIO.write(img,"png",
                new File(".\\src\\main\\resources\\static\\img\\images\\"+
                        productForm.getPhotoProduct()));
        ImageIO.write(img,"png",
                new File(".\\uploads\\img\\images\\"+
                        productForm.getPhotoProduct()));
        return "redirect:/adminPanel/adminProduct";
    }
    @PostMapping(value="/adminPanel/adminProduct", params = { "productId" })
    public String  adminDeleteProduct(@RequestParam(required = true ) int productId,
                                   @RequestParam(required = true, defaultValue = "" ) String action,
                                   Model model) {
        if (action.equals("delete")){
            File file = new File(".\\src\\main\\resources\\static\\img\\images\\"+
                    productService.findProductById(productId).getPhotoProduct());
            file.delete();
            file.exists();
            File fileUpload = new File(".\\uploads\\img\\images\\"+
                    productService.findProductById(productId).getPhotoProduct());
            fileUpload.delete();
            fileUpload.exists();
            for (OrderProduct orPr:orderProductService.allOrderProducts()){
                if (orPr.getPhotoProduct().equals(productService.findProductById(productId).getPhotoProduct())){
                    orPr.setPhotoProduct("defaultImg.png");
                    orderProductService.updateOrderProduct(orPr);
                }
            }
            for (ProductSize prSz:productService.allProductSizes(productId)){
                productSizeService.deleteProductSize(prSz.getId());
            }
            productService.deleteProduct(productId);
        }
        return "redirect:/adminPanel/adminProduct";
    }
    @GetMapping("/adminPanel/adminProduct/viewAdminProduct")
    public String adminGetViewProduct(@RequestParam(required = true ) int productId,Model model) {
        model.addAttribute("productForm", productService.findProductById(productId));
        model.addAttribute("linkImg", "/img/images/"+productService.findProductById(productId).getPhotoProduct());
        return "viewAdminProduct";
    }
    @GetMapping("/adminPanel/adminProduct/editAdminProduct")
    public String adminGetEditProduct(@RequestParam(required = true ) int productId,Model model) throws IOException {
        model.addAttribute("productForm", productService.findProductById(productId));
        model.addAttribute("fileImg", new File(".\\img\\images\\" + productService.findProductById(productId).getPhotoProduct()));
        model.addAttribute("AllCatalogs", catalogService.allCatalogs());
        return "editAdminProduct";
    }
    @PostMapping("/adminPanel/adminProduct/editAdminProduct")
    public String adminPostEditProduct(@ModelAttribute("productForm") @Valid Product productForm, @RequestParam(required = true ) MultipartFile imgFile, BindingResult bindingResult, Model model) throws IOException {
        model.addAttribute("AllCatalogs", catalogService.allCatalogs());
        if (bindingResult.hasErrors()) {
            return "editAdminProduct";
        }
        for(Product pr:productService.allProducts()){
            if ((pr.getNameProduct().equals(productForm.getNameProduct())||pr.getPhotoProduct().equals(imgFile.getOriginalFilename()))&&pr.getId()!=productForm.getId()){
                return "redirect:/adminPanel/adminProduct/editAdminProduct?productId="+productForm.getId()+"&error=true";
            }
        }
        for(Banner bn:bannerService.allBanners()){
            if (bn.getName().equals(imgFile.getOriginalFilename())){
                return "redirect:/adminPanel/adminProduct/editAdminProduct?productId="+productForm.getId()+"&error=true";
            }
        }
        for(Catalog ct:catalogService.allCatalogs()){
            if (ct.getImg().equals(imgFile.getOriginalFilename())||ct.getIcon().equals(imgFile.getOriginalFilename())){
                return "redirect:/adminPanel/adminProduct/editAdminProduct?productId="+productForm.getId()+"&error=true";
            }
        }
        String oldPhotoName=productForm.getPhotoProduct();
        if (!imgFile.getOriginalFilename().equals("")){
            productForm.setPhotoProduct(imgFile.getOriginalFilename());
            InputStream is = new ByteArrayInputStream(imgFile.getBytes());
            BufferedImage img = ImageIO.read(is);
            File file = new File(".\\src\\main\\resources\\static\\img\\images\\"+
                    productService.findProductById(productForm.getId()).getPhotoProduct());
            file.delete();
            file.exists();
            File fileUpload = new File(".\\uploads\\img\\images\\"+
                    productService.findProductById(productForm.getId()).getPhotoProduct());
            fileUpload.delete();
            fileUpload.exists();
            productForm.setPhotoProduct(imgFile.getOriginalFilename());
            ImageIO.write(img,"png",
                    new File(".\\src\\main\\resources\\static\\img\\images\\"+
                            productForm.getPhotoProduct()));
            ImageIO.write(img,"png",
                    new File(".\\uploads\\img\\images\\"+
                            productForm.getPhotoProduct()));
        }
        if (!productService.updateProduct(productForm)){
            for (OrderProduct orPr:orderProductService.allOrderProducts()){
                if (orPr.getPhotoProduct().equals(oldPhotoName)){
                    orPr.setPhotoProduct(imgFile.getOriginalFilename());
                    orderProductService.updateOrderProduct(orPr);
                }
            }
            return "redirect:/adminPanel/adminProduct";
        }
        return "redirect:/adminPanel/adminProduct";
    }
    @GetMapping("/adminPanel/adminOrder")
    public String adminOrderList(Model model) {
        model.addAttribute("allOrders", orderService.allOrders());
        model.addAttribute("orderServ", orderService);
        return "adminOrder";
    }
    @PostMapping(value = "/adminPanel/adminOrder", params = { "sortName", "sortBy" })
    public String adminSortOrderList(@RequestParam(required = true, defaultValue = "",value="sortName") String sortName,
                                    @RequestParam(required = true, defaultValue = "",value="sortBy") String sortBy,
                                    @RequestParam(required = false, value="lstOr") List<Order> lstOr, Model model) {
        if (lstOr==null){
            lstOr=new ArrayList<>();
        }
        List<Order>lstT=orderService.sortOrder(lstOr,sortName,sortBy);
        model.addAttribute("allOrders", lstT);
        model.addAttribute("orderServ", orderService);
        return "adminOrder";
    }
    @PostMapping(value = "/adminPanel/adminOrder")
    public String adminResetOrderList( Model model) {
        return "redirect:adminOrder";
    }
    @PostMapping(value="/adminPanel/adminOrder", params = { "orderId" })
    public String  adminDeleteOrder(@RequestParam(required = true ) int orderId,
                                      @RequestParam(required = true, defaultValue = "" ) String action,
                                      Model model) {
        if (action.equals("delete")){
            for (OrderProduct orPr:orderService.allOrderProducts(orderId)){
                orderProductService.deleteOrderProduct(orPr.getId());
            }
            orderService.deleteOrder(orderId);
        }
        return "redirect:/adminPanel/adminOrder";
    }
    @GetMapping("/adminPanel/adminOrder/viewAdminOrder")
    public String adminGetViewOrder(@RequestParam(required = true ) int orderId,Model model) {
        model.addAttribute("orderForm", orderService.findOrderById(orderId));
        model.addAttribute("orderServ", orderService);
        return "viewAdminOrder";
    }
    @GetMapping("/adminPanel/adminOrder/editAdminOrder")
    public String adminGetEditOrder(@RequestParam(required = true ) int orderId,Model model){
        model.addAttribute("orderForm", orderService.findOrderById(orderId));
        return "editAdminOrder";
    }
    @PostMapping("/adminPanel/adminOrder/editAdminOrder")
    public String adminPostEditOrder(@ModelAttribute("orderForm") @Valid Order orderForm,@RequestParam(required = true ) String statusText, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "editAdminOrder";
        }
        orderForm.setOrderStatus(statusText);
        if (!orderService.updateOrder(orderForm)){
            return "redirect:/adminPanel/adminOrder";
        }
        return "redirect:/adminPanel/adminOrder";
    }
    @GetMapping("/adminPanel/adminOrder/adminOrderProduct")
    public String adminOrderProductList(@RequestParam(required = true ) int orderId,Model model) {
        model.addAttribute("allOrderProducts", orderService.allOrderProducts(orderId));
        model.addAttribute("undoListCreate", orderId);
        return "adminOrderProduct";
    }
    @PostMapping(value = "/adminPanel/adminOrder/adminOrderProduct", params = { "sortName", "sortBy" })
    public String adminSortOrderProductList(@RequestParam(required = true, defaultValue = "",value="sortName") String sortName,
                                           @RequestParam(required = true, defaultValue = "",value="sortBy") String sortBy,
                                           @RequestParam(required = true ) int orderId,
                                           @RequestParam(required = false, value="lstOrPr") List<OrderProduct> lstOrPr, Model model) {
        if (lstOrPr==null){
            lstOrPr=new ArrayList<>();
        }
        List<OrderProduct>lstT=orderProductService.sortOrderProduct(lstOrPr,sortName,sortBy);
        model.addAttribute("allOrderProducts", lstT);
        model.addAttribute("undoListCreate", orderId);
        return "adminOrderProduct";
    }
    @PostMapping(value = "/adminPanel/adminOrder/adminOrderProduct")
    public String adminResetOrderProductList( Model model,@RequestParam(required = true ) int orderId) {
        return "redirect:/adminPanel/adminOrder/adminOrderProduct?orderId="+orderId;
    }
    @PostMapping(value="/adminPanel/adminOrder/adminOrderProduct", params = { "orderProductId" })
    public String  adminDeleteOrderProduct(@RequestParam(required = true ) int orderProductId,
                                      @RequestParam(required = true, defaultValue = "" ) String action,
                                      Model model) {
        int linkId=orderProductService.findOrderByOrderProductId(orderProductId).getId();
        if (action.equals("delete")){
            orderProductService.deleteOrderProduct(orderProductId);
        }
        return "redirect:/adminPanel/adminOrder/adminOrderProduct?orderId="+linkId;
    }
    @GetMapping("/adminPanel/adminOrder/adminOrderProduct/viewAdminOrderProduct")
    public String adminGetViewOrderProduct(@RequestParam(required = true ) int orderProductId,Model model) {
        model.addAttribute("orderProductForm", orderProductService.findOrderProductById(orderProductId));
        model.addAttribute("linkImg", "/img/images/"+orderProductService.findOrderProductById(orderProductId).getPhotoProduct());
        return "viewAdminOrderProduct";
    }
    @GetMapping("/adminPanel/adminProduct/adminProductSize")
    public String adminProductSizeList(@RequestParam(required = true ) int productId,Model model) {
        model.addAttribute("allProductSizes", productService.allProductSizes(productId));
        model.addAttribute("undoListCreate", productId);
        return "adminProductSize";
    }
    @PostMapping(value = "/adminPanel/adminProduct/adminProductSize", params = { "sortName", "sortBy" })
    public String adminSortProductSizeList(@RequestParam(required = true, defaultValue = "",value="sortName") String sortName,
                                     @RequestParam(required = true, defaultValue = "",value="sortBy") String sortBy,
                                     @RequestParam(required = true ) int productId,
                                     @RequestParam(required = false, value="lstPrSz") List<ProductSize> lstPrSz, Model model) {
        if (lstPrSz==null){
            lstPrSz=new ArrayList<>();
        }
        List<ProductSize>lstT=productSizeService.sortProductSize(lstPrSz,sortName,sortBy);
        model.addAttribute("allProductSizes", lstT);
        model.addAttribute("undoListCreate", productId);
        return "adminProductSize";
    }
    @PostMapping(value = "/adminPanel/adminProduct/adminProductSize")
    public String adminResetProductSizeList( Model model,@RequestParam(required = true ) int productId) {
        return "redirect:/adminPanel/adminProduct/adminProductSize?productId="+productId;
    }
    @GetMapping("/adminPanel/adminProduct/adminProductSize/viewAdminProductSize")
    public String adminGetViewProductSize(@RequestParam(required = true ) int productSizeId,Model model) {
        model.addAttribute("productSizeForm", productSizeService.findProductSizeById(productSizeId));
        return "viewAdminProductSize";
    }
    @PostMapping(value="/adminPanel/adminProduct/adminProductSize",params = { "productSizeId" })
    public String  adminDeleteProductSize(@RequestParam(required = true ) int productSizeId,
                                           @RequestParam(required = true, defaultValue = "" ) String action,
                                           Model model) {
        int linkId=productSizeService.findProductByProductSizeId(productSizeId).getId();
        if (action.equals("delete")){
            productSizeService.deleteProductSize(productSizeId);
        }
        return "redirect:/adminPanel/adminProduct/adminProductSize?productId="+linkId;
    }
    @GetMapping("/adminPanel/adminProduct/adminProductSize/createAdminProductSize")
    public String adminGetCreateProductSize(@RequestParam(required = true )int undoListCreate,Model model) {
        model.addAttribute("undoListCreate", undoListCreate);
        model.addAttribute("productSizeForm", new ProductSize());
        return "createAdminProductSize";
    }
    @PostMapping("/adminPanel/adminProduct/adminProductSize/createAdminProductSize")
    public String adminPostCreateProductSize(@ModelAttribute("productSizeForm") @Valid ProductSize productSizeForm,
                                BindingResult bindingResult,
                                Model model) {
        int linkId;
        if (bindingResult.hasErrors()) {
            return "createAdminProductSize";
        }
        linkId=productSizeForm.getProductId();
        for (ProductSize prSz:productService.allProductSizes(linkId)){
            if (prSz.getSizeProduct().equals(productSizeForm.getSizeProduct())&&prSz.getId()!=productSizeForm.getId()){
                model.addAttribute("undoListCreate", productSizeForm.getProductId());
                model.addAttribute("productSizeForm", new ProductSize());
                return "redirect:/adminPanel/adminProduct/adminProductSize/createAdminProductSize?undoListCreate="+linkId+"&error=true";
            }
        }
        if (!productSizeService.saveProductSize(productSizeForm)){
            model.addAttribute("undoListCreate", productSizeForm.getProductId());
            model.addAttribute("productSizeForm", new ProductSize());
            return "redirect:/adminPanel/adminProduct/adminProductSize/createAdminProductSize?undoListCreate="+linkId+"&error=true";
        }
        return "redirect:/adminPanel/adminProduct/adminProductSize?productId="+linkId;
    }
    @GetMapping("/adminPanel/adminProduct/adminProductSize/editAdminProductSize")
    public String adminGetEditProductSize(@RequestParam(required = true )int productSizeId,Model model) {
        model.addAttribute("productSizeForm", productSizeService.findProductSizeById(productSizeId));
        return "editAdminProductSize";
    }
    @PostMapping("/adminPanel/adminProduct/adminProductSize/editAdminProductSize")
    public String adminPostEditProductSize(@ModelAttribute("productSizeForm") @Valid ProductSize productSizeForm,
                                             BindingResult bindingResult,
                                             Model model) {
        int linkId;
        if (bindingResult.hasErrors()) {
            return "editAdminProductSize";
        }
        linkId=productSizeForm.getProductId();
        for (ProductSize prSz:productService.allProductSizes(linkId)){
            if (prSz.getSizeProduct().equals(productSizeForm.getSizeProduct())&&prSz.getId()!=productSizeForm.getId()){
                model.addAttribute("productSizeForm", new ProductSize());
                return "redirect:/adminPanel/adminProduct/adminProductSize/editAdminProductSize?productSizeId="+productSizeForm.getId()+"&error=true";
            }
        }
        if (productSizeService.updateProductSize(productSizeForm)){
            model.addAttribute("productSizeForm", new ProductSize());
            return "redirect:/adminPanel/adminProduct/adminProductSize/editAdminProductSize?productSizeId="+productSizeForm.getId()+"&error=true";
        }
        return "redirect:/adminPanel/adminProduct/adminProductSize?productId="+linkId;
    }
    @GetMapping("/adminPanel/adminStatistics")
    public String adminStatisticsList(Model model) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd").create();
        model.addAttribute("allAttendancesView", gson.toJson(attendanceService.allAttendancesByStatus("Просмотр")));
        model.addAttribute("allAttendancesBuy", gson.toJson(attendanceService.allAttendancesByStatus("Покупка")));
        return "adminStatistics";
    }

    @GetMapping("/adminPanel/adminBanner")
    public String adminBannerList(Model model) {
        model.addAttribute("allBanners", bannerService.allBanners());
        return "adminBanner";
    }
    @PostMapping(value = "/adminPanel/adminBanner", params = { "sortName", "sortBy" })
    public String adminSortBannerList(@RequestParam(required = true, defaultValue = "",value="sortName") String sortName,
                                     @RequestParam(required = true, defaultValue = "",value="sortBy") String sortBy,
                                     @RequestParam(required = false, value="lstBn") List<Banner> lstBn, Model model) {
        if (lstBn==null){
            lstBn=new ArrayList<>();
        }
        List<Banner>lstT=bannerService.sortBanner(lstBn,sortName,sortBy);
        model.addAttribute("allBanners", lstT);
        return "adminBanner";
    }
    @PostMapping(value = "/adminPanel/adminBanner")
    public String adminResetBannerList( Model model) {
        return "redirect:adminBanner";
    }
    @GetMapping("/adminPanel/adminBanner/createAdminBanner")
    public String adminGetCreateBanner(Model model) {
        model.addAttribute("bannerForm", new Banner());
        return "createAdminBanner";
    }
    @PostMapping("/adminPanel/adminBanner/createAdminBanner")
    public String adminPostCreateBanner(@ModelAttribute("bannerForm") @Valid Banner bannerForm, @RequestParam(required = true ) MultipartFile imgFile, BindingResult bindingResult, Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            return "createAdminBanner";
        }
        bannerForm.setName(imgFile.getOriginalFilename());
        InputStream is = new ByteArrayInputStream(imgFile.getBytes());
        BufferedImage img = ImageIO.read(is);
        for(Banner bn:bannerService.allBanners()){
            if (bn.getName().equals(imgFile.getOriginalFilename())&&bn.getId()!=bannerForm.getId()){
                return "redirect:/adminPanel/adminBanner/createAdminBanner?error=true";
            }
        }
        for(Product pr:productService.allProducts()){
            if (pr.getPhotoProduct().equals(imgFile.getOriginalFilename())){
                return "redirect:/adminPanel/adminBanner/createAdminBanner?error=true";
            }
        }
        for(Catalog ct:catalogService.allCatalogs()){
            if (ct.getIcon().equals(imgFile.getOriginalFilename())||ct.getImg().equals(imgFile.getOriginalFilename())){
                return "redirect:/adminPanel/adminBanner/createAdminBanner?error=true";
            }
        }
        if (!bannerService.saveBanner(bannerForm)){
            return "redirect:/adminPanel/adminBanner/createAdminBanner?error=true";
        }
        ImageIO.write(img,"png",
                new File(".\\src\\main\\resources\\static\\img\\images\\"+
                        bannerForm.getName()));
        ImageIO.write(img,"png",
                new File(".\\uploads\\img\\images\\"+
                        bannerForm.getName()));
        return "redirect:/adminPanel/adminBanner";
    }
    @PostMapping(value="/adminPanel/adminBanner", params = { "bannerId" })
    public String  adminDeleteBanner(@RequestParam(required = true ) int bannerId,
                                      @RequestParam(required = true, defaultValue = "" ) String action,
                                      Model model) {
        if (action.equals("delete")){
            File file = new File(".\\src\\main\\resources\\static\\img\\images\\"+
                    bannerService.findBannerById(bannerId).getName());
            file.delete();
            file.exists();
            File fileUpload = new File(".\\uploads\\img\\images\\"+
                    bannerService.findBannerById(bannerId).getName());
            fileUpload.delete();
            fileUpload.exists();
            bannerService.deleteBanner(bannerId);
        }
        return "redirect:/adminPanel/adminBanner";
    }
    @GetMapping("/adminPanel/adminBanner/viewAdminBanner")
    public String adminGetViewBanner(@RequestParam(required = true ) int bannerId,Model model) {
        model.addAttribute("bannerForm", bannerService.findBannerById(bannerId));
        model.addAttribute("linkImg", "/img/images/"+bannerService.findBannerById(bannerId).getName());
        return "viewAdminBanner";
    }

    @GetMapping("/adminPanel/adminCatalog")
    public String adminCatalogList(Model model) {
        model.addAttribute("allCatalogs", catalogService.allCatalogs());
        return "adminCatalog";
    }
    @PostMapping(value = "/adminPanel/adminCatalog", params = { "sortName", "sortBy" })
    public String adminSortCatalogList(@RequestParam(required = true, defaultValue = "",value="sortName") String sortName,
                                      @RequestParam(required = true, defaultValue = "",value="sortBy") String sortBy,
                                      @RequestParam(required = false, value="lstCt") List<Catalog> lstCt, Model model) {
        if (lstCt==null){
            lstCt=new ArrayList<>();
        }
        List<Catalog>lstT=catalogService.sortCatalog(lstCt,sortName,sortBy);
        model.addAttribute("allCatalogs", lstT);
        return "adminCatalog";
    }
    @PostMapping(value = "/adminPanel/adminCatalog")
    public String adminResetCatalogList( Model model) {
        return "redirect:adminCatalog";
    }
    @GetMapping("/adminPanel/adminCatalog/createAdminCatalog")
    public String adminGetCreateCatalog(Model model) {
        model.addAttribute("catalogForm", new Catalog());
        return "createAdminCatalog";
    }
    @PostMapping("/adminPanel/adminCatalog/createAdminCatalog")
    public String adminPostCreateCatalog(@ModelAttribute("catalogForm") @Valid Catalog catalogForm, @RequestParam(required = true ) MultipartFile imgFile, @RequestParam(required = true ) MultipartFile iconFile, BindingResult bindingResult, Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            return "createAdminCatalog";
        }
        catalogForm.setImg(imgFile.getOriginalFilename());
        InputStream is1 = new ByteArrayInputStream(imgFile.getBytes());
        BufferedImage img1 = ImageIO.read(is1);
        catalogForm.setIcon(iconFile.getOriginalFilename());
        InputStream is2 = new ByteArrayInputStream(iconFile.getBytes());
        BufferedImage img2 = ImageIO.read(is2);
        if (imgFile.getOriginalFilename().equals(iconFile.getOriginalFilename())){
            return "redirect:/adminPanel/adminCatalog/createAdminCatalog?error=true";
        }
        for(Catalog cn:catalogService.allCatalogs()){
            if (cn.getImg().equals(imgFile.getOriginalFilename())&&cn.getIcon().equals(iconFile.getOriginalFilename())&&catalogForm.getName().equals(cn.getName())&&cn.getId()!=catalogForm.getId()){
                return "redirect:/adminPanel/adminCatalog/createAdminCatalog?error=true";
            }
        }
        for(Product pr:productService.allProducts()){
            if (pr.getPhotoProduct().equals(imgFile.getOriginalFilename())||pr.getPhotoProduct().equals(iconFile.getOriginalFilename())){
                return "redirect:/adminPanel/adminCatalog/createAdminCatalog?error=true";
            }
        }
        for(Banner bn:bannerService.allBanners()){
            if (bn.getName().equals(imgFile.getOriginalFilename())||bn.getName().equals(iconFile.getOriginalFilename())){
                return "redirect:/adminPanel/adminCatalog/createAdminCatalog?error=true";
            }
        }
        if (!catalogService.saveCatalog(catalogForm)){
            return "redirect:/adminPanel/adminCatalog/createAdminCatalog?error=true";
        }
        ImageIO.write(img1,"png",
                new File(".\\src\\main\\resources\\static\\img\\images\\"+
                        catalogForm.getImg()));
        ImageIO.write(img1,"png",
                new File(".\\uploads\\img\\images\\"+
                        catalogForm.getImg()));
        ImageIO.write(img2,"png",
                new File(".\\src\\main\\resources\\static\\img\\images\\"+
                        catalogForm.getIcon()));
        ImageIO.write(img2,"png",
                new File(".\\uploads\\img\\images\\"+
                        catalogForm.getIcon()));
        return "redirect:/adminPanel/adminCatalog";
    }
    @PostMapping(value="/adminPanel/adminCatalog", params = { "catalogId" })
    public String  adminDeleteCatalog(@RequestParam(required = true ) int catalogId,
                                     @RequestParam(required = true, defaultValue = "" ) String action,
                                     Model model) {
        if (action.equals("delete")){
            File file1 = new File(".\\src\\main\\resources\\static\\img\\images\\"+
                    catalogService.findCatalogById(catalogId).getImg());
            file1.delete();
            file1.exists();
            File fileUpload1 = new File(".\\uploads\\img\\images\\"+
                    catalogService.findCatalogById(catalogId).getImg());
            fileUpload1.delete();
            fileUpload1.exists();
            File file2 = new File(".\\src\\main\\resources\\static\\img\\images\\"+
                    catalogService.findCatalogById(catalogId).getIcon());
            file2.delete();
            file2.exists();
            File fileUpload2 = new File(".\\uploads\\img\\images\\"+
                    catalogService.findCatalogById(catalogId).getIcon());
            fileUpload2.delete();
            fileUpload2.exists();
            catalogService.deleteCatalog(catalogId);
        }
        return "redirect:/adminPanel/adminCatalog";
    }
    @GetMapping("/adminPanel/adminCatalog/viewAdminCatalog")
    public String adminGetViewCatalog(@RequestParam(required = true ) int catalogId,Model model) {
        model.addAttribute("catalogForm", catalogService.findCatalogById(catalogId));
        model.addAttribute("linkImg", "/img/images/"+catalogService.findCatalogById(catalogId).getImg());
        model.addAttribute("linkIcon", "/img/images/"+catalogService.findCatalogById(catalogId).getIcon());
        return "viewAdminCatalog";
    }
}
