package com.itsci.project65.service.impl;

import com.itsci.project65.model.Equipment;
import com.itsci.project65.model.EquipmentOwner;
import com.itsci.project65.repository.EquipmentOwnerRepository;
import com.itsci.project65.repository.EquipmentRepository;
import com.itsci.project65.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipmentServiceImpl implements EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private EquipmentOwnerRepository equipmentOwnerRepository;

    private final String IMAGE_UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/images/";

    @Override
    @org.springframework.transaction.annotation.Transactional
    public Equipment createEquipment(Equipment equipment, org.springframework.web.multipart.MultipartFile file) {
        // Step 1: Ensure owner is valid and attached
        if (equipment.getEquipmentOwner() == null || equipment.getEquipmentOwner().getOwnerId() <= 0) {
            throw new RuntimeException("Owner is required to create equipment.");
        }
        EquipmentOwner owner = equipmentOwnerRepository.findById(equipment.getEquipmentOwner().getOwnerId())
                .orElseThrow(() -> new RuntimeException("Owner not found with id: " + equipment.getEquipmentOwner().getOwnerId()));
        equipment.setEquipmentOwner(owner);

        // Step 2: Save equipment to generate ID (equipmentImg is null at this point)
        Equipment savedEquipment = equipmentRepository.save(equipment);

        // Step 3: Handle file upload if a file is provided
        if (file != null && !file.isEmpty()) {
            try {
                String originalFileName = org.springframework.util.StringUtils.cleanPath(file.getOriginalFilename());
                String fileExtension = "";
                int i = originalFileName.lastIndexOf('.');
                if (i > 0) {
                    fileExtension = originalFileName.substring(i);
                }
                String newFileName = savedEquipment.getEquipmentId() + fileExtension;

                java.io.File uploadDir = new java.io.File(IMAGE_UPLOAD_DIR);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                file.transferTo(new java.io.File(uploadDir, newFileName));

                // Step 4: Set the image name on the entity
                savedEquipment.setEquipmentImg(newFileName);
                // No need to call save again, transaction will handle it
            } catch (java.io.IOException e) {
                // Using RuntimeException to trigger transaction rollback
                throw new RuntimeException("Failed to store file: " + e.getMessage());
            }
        }

        return savedEquipment;
    }

    @Override
    public Equipment updateEquipment(Equipment equipment) {
        Optional<Equipment> existingEquipmentOpt = equipmentRepository.findById(equipment.getEquipmentId());
        if (existingEquipmentOpt.isPresent()) {
            Equipment existingEquipment = existingEquipmentOpt.get();

            // Update fields from the request
            existingEquipment.setEquipmentName(equipment.getEquipmentName());
            existingEquipment.setPrice(equipment.getPrice());
            existingEquipment.setEquipmentDetails(equipment.getEquipmentDetails());
            existingEquipment.setEquipmentStatus(equipment.getEquipmentStatus());
            existingEquipment.setEquipmentFeature(equipment.getEquipmentFeature());
            existingEquipment.setEquipmentAddress(equipment.getEquipmentAddress());
            existingEquipment.setEquipmentImg(equipment.getEquipmentImg());
            existingEquipment.setViewsReviews(equipment.getViewsReviews());

            if (equipment.getEquipmentOwner() != null && equipment.getEquipmentOwner().getOwnerId() > 0) {
                EquipmentOwner owner = equipmentOwnerRepository.findById(equipment.getEquipmentOwner().getOwnerId())
                        .orElseThrow(() -> new RuntimeException("Owner not found with id: " + equipment.getEquipmentOwner().getOwnerId()));
                existingEquipment.setEquipmentOwner(owner);
            }

            return equipmentRepository.save(existingEquipment);
        } else {
            throw new RuntimeException("ไม่พบอุปกรณ์ที่ต้องการแก้ไข (ID: " + equipment.getEquipmentId() + ")");
        }
    }

    @Override
    public Equipment getEquipmentById(int equipmentId) {
        return equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new RuntimeException("ไม่พบอุปกรณ์ที่ต้องการค้นหา (ID: " + equipmentId + ")"));
    }

    @Override
    public void deleteEquipment(int equipmentId) {
        Optional<Equipment> existingEquipment = equipmentRepository.findById(equipmentId);
        if (existingEquipment.isPresent()) {
            equipmentRepository.delete(existingEquipment.get());
        } else {
            throw new RuntimeException("ไม่พบอุปกรณ์ที่ต้องการลบ (ID: " + equipmentId + ")");
        }
    }

    @Override
    public List<Equipment> getAllEquipments() {
        return equipmentRepository.findAll();
    }

    @Override
    public List<Equipment> getAllByTypeID(int typeId) {
        return equipmentRepository.findByEquipmentType_EquipmentTypeId(typeId);
    }


}
