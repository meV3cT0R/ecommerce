package com.vector.shop.user.phonenumber;



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
public class PhoneNumber {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    private String number;
    @ManyToOne
    @JoinColumn(name="user_id",referencedColumnName = "id")
    private User user;

    @Override
    public String toString() {
        return "Phone Number: "+number;
    }
}
