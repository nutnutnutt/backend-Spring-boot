package com.itsci.project65.controler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itsci.project65.model.EquipmentOwner;
import com.itsci.project65.service.EquipmentOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/equipment-owner")
public class EquipmentOwnerControler {

    @Autowired
    private EquipmentOwnerService equipmentOwnerService;

    @PostMapping(value = "/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> createEquipmentOwner(
            @RequestPart("ownerData") String ownerData,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            // ใช้ ObjectMapper แปลง JSON String -> EquipmentOwner object
            ObjectMapper objectMapper = new ObjectMapper();
            EquipmentOwner equipmentOwner = objectMapper.readValue(ownerData, EquipmentOwner.class);

            // ถ้ามีการอัพโหลดไฟล์
            if (file != null && !file.isEmpty()) {
                // เช่น เซฟไฟล์ลง server แล้วเก็บ path ลง DB
                String fileName = file.getOriginalFilename();
                // TODO: save file ที่ไหนสักที่ แล้วเซ็ต path
                equipmentOwner.setOwnerImg(fileName);
            } else {
                equipmentOwner.setOwnerImg(null);
            }

            EquipmentOwner createdOwner = equipmentOwnerService.createEquipmentOwner(equipmentOwner);

            if (createdOwner == null) {
                return new ResponseEntity<>("Cannot add Equipment Owner", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(createdOwner, HttpStatus.CREATED);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + e.getMessage());
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getEquipmentOwnerById(@PathVariable("id") int id) {
        try {
            EquipmentOwner equipmentOwner = equipmentOwnerService.getEquipmentOwnerById(id);
            if (equipmentOwner == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Equipment Owner not found");
            }
            return new ResponseEntity<>(equipmentOwner, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateEquipmentOwner(@RequestBody EquipmentOwner equipmentOwner) {
        try {
            EquipmentOwner existingOwner = equipmentOwnerService.getEquipmentOwnerById(equipmentOwner.getOwnerId());
            if (existingOwner == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Equipment Owner not found");
            }

            existingOwner.setOwnerAlley(equipmentOwner.getOwnerAlley());
            existingOwner.setOwnerCFPassword(equipmentOwner.getOwnerCFPassword());
            existingOwner.setOwnerDOB(equipmentOwner.getOwnerDOB());
            existingOwner.setOwnerDistrict(equipmentOwner.getOwnerDistrict());
            existingOwner.setOwnerEmail(equipmentOwner.getOwnerEmail());
            existingOwner.setOwnerFName(equipmentOwner.getOwnerFName());
            existingOwner.setOwnerGender(equipmentOwner.getOwnerGender());
            existingOwner.setOwnerHouseNumber(equipmentOwner.getOwnerHouseNumber());
            existingOwner.setOwnerLName(equipmentOwner.getOwnerLName());
            existingOwner.setOwnerMoo(equipmentOwner.getOwnerMoo());
            existingOwner.setOwnerPassword(equipmentOwner.getOwnerPassword());
            existingOwner.setOwnerPostalCode(equipmentOwner.getOwnerPostalCode());
            existingOwner.setOwnerProvince(equipmentOwner.getOwnerProvince());
            existingOwner.setOwnerSubDistrict(equipmentOwner.getOwnerSubDistrict());
            existingOwner.setOwnerTel(equipmentOwner.getOwnerTel());
            existingOwner.setOwnerUserName(equipmentOwner.getOwnerUserName());


            EquipmentOwner updatedOwner = equipmentOwnerService.updateEquipmentOwner(existingOwner);
            return new ResponseEntity<>(updatedOwner, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEquipmentOwner(@PathVariable("id") int id) {
        try {
            equipmentOwnerService.deleteEquipmentOwner(id);
            return new ResponseEntity<>("Deleted Equipment Owner Successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }
}

