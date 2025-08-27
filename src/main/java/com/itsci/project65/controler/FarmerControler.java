package com.itsci.project65.controler;

import com.itsci.project65.model.Farmer;
import com.itsci.project65.service.FarmerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/farmer")
public class FarmerControler {

    @Autowired
    FarmerService farmerService;

    @PostMapping("/create")
    ResponseEntity<?> createFarmer(@RequestBody Farmer farmer){
        try{
            // Passing null for the file parameter as this controller does not handle file uploads
            Farmer createdFarmer = farmerService.createFarmer(farmer, null);
            if (createdFarmer == null){
                return new ResponseEntity<>("Cannot add Farmer", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(createdFarmer,HttpStatus.CREATED);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getFarmerById(@PathVariable("id") int id ){
        try{
            Farmer farmer = farmerService.getFarmerById(id);
            if (farmer == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return  new ResponseEntity<>(farmer,HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/update/{id}")
    ResponseEntity<?> updateFarmer(@PathVariable("id") int id, @RequestBody Farmer farmer){
        try {
            // The service now handles getting the existing farmer
            // Passing null for the file parameter
            Farmer updatedFarmer = farmerService.updateFarmer(id, farmer, null);

            if (updatedFarmer == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            return new ResponseEntity<>(updatedFarmer,HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<?> deleteFarmer(@PathVariable("id") int id){
        try{
            farmerService.deleteFarmer(id);
            return new ResponseEntity<>("DELETED FARMER SUCCESSFUL",HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
