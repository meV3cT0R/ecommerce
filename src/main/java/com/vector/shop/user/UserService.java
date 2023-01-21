package com.vector.shop.user;



import java.util.Map;

import com.vector.shop.bill.Bill;
import com.vector.shop.product.Product;
import com.vector.shop.user.card.Card;

public interface UserService {
    User save(User user);
    Bill buyItemInCart(User user);
    Product addToCart(Product product,User user) throws AddToCartException;
    Bill buy(User user,Product product);
    User update(User user);
    Card saveCreditCard(User user,Card creditCard);
    Bill viewCart(User user);
    Map<Error,String> validate(User user);
    User find(String username);
}
