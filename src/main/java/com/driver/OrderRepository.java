package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderRepository {

    //order->time
    Map<String, Order> orders =  new HashMap<>();

    //partner->count of orders
    Map<String, DeliveryPartner>partners = new HashMap<>();

    //order->partner
    Map<String, String>OrderPartnerPair = new HashMap<>();

    //partner->{order,...}
    Map<String, List<String>>PartnerOrderList = new HashMap<>();

    public void addOrder(Order order) {
        if(!orders.containsKey(order.getId())) {
            orders.put(order.getId(), order);
        }
    }

    public void addPartner(String partnerId) {

           DeliveryPartner deliveryPartner = new DeliveryPartner(partnerId);
           partners.put(partnerId, deliveryPartner);

    }

    public void addOrderPartnerPair(String orderId, String partnerId) {

        if(orders.containsKey(orderId) && partners.containsKey(partnerId)) {
            OrderPartnerPair.put(orderId, partnerId);
            List<String> ord = new ArrayList<>();
            if(PartnerOrderList.containsKey(partnerId)){
                    ord = PartnerOrderList.get(partnerId);
                    ord.add(orderId);
            }
            else{
                ord.add(orderId);
            }
            PartnerOrderList.put(partnerId, ord);
            DeliveryPartner deliveryPartner = partners.get(partnerId);
            deliveryPartner.setNumberOfOrders(ord.size());
        }


    }

    public Order getOrderById(String orderId) {

            return orders.get(orderId);

    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return  partners.get(partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        List<String>orderlist = PartnerOrderList.get(partnerId);

        return orderlist.size();

    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        return PartnerOrderList.get(partnerId);
    }

    public List<String> getAllOrders() {
        List<String>orderlist = new ArrayList<>();

        for(String ord : orders.keySet()){
            orderlist.add(ord);
        }

        return orderlist;
    }

    public Integer getCountOfUnassignedOrders() {
        int count=0;
        for(Map.Entry<String, String> entry : OrderPartnerPair.entrySet()){
            if(entry.getValue()==null){
                count++;
            }
        }

        return count;
        //return orders.size()-OrderPartnerPair.size();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(int time, String partnerId) {

        int count=0;
        List<String>list = PartnerOrderList.get(partnerId);
        for (String orderId : list){
            int delTime = orders.get(orderId).getDeliveryTime();
            if(delTime>time){
                count++;
            }
        }

        return count;
    }

    public int getLastDeliveryTimeByPartnerId(String partnerId) {
        List<String>list = PartnerOrderList.get(partnerId);

        int maxTime = 0;
        for(String order : list){
            int currTime = orders.get(order).getDeliveryTime();
            maxTime = Math.max(maxTime, currTime);
        }
        return maxTime;
    }

    public void deletePartnerById(String partnerId) {
        partners.remove(partnerId);
        List<String> list = PartnerOrderList.get(partnerId);
        PartnerOrderList.remove(partnerId);

        for (String orderId : list){
            OrderPartnerPair.remove(orderId);
        }
    }

    public void deleteOrderById(String orderId) {

        orders.remove(orderId);
        String partnerId = OrderPartnerPair.get(orderId);

        PartnerOrderList.get(partnerId).remove(orderId);

        OrderPartnerPair.remove(orderId);

        partners.get(partnerId).setNumberOfOrders(PartnerOrderList.get(partnerId).size());


        }
    }



