package com.itsci.project65.controlerAPI;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itsci.project65.model.Farmer;
import com.itsci.project65.service.FarmerService;
import com.itsci.project65.dto.LoginRequest;
import com.itsci.project65.dto.LoginResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@RestController
@RequestMapping("/api/farmer")
public class Farmercontroler {

    @Autowired
    private FarmerService farmerService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse response = farmerService.authenticateAndGenerateToken(loginRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestParam("farmer") String farmerStr,
            @RequestParam(value = "file", required = false) MultipartFile file) {

        System.out.println("กำลังประมวลผลหลังบ้าน");
        System.out.println("farmerStr : " + farmerStr);
        System.out.println("file : " + file);


        try {
            Farmer farmer = objectMapper.readValue(farmerStr, Farmer.class);

            // ตรวจสอบว่ามีไฟล์อัพโหลดมาด้วยไหม
            if (file != null && !file.isEmpty()) {
                // กำหนดโฟลเดอร์เก็บไฟล์ (เช่น src/main/resources/static/uploads)
                String uploadDir = "uploads/farmer";
                File uploadPath = new File(uploadDir);
                if (!uploadPath.exists()) {
                    uploadPath.mkdirs();
                }

                // ตั้งชื่อไฟล์ (กันชื่อซ้ำ โดยใช้ UUID)
                String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                Path filePath = Paths.get(uploadDir, fileName);

                // เซฟไฟล์ลงเครื่อง
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                // เก็บ path หรือชื่อไฟล์ไว้ใน farmer
                farmer.setFarmerImg(fileName);
            } else {
                farmer.setFarmerImg(null); // ถ้าไม่มีไฟล์
            }

            farmerService.createFarmer(farmer, file);
            return new ResponseEntity<>("สมัครสมาชิกเรียบร้อยแล้ว", HttpStatus.CREATED);

        } catch (IOException e) {
            return new ResponseEntity<>("Error parsing farmer data: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/update/{farmerId}")
    public ResponseEntity<Farmer> updateFarmer(@PathVariable int farmerId, @RequestParam("farmer") String farmerStr, @RequestParam(value = "file", required = false) MultipartFile file) {
        try {
            Farmer farmerDetails = objectMapper.readValue(farmerStr, Farmer.class);
            Farmer updatedFarmer = farmerService.updateFarmer(farmerId, farmerDetails, file);
            return new ResponseEntity<>(updatedFarmer, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{farmerId}")
    public ResponseEntity<Farmer> getFarmerById(@PathVariable int farmerId) {
        Farmer farmer = farmerService.getFarmerById(farmerId);
        return new ResponseEntity<>(farmer, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{farmerId}")
    public ResponseEntity<String> deleteFarmer(@PathVariable int farmerId) {
        farmerService.deleteFarmer(farmerId);
        return new ResponseEntity<>("Farmer deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/image/{filename:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            Path imagePath = Paths.get("uploads/farmer").resolve(filename).normalize();
            Resource resource = new UrlResource(imagePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                String contentType = Files.probeContentType(imagePath);
                if (contentType == null) {
                    contentType = "application/octet-stream";
                }

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}


