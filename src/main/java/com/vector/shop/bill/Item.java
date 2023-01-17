package com.vector.shop.bill;

import com.vector.shop.product.Product;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
public class Item {
    public @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    public final String name;
    public final long quantity;
    public final long price;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="productId",referencedColumnName = "id")
    public final Product product;
    @ManyToOne
    @JoinColumn(name="bill_id")
    public Bill bill;
}
