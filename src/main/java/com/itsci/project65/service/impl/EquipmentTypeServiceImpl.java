package com.itsci.project65.service.impl;

import com.itsci.project65.model.EquipmentType;
import com.itsci.project65.repository.EquipmentTypeRepository;
import com.itsci.project65.service.EquipmentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipmentTypeServiceImpl implements EquipmentTypeService {

    @Autowired
    private EquipmentTypeRepository equipmentTypeRepository;

    @Override
    public EquipmentType createEquipmentType(EquipmentType equipmentType) {
        return equipmentTypeRepository.save(equipmentType);
    }

    @Override
    public EquipmentType updateEquipmentType(EquipmentType equipmentType) {
        Optional<EquipmentType> existingType = equipmentTypeRepository.findById(equipmentType.getEquipmentTypeId());
        if (existingType.isPresent()) {
            return equipmentTypeRepository.save(equipmentType);
        } else {
            throw new RuntimeException("ไม่พบประเภทอุปกรณ์ที่ต้องการแก้ไข (ID: " + equipmentType.getEquipmentTypeId() + ")");
        }
    }

    @Override
    public EquipmentType getEquipmentTypeById(int equipmentTypeId) {
        return equipmentTypeRepository.findById(equipmentTypeId)
                .orElseThrow(() -> new RuntimeException("ไม่พบประเภทอุปกรณ์ที่ต้องการค้นหา (ID: " + equipmentTypeId + ")"));
    }

    @Override
    public void deleteEquipmentType(int equipmentTypeId) {
        Optional<EquipmentType> existingType = equipmentTypeRepository.findById(equipmentTypeId);
        if (existingType.isPresent()) {
            equipmentTypeRepository.delete(existingType.get());
        } else {
            throw new RuntimeException("ไม่พบประเภทอุปกรณ์ที่ต้องการลบ (ID: " + equipmentTypeId + ")");
        }
    }

    @Override
    public List<EquipmentType> getAllEquipmentType() {
        return equipmentTypeRepository.findAll();
    }

}
