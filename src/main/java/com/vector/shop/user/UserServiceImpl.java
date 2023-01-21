package com.vector.shop.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.vector.shop.bill.Bill;
import com.vector.shop.bill.BillService;
import com.vector.shop.bill.Item;
import com.vector.shop.product.Product;
import com.vector.shop.product.ProductService;
import com.vector.shop.user.card.Card;
import com.vector.shop.user.card.CardRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class UserServiceImpl implements UserService {
    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private UserRepository userRepo;

    private PasswordEncoder passwordEncoder;
    @Autowired
    private ProductService productService;
    @Autowired
    private BillService billService;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CardRepository cardRepository;

    public UserServiceImpl(UserRepository userRepo,PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User save(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setJoinedDate(new Date());
        user.setAdmin(false);
        return userRepo.save(user);
    }


    @Override
    public Product addToCart(Product product,@AuthenticationPrincipal User user){
        user.getAuthorities().stream().forEach(t -> logger.info(t.toString()));
        productService.save(product);
        List<Product> products = entityManager.find(User.class, user.getId()).getCart();
        products.add(product);
        user.setCart(products);
    
        update(user);
        return product;   
    }

    @Override
    public Bill buyItemInCart(@AuthenticationPrincipal User user) {
        user = entityManager.find(User.class,user.getId());
        Bill bill = new Bill();
        List<Product> products = user.getCart();
        if(products.size() <1) 
            throw new CartEmptyException("Cart is Empty");

        Card card = user.getCard();
        if(card==null)
            throw new NoCreditCardException("No Credit Card ");
            
        List<Item> items = new ArrayList<>();
        products.forEach(item->{
            if(item.getQuantity() <1) throw new ProductNotInStockException("Item not in Stock");
            items.add(new Item(item.getName(),1,item.getPrice(),item));
            item.setQuantity(item.getQuantity()-1);
            productService.update(item);
        });

        bill.setProducts(items);
        user.getBought().addAll(products);
        user.setCart(new ArrayList<>());
        return billService.save(bill);
    }
    
    
    @Override
    public Bill buy(User user, Product product) {

        return null;
    }
    
    @Override
    public User update(User user) {
        return userRepo.save(user);
    }

    @Override
    public Card saveCreditCard(User user,Card creditCard) {
        cardRepository.save(creditCard);
        user.setCard(creditCard);
        update(user);
        return creditCard;
    }

    @Override
    public Bill viewCart(User user) {
        user = entityManager.find(User.class,user.getId());
        List<Item> items = user.getCart().stream()
        .map(item->new Item(item.getName(), 1, item.getPrice(), item))
        .toList();
        if(items.size()<1) throw new CartEmptyException("Cart Is Empty");
        Bill bill = new Bill();
        bill.setCustomerName(user.getFirstName()+ " "+user.getLastName());
        bill.setProducts(items);
        bill.calculateTotalprice();
        return bill;
    }

    @Override
    public User find(String username) {
        return userRepo.findByUsername(username);
    }
    @Override
    public Map<Error, String> validate(User user) {
        Map<Error,String> errors = new HashMap<>();
        validation(user,errors,user.getFirstName(),Error.FIRSTNAME,6,30,false,"[a-zA-Z]");
        validation(user,errors,user.getLastName(),Error.LASTNAME,6,30,false,"[a-zA-Z]");
        validation(user,errors,user.getPassword(),Error.PASSWORD,6,30,false,"\\s");
        validation(user,errors,user.getUsername(),Error.USERNAME,6,30,true,"[a-zA-Z0-9]");
        if(user.getGender()!='M' || user.getGender()!='F'){
            errors.put(Error.GENDER,"what ? the fuck? you don't have gender?");
        }
        if(user.getDob().compareTo(new Date()) > 0) {
            errors.put(Error.BIRTHDATE, "are you not born yet?");
        }
        return errors;
    }
    
    //min inclusive
    // max exclusive
    private void validation(User user,Map<Error,String> errors,String validate,Error error,int min,int max,boolean checkInDb,String regex) {
        String name = (Stream.of(error.name().toLowerCase()).
        map(string->{
           return (Character.toUpperCase(string.charAt(0))+ string.substring(1));
        }).reduce((a,b)->a+b).get()

       );
        if(validate.length() >= max || validate.length() < min) {
            errors.put(error,String.format("%s must be between %d-%d",name,min,max));
        }else
        if(!validate.matches(regex)) {
            errors.put(error,name+" must be alphanumeric");
        }else 
            if(checkInDb) {
                if(user.equals(find(validate))) {
                   errors.put(error,name+"already in use");
               }
            }
    }   
}
