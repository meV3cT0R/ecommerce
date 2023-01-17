package com.vector.shop.user.card;



import org.hibernate.validator.constraints.CreditCardNumber;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Entity
@Data
public class Card {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) long id;
    
    @CreditCardNumber(message="Not a valid credit card")
    private String ccNumber;
    @Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$")
    private String expireDate;

    @Digits(integer=3,fraction = 0,message="Invalid CVV")
    private short cvv;
}
