package com.patika.shoppingapp.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TaxRateService {

    @Value("${tax.rate}")
    private double taxRate;

    public double getTaxRate() {
        return taxRate;
    }
}