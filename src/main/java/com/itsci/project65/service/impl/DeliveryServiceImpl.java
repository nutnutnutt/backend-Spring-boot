package com.itsci.project65.service.impl;

import com.itsci.project65.model.Delivery;
import com.itsci.project65.repository.DeliveryRepository;
import com.itsci.project65.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Override
    public Delivery createDelivery(Delivery delivery) {
        return deliveryRepository.save(delivery);
    }

    @Override
    public Delivery updateDelivery(Delivery delivery) {
        Optional<Delivery> existingDelivery = deliveryRepository.findById(delivery.getDeliveryId());
        if (existingDelivery.isPresent()) {
            return deliveryRepository.save(delivery);
        } else {
            throw new RuntimeException("ไม่พบข้อมูลการจัดส่งที่ต้องการแก้ไข (ID: " + delivery.getDeliveryId() + ")");
        }
    }

    @Override
    public Delivery getDeliveryById(int deliveryId) {
        return deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new RuntimeException("ไม่พบข้อมูลการจัดส่งที่ต้องการค้นหา (ID: " + deliveryId + ")"));
    }

    @Override
    public void deleteDelivery(int deliveryId) {
        Optional<Delivery> existingDelivery = deliveryRepository.findById(deliveryId);
        if (existingDelivery.isPresent()) {
            deliveryRepository.delete(existingDelivery.get());
        } else {
            throw new RuntimeException("ไม่พบข้อมูลการจัดส่งที่ต้องการลบ (ID: " + deliveryId + ")");
        }
    }
}
