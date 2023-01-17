package com.vector.shop.product.comment;

import java.util.Date;
import com.vector.shop.product.Product;
import com.vector.shop.user.User;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String commentText;
    private Date createdDate;

    @ManyToOne
    @JoinColumn(name="userId",referencedColumnName="id")
    private User user;

    @ManyToOne
    @JoinColumn(name="productId",referencedColumnName = "id")
    private Product product;

    @Override
    public String toString() {
        return "a comment";
    }
}
