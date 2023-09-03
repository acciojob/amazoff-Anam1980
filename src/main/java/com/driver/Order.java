package com.driver;

import org.springframework.stereotype.Component;


public class Order {

    private String id;
    private int deliveryTime;

    public Order(String id, String deliveryTime) {

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
        this.id = id;

        int hours = Integer.parseInt(deliveryTime.substring(0,2));
        int min = Integer.parseInt(deliveryTime.substring(3));

        int delTimeinMin = hours * 60 + min;

        this.deliveryTime = delTimeinMin;

    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}

    public void setId(String id) {
        this.id = id;
    }

    public void setDeliveryTime(int deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
}
