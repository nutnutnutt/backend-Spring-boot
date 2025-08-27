package com.itsci.project65.controler;

import com.itsci.project65.model.Equipment;
import com.itsci.project65.model.EquipmentType;
import com.itsci.project65.model.EquipmentOwner;
import com.itsci.project65.repository.EquipmentOwnerRepository;
import com.itsci.project65.repository.EquipmentRepository;
import com.itsci.project65.service.EquipmentService;
import com.itsci.project65.service.EquipmentTypeService;
import com.itsci.project65.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/equipment")
public class EquipmentControler {

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EquipmentOwnerRepository equipmentOwnerRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private EquipmentTypeService equipmentTypeService;

    private final String IMAGE_UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/images/";


    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createEquipment(
            @RequestParam("equipmentName") String equipmentName,
            @RequestParam("equipmentTypeId") int typeId,
            @RequestParam("properties") String properties,
            @RequestParam("description") String description,
            @RequestParam("address") String address,
            @RequestParam("price") double price,
            @RequestParam(value = "image", required = false) MultipartFile file,
            @RequestHeader("Authorization") String authHeader
    ) {
        Map<String, Object> response = new HashMap<>();
        try {
            // 1. Extract token and find owner
            String token = authHeader.substring(7);
            String ownerUserName = jwtUtil.extractUsername(token);
            EquipmentOwner owner = equipmentOwnerRepository.findByOwnerUserName(ownerUserName);

            if (owner == null) {
                response.put("status", "error");
                response.put("message", "Invalid token or owner not found.");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            // 2. Prepare Equipment object
            Equipment equipment = new Equipment();
            equipment.setEquipmentName(equipmentName);
            equipment.setEquipmentDetails(description);
            equipment.setEquipmentFeature(properties);
            equipment.setEquipmentAddress(address);
            equipment.setPrice((int) price);
            equipment.setEquipmentStatus("Available");
            equipment.setEquipmentOwner(owner);

            // 3. Prepare EquipmentType
            EquipmentType equipmentType = equipmentTypeService.getEquipmentTypeById(typeId);
            System.out.println(equipmentType.getEquipmentTypeId());
            equipment.setEquipmentType(equipmentType);
            // 4. Delegate creation and file upload to the transactional service
            Equipment savedEquipment = equipmentService.createEquipment(equipment, file);

            // 5. Build success response
            response.put("status", "success");
            response.put("message", "Equipment created successfully!");
            response.put("equipment", savedEquipment);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Error creating equipment: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all-by-type/{id}")
    public ResponseEntity<List<Equipment>> getAllEqByTypeId(@PathVariable("id") int id) {
        try {
            List<Equipment> equipments = equipmentService.getAllByTypeID(id);
            return new ResponseEntity<>(equipments, HttpStatus.OK);

        } catch (Exception e) {
            // Log the exception for debugging purposes
            System.err.println("Error fetching owner equipment: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/my-equipment")
    public ResponseEntity<List<Equipment>> getOwnerEquipment(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7);
            String ownerUserName = jwtUtil.extractUsername(token);
            EquipmentOwner owner = equipmentOwnerRepository.findByOwnerUserName(ownerUserName);

            if (owner == null) {
                // Return 401 Unauthorized if the token is invalid or owner doesn't exist
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            List<Equipment> equipments = equipmentRepository.findByEquipmentOwner(owner);
            return new ResponseEntity<>(equipments, HttpStatus.OK);

        } catch (Exception e) {
            // Log the exception for debugging purposes
            System.err.println("Error fetching owner equipment: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<?> getEquipmentById(@PathVariable("id") int id) {
        try {
            Equipment equipment = equipmentService.getEquipmentById(id);
            return new ResponseEntity<>(equipment, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEquipment(
            @PathVariable("id") int id,
            @RequestParam("equipmentName") String equipmentName,
            @RequestParam("equipmentDetails") String equipmentDetails,
            @RequestParam("equipmentFeature") String equipmentFeature,
            @RequestParam("equipmentList") String equipmentList,
            @RequestParam("equipmentAddress") String equipmentAddress,
            @RequestParam("price") double price,
            @RequestParam("equipmentStatus") String equipmentStatus,
            @RequestParam(value = "image", required = false) MultipartFile file,
            @RequestHeader("Authorization") String authHeader
    ) {
        try {
            // 1. Get existing equipment
            Equipment existingEquipment = equipmentService.getEquipmentById(id);

            // 2. Verify ownership via JWT
            String token = authHeader.substring(7);
            String ownerUserName = jwtUtil.extractUsername(token);
            EquipmentOwner ownerFromToken = equipmentOwnerRepository.findByOwnerUserName(ownerUserName);

            if (ownerFromToken == null || existingEquipment.getEquipmentOwner().getOwnerId() != ownerFromToken.getOwnerId()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to update this equipment.");
            }

            // 3. Handle file upload
            String fileName = existingEquipment.getEquipmentImg();
            if (file != null && !file.isEmpty()) {
                fileName = System.currentTimeMillis() + "_" + StringUtils.cleanPath(file.getOriginalFilename());
                File uploadDir = new File(IMAGE_UPLOAD_DIR);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                file.transferTo(new File(IMAGE_UPLOAD_DIR + fileName));
                existingEquipment.setEquipmentImg(fileName);
            }

            // 4. Update equipment details
            existingEquipment.setEquipmentName(equipmentName);
            existingEquipment.setEquipmentDetails(equipmentDetails);
            existingEquipment.setEquipmentFeature(equipmentFeature);
            existingEquipment.setEquipmentAddress(equipmentAddress);
            existingEquipment.setPrice((int) price);
            existingEquipment.setEquipmentStatus(equipmentStatus);
            // The owner is already set and verified, no need to change it.

            // 5. Save updated equipment
            Equipment updatedEquipment = equipmentService.updateEquipment(existingEquipment);
            return new ResponseEntity<>(updatedEquipment, HttpStatus.OK);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File Upload Error: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEquipment(@PathVariable("id") int id) {
        try {
            equipmentService.deleteEquipment(id);
            return new ResponseEntity<>("Deleted equipment successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }


    @GetMapping("/image/{filename:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            Path imagePath = Paths.get("uploads/images").resolve(filename).normalize();
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
