package com.itsci.project65.controlerAPI;

import com.itsci.project65.model.Equipment;
import com.itsci.project65.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/equipment")
public class Equipmentcontroller {

    @Autowired
    private EquipmentService equipmentService;

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addEquipment(@RequestBody Equipment equipment) {
        Map<String, Object> response = new HashMap<>();
        try {
                        // Passing null for the file as this legacy endpoint doesn't support file uploads.
            Equipment createdEquipment = equipmentService.createEquipment(equipment, null);
            response.put("success", true);
            response.put("message", "Equipment added successfully");
            response.put("data", createdEquipment);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to add equipment: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getall")
    public ResponseEntity<Map<String, Object>> getAllEquipments() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Equipment> equipments = equipmentService.getAllEquipments();
            response.put("success", true);
            response.put("message", "Equipments retrieved successfully");
            response.put("data", equipments);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to retrieve equipments: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Map<String, Object>> getEquipmentById(@PathVariable("id") int id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Equipment equipment = equipmentService.getEquipmentById(id);
            response.put("success", true);
            response.put("message", "Equipment retrieved successfully");
            response.put("data", equipment);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to retrieve equipment: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> updateEquipment(@PathVariable("id") int id, @RequestBody Equipment equipment) {
        Map<String, Object> response = new HashMap<>();
        try {
            equipment.setEquipmentId(id);
            Equipment updatedEquipment = equipmentService.updateEquipment(equipment);
            response.put("success", true);
            response.put("message", "Equipment updated successfully");
            response.put("data", updatedEquipment);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to update equipment: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteEquipment(@PathVariable("id") int id) {
        Map<String, Object> response = new HashMap<>();
        try {
            equipmentService.deleteEquipment(id);
            response.put("success", true);
            response.put("message", "Equipment deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to delete equipment: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
