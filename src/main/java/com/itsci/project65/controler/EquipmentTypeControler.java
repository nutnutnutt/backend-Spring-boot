package com.itsci.project65.controler;

import com.itsci.project65.model.EquipmentType;
import com.itsci.project65.service.EquipmentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipment-type")
public class EquipmentTypeControler {

    @Autowired
    private EquipmentTypeService equipmentTypeService;


    @GetMapping("/all")
    public ResponseEntity<?> getAllEquipmentType(){
        try{
            List<EquipmentType> list = equipmentTypeService.getAllEquipmentType();

            return new ResponseEntity<>(list, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ไม่พบข้อมูล");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createEquipmentType(@RequestBody EquipmentType equipmentType) {
        try {
            EquipmentType createdType = equipmentTypeService.createEquipmentType(equipmentType);
            return new ResponseEntity<>(createdType, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ไม่สามารถเพิ่มประเภทอุปกรณ์ได้");
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getEquipmentTypeById(@PathVariable("id") int id) {
        try {
            EquipmentType equipmentType = equipmentTypeService.getEquipmentTypeById(id);
            return new ResponseEntity<>(equipmentType, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping("/update")
    public ResponseEntity<?> updateEquipmentType(@RequestBody EquipmentType equipmentType) {
        try {
            EquipmentType existingType = equipmentTypeService.getEquipmentTypeById(equipmentType.getEquipmentTypeId());
            if (existingType == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ไม่พบประเภทอุปกรณ์ที่ต้องการแก้ไข");
            }

            existingType.setEquipmentTypeName(equipmentType.getEquipmentTypeName());

            EquipmentType updatedType = equipmentTypeService.updateEquipmentType(existingType);
            return new ResponseEntity<>(updatedType, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEquipmentType(@PathVariable("id") int id) {
        try {
            equipmentTypeService.deleteEquipmentType(id);
            return new ResponseEntity<>("ลบประเภทอุปกรณ์เรียบร้อย", HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}

