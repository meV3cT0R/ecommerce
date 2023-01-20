package com.vector.shop.product;


import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.vector.shop.product.comment.Comment;
import com.vector.shop.product.comment.CommentRepository;
import com.vector.shop.user.User;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class ProductServiceImpl implements ProductService{
    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
    @Autowired
    private ProductRepository productRepo;
    @Autowired
    private CommentRepository commentRepo;
    @Autowired
    private UserRatingRepo userRatingRepo;


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Product save(Product product) {
        
        return productRepo.save(product);
    }

    @Override
    public Product delete(Product product) {

        return null;
    }

    @Override
    public List<Product> search(String name) {
        System.out.println(name);
        List<Product> products = productRepo.findAll().stream()
        .filter(product->product.getName().toLowerCase().contains(name))
        .toList();
        products.forEach(System.out::println);
        return products;
    }

    @Override
    public List<Product> getProducts() {
        PageRequest page = PageRequest.of(0,5);
        return productRepo.findAll(page).getContent();   
    }

    @Override
    public Product findById(long id) {
        Optional<Product> product = productRepo.findById(id);
        if(product.isPresent())
            return product.get();
        return null;
    }

    @Override
    public Product update(Product product) {
        return productRepo.save(product);
    }

    @Override
    public Comment saveComment(Comment comment, User user, Product product) {
        log.info("Before saving comment");
        comment.setProduct(product);
        comment.setUser(user);
        commentRepo.save(comment);
        return comment;
    }
    
    @Override
    public List<Comment> loadComments(Product p) {
        return commentRepo.findAll().stream()
        .filter(product->product.getProduct().getId()==p.getId()).toList();
    }

    @Override
    public void rate(int rating, long id,User user) {
        Product product = findById(id);
        product.increaseRating(rating);
        UserRating userRating = new UserRating();
        userRating.setProduct(product);
        userRating.setUser(user);
        userRating.setRating(rating);
        userRatingRepo.save(userRating);
        update(product);
    }
}
