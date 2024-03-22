package ru.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.shop.entity.*;
import ru.shop.service.*;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Date;
import java.util.*;

@Controller
public class GuestController {
    @Autowired
    private ProductService productService;
    @Autowired
    private BannerService bannerService;
    @Autowired
    private ProductSizeService productSizeService;
    @Autowired
    private OrderProductService orderProductService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private UserService userService;
    @Autowired
    private CatalogService catalogService;
    @GetMapping("/")
    public String bannerList(Model model) {
        List<Banner> lstBn=bannerService.allBanners();
        Map<String,Integer> lstBnIndex=new HashMap<>();
        Map<String,String> lstBnHidden=new HashMap<>();
        model.addAttribute("allBanners", lstBn);
        int i=0;
        for (Banner bannerEl:bannerService.allBanners()){
            lstBnIndex.put(bannerEl.getName(),i);
            if (i==0){
                lstBnHidden.put(bannerEl.getName(),"flex");
            }
            else{
                lstBnHidden.put(bannerEl.getName(),"none");
            }
            i++;
        }
        model.addAttribute("AllBannerIndex", lstBnIndex);
        model.addAttribute("AllBannerHidden", lstBnHidden);
        model.addAttribute("AllCatalogs", catalogService.allCatalogs());
        if (userService.allUsers().size()==0){
            User user = new User();
            user.setPassword("biznes556/ru");
            user.setPasswordConfirm("biznes556/ru");
            user.setUsername("PlanetGift");
            userService.saveUser(user);
        }
        return "index";
    }
    @GetMapping("/catalog")
    public String mainCatalogList(Model model) {
        Attendance attendance=new Attendance();
        attendance.setDateCreate(new Date(System.currentTimeMillis()));
        attendance.setStatus("Просмотр");
        attendanceService.saveAttendance(attendance);
        model.addAttribute("AllCatalogs", catalogService.allCatalogs());
        return "catalog";
    }

    @GetMapping("/catalog/catalogProduct")
    public String clientProductList(Model model,@RequestParam(required = true ) String groupProduct) {
        model.addAttribute("allProducts", productService.allProductsByGroup(groupProduct));
        model.addAttribute("undoListGroup", groupProduct);
        model.addAttribute("productServ", productService);
        model.addAttribute("AllCatalogs", catalogService.allCatalogs());
        return "catalogProduct";
    }
    @PostMapping(value = "/catalog/catalogProduct", params = { "sortName", "sortBy" })
    public String clientSortProductList(@RequestParam(required = true, defaultValue = "",value="sortName") String sortName,
                                    @RequestParam(required = true, defaultValue = "",value="sortBy") String sortBy,
                                    @RequestParam(required = true ) String groupProduct,
                                    @RequestParam(required = false, value="lstPr") List<Product> lstPr, Model model) {
        if (lstPr==null){
            lstPr=new ArrayList<>();
        }
        List<Product>lstT=productService.sortProduct(lstPr,sortName,sortBy);
        model.addAttribute("allProducts", lstT);
        model.addAttribute("undoListGroup", groupProduct);
        model.addAttribute("productServ", productService);
        model.addAttribute("AllCatalogs", catalogService.allCatalogs());
        return "catalogProduct";
    }
    @PostMapping(value = "/catalog/catalogProduct")
    public String adminResetUserList( Model model,@RequestParam(required = true ) String groupProduct)throws Throwable {
        return "redirect:/catalog/catalogProduct?groupProduct="+ URLEncoder.encode(groupProduct, "UTF-8");
    }
    @GetMapping("/catalog/catalogProduct/viewCatalogProduct")
    public String clientGetViewProduct(@RequestParam(required = true ) String groupProduct,@RequestParam(required = true ) int productId,Model model) {
        model.addAttribute("productForm", productService.findProductById(productId));
        model.addAttribute("allProductSizes", productService.allProductSizes(productId));
        return "viewCatalogProduct";
    }
    @PostMapping("/catalog/catalogProduct/viewCatalogProduct")
    public String clientPostCreateProductSize(HttpServletRequest request, @RequestParam(required = true ) String groupProduct, @RequestParam(required = true ) int productId,
                                              @RequestParam(required = false, value="lstPrSz") List<ProductSize> lstPrSz,
                                              @RequestParam(required = false, value="lstCountPrSz") List<Integer> lstCountPrSz, Model model) throws Throwable {
        if (lstPrSz==null){
            lstPrSz=new ArrayList<>();
        }
        if (lstCountPrSz==null){
            lstCountPrSz=new ArrayList<>();
        }
        int countPrSz=0;
        for (int i=0;i<lstCountPrSz.size();i++){
            if (lstCountPrSz.get(i)<=0){
                countPrSz++;
            }
        }
        if (lstCountPrSz.size()==countPrSz){
            return "redirect:/catalog/catalogProduct/viewCatalogProduct?error=true&groupProduct="+URLEncoder.encode(groupProduct, "UTF-8")+"&productId="+productId;
        }
        HttpSession session = request.getSession();
        if (session.getAttribute("lstOrPrBasket")==null){
            session.setAttribute("lstOrPrBasket", new ArrayList<OrderProduct>());
        }
        List<OrderProduct>lstOrPr = (List<OrderProduct>) session.getAttribute("lstOrPrBasket");
        for (int i=0;i<lstPrSz.size();i++){
            if (lstCountPrSz.get(i)>0){
                OrderProduct orPr=new OrderProduct();
                orPr.setOrderId(0);
                orPr.setNameProduct(productService.findProductById(productId).getNameProduct());
                orPr.setDescription(productService.findProductById(productId).getDescription());
                orPr.setPhotoProduct(productService.findProductById(productId).getPhotoProduct());
                orPr.setPrice(lstPrSz.get(i).getPrice());
                orPr.setDiscount(lstPrSz.get(i).getDiscount());
                orPr.setGroupProduct(groupProduct);
                orPr.setSizeProduct(lstPrSz.get(i).getSizeProduct());
                orPr.setMaterial(productService.findProductById(productId).getMaterial());
                orPr.setCount(lstCountPrSz.get(i));
                orPr.setOrderId(0);
                boolean fl=false;
                for (int j=0;j<lstOrPr.size();j++){
                    if (lstOrPr.get(j).getNameProduct().equals(orPr.getNameProduct())&&lstOrPr.get(j).getGroupProduct().equals(orPr.getGroupProduct())&&lstOrPr.get(j).getPrice()==(orPr.getPrice())&&lstOrPr.get(j).getDiscount()==(orPr.getDiscount())&&lstOrPr.get(j).getSizeProduct().equals(orPr.getSizeProduct())){
                        lstOrPr.get(j).setCount(lstOrPr.get(j).getCount()+orPr.getCount());
                        fl=true;
                        break;
                    }
                }
                if (fl==false){
                    lstOrPr.add(orPr);
                }
            }
        }
        session.setAttribute("lstOrPrBasket", lstOrPr);
        model.addAttribute("allProducts", productService.allProductsByGroup(groupProduct));
        model.addAttribute("undoListGroup", groupProduct);
        model.addAttribute("productServ", productService);
        return "redirect:/basket";
    }

    @GetMapping("/basket")
    public String clientBasketList(HttpServletRequest request,Model model) {
        HttpSession session = request.getSession();
        if (session.getAttribute("lstOrPrBasket")==null){
            session.setAttribute("lstOrPrBasket", new ArrayList<OrderProduct>());
        }
        model.addAttribute("allOrderProductSessions", (List<OrderProduct>)session.getAttribute("lstOrPrBasket"));
        model.addAttribute("orderProductServ", orderProductService);
        model.addAttribute("productServ", productService);
        model.addAttribute("allPrice", orderProductService.allPriceWithDiscount((List<OrderProduct>)session.getAttribute("lstOrPrBasket")));
        return "basket";
    }

    @PostMapping(value="/basket",params = {"action"})
    public String  clientDeleteBasketOrderProduct(HttpServletRequest request,@RequestParam(required = true ) String orderProductName,
                                                  @RequestParam(required = true ) String orderProductGroup,
                                                  @RequestParam(required = true ) Double orderProductPrice,
                                                  @RequestParam(required = true ) Double orderProductDiscount,
                                                  @RequestParam(required = true ) String orderProductSize,
                                           @RequestParam(required = true, defaultValue = "",value = "action") String action,
                                           Model model) {
        HttpSession session = request.getSession();
        if (session.getAttribute("lstOrPrBasket")==null){
            session.setAttribute("lstOrPrBasket", new ArrayList<OrderProduct>());
        }
        List<OrderProduct>lstOrPr=new ArrayList<>();
        if (action.equals("delete")){
            for(int i=0;i<((List<OrderProduct>)session.getAttribute("lstOrPrBasket")).size();i++){
                if (((List<OrderProduct>)session.getAttribute("lstOrPrBasket")).get(i).getNameProduct().equals(orderProductName)&&((List<OrderProduct>)session.getAttribute("lstOrPrBasket")).get(i).getGroupProduct().equals(orderProductGroup)&&((List<OrderProduct>)session.getAttribute("lstOrPrBasket")).get(i).getPrice()==(orderProductPrice)&&((List<OrderProduct>)session.getAttribute("lstOrPrBasket")).get(i).getDiscount()==(orderProductDiscount)&&((List<OrderProduct>)session.getAttribute("lstOrPrBasket")).get(i).getSizeProduct().equals(orderProductSize)){

                }
                else{
                    lstOrPr.add(((List<OrderProduct>)session.getAttribute("lstOrPrBasket")).get(i));
                }
            }
        }
        session.setAttribute("lstOrPrBasket", lstOrPr);
        model.addAttribute("allOrderProductSessions", (List<OrderProduct>)session.getAttribute("lstOrPrBasket"));
        model.addAttribute("orderProductServ", orderProductService);
        model.addAttribute("productServ", productService);
        model.addAttribute("allPrice", orderProductService.allPriceWithDiscount((List<OrderProduct>)session.getAttribute("lstOrPrBasket")));
        return "redirect:/basket";
    }

    @PostMapping("/basket")
    public String  clientEditBasketOrderProduct(HttpServletRequest request,@RequestParam(required = true ) String orderProductName,
                                                  @RequestParam(required = true ) String orderProductGroup,
                                                  @RequestParam(required = true ) Double orderProductPrice,
                                                  @RequestParam(required = true ) Double orderProductDiscount,
                                                  @RequestParam(required = true ) String orderProductSize,
                                                  @RequestParam(required = true ) int countOrderProduct,
                                                  Model model) {
        HttpSession session = request.getSession();
        if (session.getAttribute("lstOrPrBasket")==null){
            session.setAttribute("lstOrPrBasket", new ArrayList<OrderProduct>());
        }
        List<OrderProduct>lstOrPr=new ArrayList<>();
        for(int i=0;i<((List<OrderProduct>)session.getAttribute("lstOrPrBasket")).size();i++){
            if (((List<OrderProduct>)session.getAttribute("lstOrPrBasket")).get(i).getNameProduct().equals(orderProductName)&&((List<OrderProduct>)session.getAttribute("lstOrPrBasket")).get(i).getGroupProduct().equals(orderProductGroup)&&((List<OrderProduct>)session.getAttribute("lstOrPrBasket")).get(i).getPrice()==(orderProductPrice)&&((List<OrderProduct>)session.getAttribute("lstOrPrBasket")).get(i).getDiscount()==(orderProductDiscount)&&((List<OrderProduct>)session.getAttribute("lstOrPrBasket")).get(i).getSizeProduct().equals(orderProductSize)){
                ((List<OrderProduct>)session.getAttribute("lstOrPrBasket")).get(i).setCount(countOrderProduct);
            }
            lstOrPr.add(((List<OrderProduct>)session.getAttribute("lstOrPrBasket")).get(i));
        }
        session.setAttribute("lstOrPrBasket", lstOrPr);
        model.addAttribute("allOrderProductSessions", (List<OrderProduct>)session.getAttribute("lstOrPrBasket"));
        model.addAttribute("orderProductServ", orderProductService);
        model.addAttribute("productServ", productService);
        model.addAttribute("allPrice", orderProductService.allPriceWithDiscount((List<OrderProduct>)session.getAttribute("lstOrPrBasket")));
        return "redirect:/basket";
    }

    @GetMapping("/basket/buyOrder")
    public String clientGetCreateBasket(HttpServletRequest request,Model model) {
        HttpSession session = request.getSession();
        if (session.getAttribute("lstOrPrBasket")==null || ((List<OrderProduct>)session.getAttribute("lstOrPrBasket")).size()==0){
            return "redirect:/basket?error=true";
        }
        model.addAttribute("orderForm", new Order());
        return "buyOrder";
    }

    @PostMapping("/basket/buyOrder")
    public String clientPostCreateBasket(HttpServletRequest request,@ModelAttribute("orderForm") @Valid Order orderForm,
                                             BindingResult bindingResult,
                                             Model model)throws AddressException, MessagingException, IOException {
        if (bindingResult.hasErrors()) {
            return "buyOrder";
        }
        orderForm.setOrderStatus("Не оплачен");
        orderForm.setCreateDate(new Date(System.currentTimeMillis()));
        HttpSession session = request.getSession();
        if (((List<OrderProduct>)session.getAttribute("lstOrPrBasket"))==null||((List<OrderProduct>)session.getAttribute("lstOrPrBasket")).size()==0){
            model.addAttribute("orderForm", new Order());
            return "redirect:/catalog";
        }
        if (!orderService.saveOrder(orderForm)){
            model.addAttribute("orderForm", new Order());
            return "redirect:/basket/buyOrder?error=true";
        }
        Order order = orderService.allOrders().get(orderService.allOrders().size()-1);
        for(int i=0;i<((List<OrderProduct>)session.getAttribute("lstOrPrBasket")).size();i++){
            ((List<OrderProduct>)session.getAttribute("lstOrPrBasket")).get(i).setOrderId(order.getId());
            orderProductService.saveOrderProduct(((List<OrderProduct>)session.getAttribute("lstOrPrBasket")).get(i));
        }
        session.setAttribute("lstOrPrBasket", new ArrayList<OrderProduct>());
        Attendance attendance=new Attendance();
        attendance.setDateCreate(new Date(System.currentTimeMillis()));
        attendance.setStatus("Покупка");
        attendanceService.saveAttendance(attendance);

        String from = "zakazplanetapodarkov@yandex.ru";
        String pass = "cfvzrduyuucsgmmk";
        //Пароль приложения: cfvzrduyuucsgmmk
        String to = orderForm.getMail();
        String host = "smtp.yandex.ru";
        Properties props = new Properties();

        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        Session sessionPost = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from,pass);
            }
        });

        try {
            Message msg = new MimeMessage(sessionPost);
            String subject = "Заказ от 'Планета подарков'";
            String body = "Заказ от 'Планета подарков':\n";
            for (OrderProduct orPr:orderService.allOrderProducts(order.getId())){
                body+="Название: "+orPr.getNameProduct()+" Размер: "+orPr.getSizeProduct()+" Вид: "+orPr.getGroupProduct()+" Материал:"+orPr.getMaterial()+" Количество: "+orPr.getCount()+" Цена: "+orderProductService.priceWithDiscount(orPr.getPrice(),orPr.getDiscount())+"₽\n";

            }
            body+="Общая цена: "+orderService.priceOrderProducts(order.getId())+"₽\n";
            body+="Чтобы отменить заказ, напишите об этом на эту же почту или позвоните по одному из номеров на сайте.";
            msg.setSubject(subject);
            msg.setText(body);
            msg.setFrom(new InternetAddress(from));
            InternetAddress toAddress = new InternetAddress(to);
            msg.addRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            msg.setSubject(subject);
            msg.setText(body);
            Transport.send(msg);
        }
        catch (AddressException ae) {
            System.out.println("1");
        }
        catch (MessagingException me) {
            System.out.println("2");
        }

        return "redirect:/catalog";
    }
}
