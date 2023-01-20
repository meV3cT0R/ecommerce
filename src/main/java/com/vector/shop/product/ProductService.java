package com.vector.shop.product;

import java.util.List;

import com.vector.shop.product.comment.Comment;
import com.vector.shop.user.User;

public interface ProductService {
    Product save(Product product);
    Product delete(Product product);
    List<Product> search(String name);
    List<Product> getProducts();
    Product findById(long id);
    Product update(Product product);
    Comment saveComment(Comment comment,User user,Product product);
    List<Comment> loadComments(Product p);
    void rate(int rating,long id,User user);
}
