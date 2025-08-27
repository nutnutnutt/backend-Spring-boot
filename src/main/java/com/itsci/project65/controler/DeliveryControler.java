package com.itsci.project65.controler;

import com.itsci.project65.model.Delivery;
import com.itsci.project65.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/delivery")
public class DeliveryControler {

    @Autowired
    private DeliveryService deliveryService;


    @PostMapping("/create")
    public ResponseEntity<?> createDelivery(@RequestBody Delivery delivery) {
        try {
            Delivery createdDelivery = deliveryService.createDelivery(delivery);
            return new ResponseEntity<>(createdDelivery, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ไม่สามารถเพิ่มข้อมูลการจัดส่งได้: " + e.getMessage());
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getDeliveryById(@PathVariable("id") int id) {
        try {
            Delivery delivery = deliveryService.getDeliveryById(id);
            return new ResponseEntity<>(delivery, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("เกิดข้อผิดพลาด: " + e.getMessage());
        }
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateDelivery(@PathVariable("id") int id, @RequestBody Delivery delivery) {
        try {
            Delivery existingDelivery = deliveryService.getDeliveryById(id);
            if (existingDelivery == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ไม่พบข้อมูลการจัดส่งที่ต้องการแก้ไข");
            }

            existingDelivery.setDeliveryDetail(delivery.getDeliveryDetail());
            existingDelivery.setDeliveryPickUpDate(delivery.getDeliveryPickUpDate());
            existingDelivery.setReturnStatus(delivery.getReturnStatus());
            existingDelivery.setUpdateStatus(delivery.getUpdateStatus());

            Delivery updatedDelivery = deliveryService.updateDelivery(existingDelivery);
            return new ResponseEntity<>(updatedDelivery, HttpStatus.OK);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("เกิดข้อผิดพลาด: " + e.getMessage());
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteDelivery(@PathVariable("id") int id) {
        try {
            deliveryService.deleteDelivery(id);
            return new ResponseEntity<>("ลบข้อมูลการจัดส่งเรียบร้อย", HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("เกิดข้อผิดพลาด: " + e.getMessage());
        }
    }
}

