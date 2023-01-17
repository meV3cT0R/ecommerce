package com.vector.shop.bill;

import java.util.Date;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Bill {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String customerName;
    @OneToMany(mappedBy = "bill")
    private List<Item> products;
    private long totalPrice;
    private Date createdDate;

    public void calculateTotalprice() {
        this.totalPrice = products.stream().mapToLong(a->a.getPrice()).sum();
    }
}
