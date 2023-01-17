package com.vector.shop.product;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import com.vector.shop.product.comment.Comment;
import com.vector.shop.user.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Product implements Serializable{
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String name;
    private String description;
    private long quantity;
    private long price;
    private Type type;
    private String image;

    @ManyToMany
    private List<User> user;

    @OneToMany(mappedBy = "product",cascade = CascadeType.MERGE)
    private List<Comment> comments;
    enum Type{
        FASHION,BEAUTY,ELECTRONICS,SPORTS,MOTORS;
    }
    public String getFormattedPrice() {
        return NumberFormat.getCurrencyInstance(new Locale("EN","NP")).format(price);
    }

    @Override
    public boolean equals(Object o) {
        if((o instanceof Product) && (this.getId()==((Product) o).getId()) ) return true;
        return false;
    }
}
