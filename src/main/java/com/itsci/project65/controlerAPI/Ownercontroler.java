package com.itsci.project65.controlerAPI;

import jakarta.validation.Valid;
import org.springframework.web.bind.MethodArgumentNotValidException;
import com.itsci.project65.model.EquipmentOwner;
import com.itsci.project65.service.EquipmentOwnerService;
import com.itsci.project65.dto.OwnerLoginRequest;
import com.itsci.project65.dto.OwnerLoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/owner")
public class Ownercontroler {

    @Autowired
    private EquipmentOwnerService equipmentOwnerService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody OwnerLoginRequest ownerLoginRequest) {
        try {
            OwnerLoginResponse response = equipmentOwnerService.authenticateAndGenerateToken(ownerLoginRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody EquipmentOwner equipmentOwner) {

        equipmentOwnerService.createEquipmentOwner(equipmentOwner);
        return new ResponseEntity<>("สมัครสมาชิกเรียบร้อยแล้ว", HttpStatus.CREATED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>("กรุณากรอกข้อมูลให้ครบ", HttpStatus.BAD_REQUEST);
    }
}
