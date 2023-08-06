package com.patika.shoppingapp.model.enumaration;

public enum OrderState {
    WAITING_APPROVAL,
    APPROVED,
    ON_SHIPMENT;

    public static OrderState getDefault(){
        return WAITING_APPROVAL;
    }

}
