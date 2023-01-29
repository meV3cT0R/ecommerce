package com.vector.shop.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;


import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.vector.shop.product.Product;
import com.vector.shop.product.comment.Comment;
import com.vector.shop.user.card.Card;
import com.vector.shop.user.phonenumber.PhoneNumber;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="users")
public class User implements UserDetails{
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String firstName;
    private String lastName;
    private char gender;
    private String password;
    private String username;
    private Date joinedDate;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date dob;
    private boolean admin;

    @OneToMany(mappedBy="user")
    private List<PhoneNumber> phoneNumbers;

    @ManyToMany
    @JoinTable(name="users_cart",
    joinColumns = @JoinColumn(name="user_id"),
    inverseJoinColumns = @JoinColumn(name="product_id"))
    private List<Product> cart;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="card_id",referencedColumnName = "id")
    private Card card;
    
    @OneToMany(mappedBy = "user",cascade = CascadeType.MERGE)
    private List<Comment> comments;

    @ManyToMany
    @JoinTable(name="user_bought_product",
    joinColumns = @JoinColumn(name="user_id"),
    inverseJoinColumns = @JoinColumn(name="product_id")
    )
    private List<Product> bought;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return username;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean hasBoughtProduct(Product product) {
        return bought.contains(product);
    }
    public PhoneNumber addPhoneNumber(PhoneNumber phoneNumber) {
        if(this.getPhoneNumbers() == null){
            this.setPhoneNumbers(new ArrayList<PhoneNumber>());
        }
        if(this.getPhoneNumbers().add(phoneNumber))
            return phoneNumber;
        else
            return null;
    }
}
