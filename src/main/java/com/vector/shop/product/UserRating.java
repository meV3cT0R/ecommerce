package com.vector.shop.product;

import com.vector.shop.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
public class UserRating {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name="productId",referencedColumnName = "id")
    private Product product;

    @ManyToOne
    @JoinColumn(name="userId",referencedColumnName = "id")
    private User user;
    private int rating;

    @Override
    public boolean equals(Object o) {
        if(o instanceof UserRating)
            return (this.getUser().getId() == ((UserRating) o).getUser().getId()) 
                && (this.getProduct().getId() == ((UserRating) o).getProduct().getId());
        return false;
    }
}
