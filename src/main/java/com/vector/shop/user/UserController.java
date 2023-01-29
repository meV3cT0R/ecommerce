package com.vector.shop.user;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vector.shop.bill.Bill;
import com.vector.shop.product.Product;
import com.vector.shop.product.ProductService;
import com.vector.shop.user.card.Card;
import com.vector.shop.user.phonenumber.PhoneNumber;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


@Controller
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private UserService userService;
    private ProductService productService;

    @PersistenceContext
    private EntityManager entityManager;
    public UserController(UserService userService,ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }

    @GetMapping("/")
    public String home(Model model,@AuthenticationPrincipal User user) {
        model.addAttribute("products", productService.getProducts());

        model.addAttribute("user", user);
        return "home";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("phoneNumber",new PhoneNumber());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(Model model,User user,PhoneNumber phoneNumber) {
        user.addPhoneNumber(phoneNumber);
        Map<Error,String> errors = userService.validate(user);

        if(errors.isEmpty()){
            userService.save(user);
            return "login";
        }

        for(Map.Entry<Error,String> entry: errors.entrySet()) {
            log.info(entry.getKey() + " : "+entry.getValue());
            model.addAttribute(entry.getKey().name().toLowerCase()+"Error",
              entry.getValue());
        }
        System.out.println(user);
        return "register";
    }

    @GetMapping("/cpanel")
    public String cpanelAdmin(Model model,@AuthenticationPrincipal User user) {
        model.addAttribute("user", user);
        model.addAttribute("product",new Product());
        return "cpanel";
    }

    @GetMapping("/addincart")
    public String addInCart(Model model,@AuthenticationPrincipal User user,@RequestParam("productId") String id) {
        Product product = productService.findById(Long.parseLong(id));
        userService.addToCart(product,user);

        model.addAttribute("product",product);
        return String.format("redirect:/product/%d",product.getId());
    }

    @GetMapping("/cart")
    public String viewCart(Model model,@AuthenticationPrincipal User user) {
        model.addAttribute("user",user);
        try {
            Bill cart = userService.viewCart(user);
            model.addAttribute("cart",cart);
            
            return "cart";
        }catch(CartEmptyException e) {
            model.addAttribute("error",e.getMessage());
            return "cart";
        }
    }

    @PostMapping("/buyincart")
    public String buyInCart(Model model,@AuthenticationPrincipal User user) {
        model.addAttribute("user",user);
        Bill bill =null;
        try {
           bill = userService.buyItemInCart(user);
           System.out.println(bill);
        }catch(NoCreditCardException cce) {
            log.warn(cce.getMessage());
            model.addAttribute("user",user);
            model.addAttribute("card",new Card());
            return "creditcard";
        }catch(CartEmptyException cee) {
            log.warn("Cart is empty");
            model.addAttribute("error","Cart Is Empty");
            return viewCart(model, user);
        }
        model.addAttribute("message","Items Purchased");
        return home(model,user);
    }

    @PostMapping("/card/save")
    public String saveCard(Model model,@AuthenticationPrincipal User user,Card card) {
        userService.saveCreditCard(user, card);
        return viewCart(model, user);
    }

    @GetMapping("/dashboard")
    public String dashBoard() {
        return "dashboard";
    }

    @GetMapping("/buy") 
    public String buy(Model model,@AuthenticationPrincipal User user,@RequestParam("productId") String id) {
        model.addAttribute("user",user);
        List<Product> item = new ArrayList<>();
        item.add(productService.findById(Long.parseLong(id)));
        user.setCart(item);
        userService.update(user);
        model.addAttribute("bill",userService.viewCart(user));
        return "bill";
    }
}
