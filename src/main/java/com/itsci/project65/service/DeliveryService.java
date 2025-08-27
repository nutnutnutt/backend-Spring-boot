package com.itsci.project65.service;

import com.itsci.project65.model.Delivery;

public interface DeliveryService {
    public Delivery createDelivery(Delivery delivery);
    public Delivery updateDelivery(Delivery delivery);
    public Delivery getDeliveryById(int deliveryId);
    public void deleteDelivery(int deliveryId);
}
