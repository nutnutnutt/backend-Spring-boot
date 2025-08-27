package com.itsci.project65.controler;

import com.itsci.project65.model.Address;
import com.itsci.project65.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/address")
public class AddressControler {

    @Autowired
    private AddressService addressService;

    @PostMapping("/create")
    public ResponseEntity<?> createAddress(@RequestBody Address address) {
        try {
            Address createdAddress = addressService.createAddress(address);
            return new ResponseEntity<>(createdAddress, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ไม่สามารถเพิ่มที่อยู่ได้");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAddressById(@PathVariable("id") int id) {
        try {
            Address address = addressService.getAddressById(id);
            return new ResponseEntity<>(address, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateAddress(@RequestBody Address address) {
        try {
            Address existingAddress = addressService.getAddressById(address.getAddressId());
            if (existingAddress == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ไม่พบที่อยู่ที่ต้องการแก้ไข");
            }

            existingAddress.setAddressDetails(address.getAddressDetails());
            existingAddress.setAddressName(address.getAddressName());


            Address updatedAddress = addressService.updateAddress(existingAddress);
            return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable("id") int id) {
        try {
            addressService.deleteAddress(id);
            return new ResponseEntity<>("ลบที่อยู่เรียบร้อย", HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

