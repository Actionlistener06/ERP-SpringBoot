package com.patika.shoppingapp.model;

import com.patika.shoppingapp.model.enumaration.OrderState;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@Table(name="purchase_order")
public class Order extends Base{

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;


    @ElementCollection
    @CollectionTable(name = "order_items", joinColumns = @JoinColumn(name = "order_id"))
    @MapKeyJoinColumn(name = "product_id")
    @Column(name = "quantity")
    private Map<Product, Integer> products = new HashMap<>();

    @OneToOne(mappedBy = "order", fetch=FetchType.EAGER)
    private Invoice orderInvoice;

    private OrderState orderState=getOrderState(); //Default enum deger atamasi

    private String shippingAddress;

    private Double totalPrice;

}
