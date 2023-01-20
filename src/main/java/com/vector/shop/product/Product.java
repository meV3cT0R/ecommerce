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

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
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

    @ManyToMany
    private List<User> rate;

    @OneToMany(mappedBy = "product")
    private List<UserRating> userRatings;

    private int[] rating = {0,0,0,0,0}; 

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

    public void increaseRating(int index) {
        if(index > 5 || index < 1)
            throw new IllegalArgumentException("Rating should be between 1-5");
        
        rating[index-1]++;
    }
    
    public float getAverageRating() {
        float totalRatingCount=0,totalRating=0;
        for(int i=0;i<rating.length;i++) {
            for(int j=0;j<rating[i];j++) {
                totalRating+=i+1;
            }
            totalRatingCount+=rating[i];
        }
        return totalRating/totalRatingCount;
    }

    public boolean hasRatedThisProduct(User user,Product product) {
        UserRating meowRating = new UserRating();
        meowRating.setUser(user);
        meowRating.setProduct(product);
        return userRatings.contains(meowRating);
    }
}
