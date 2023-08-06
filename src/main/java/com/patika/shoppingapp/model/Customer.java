package com.patika.shoppingapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
@Entity
@Table(name="customer")
@Getter
@Setter
public class Customer extends Base{
    @Column
    private String name;
    @Column
    private String email;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private Set<Order> customerOrders;

}
