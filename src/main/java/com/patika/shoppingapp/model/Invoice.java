package com.patika.shoppingapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "invoice")
@Getter
@Setter
public class Invoice extends Base{

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", nullable = false)
    private Set<Product> products;

    @ElementCollection
    private List<Integer> quantities;

    @Column
    private boolean isKdvApplied;

    private double totalPrice;

    private double totalPriceWithTaxes;

    public void addProduct(Product product, int quantity) {
        products.add(product);
        quantities.add(quantity);
    }
}
