package com.itsci.project65.service.impl;

import com.itsci.project65.model.EquipmentOwner;
import com.itsci.project65.repository.EquipmentOwnerRepository;
import com.itsci.project65.service.EquipmentOwnerService;
import com.itsci.project65.dto.OwnerLoginRequest;
import com.itsci.project65.dto.OwnerLoginResponse;
import com.itsci.project65.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EquipmentOwnerServiceImpl implements EquipmentOwnerService {

    @Autowired
    private EquipmentOwnerRepository equipmentOwnerRepository;
    
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public EquipmentOwner createEquipmentOwner(EquipmentOwner equipmentOwner) {
        return equipmentOwnerRepository.save(equipmentOwner);
    }

    @Override
    public EquipmentOwner updateEquipmentOwner(EquipmentOwner equipmentOwner) {
        Optional<EquipmentOwner> existingOwner = equipmentOwnerRepository.findById(equipmentOwner.getOwnerId());
        if (existingOwner.isPresent()) {
            return equipmentOwnerRepository.save(equipmentOwner);
        } else {
            throw new RuntimeException("ไม่พบเจ้าของอุปกรณ์ที่ต้องการแก้ไข (ID: " + equipmentOwner.getOwnerId() + ")");
        }
    }

    @Override
    public EquipmentOwner getEquipmentOwnerById(int ownerId) {
        return equipmentOwnerRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("ไม่พบเจ้าของอุปกรณ์ที่ต้องการค้นหา (ID: " + ownerId + ")"));
    }

    @Override
    public void deleteEquipmentOwner(int ownerId) {
        Optional<EquipmentOwner> existingOwner = equipmentOwnerRepository.findById(ownerId);
        if (existingOwner.isPresent()) {
            equipmentOwnerRepository.delete(existingOwner.get());
        } else {
            throw new RuntimeException("ไม่พบเจ้าของอุปกรณ์ที่ต้องการลบ (ID: " + ownerId + ")");
        }
    }

    @Override
    public EquipmentOwner login(String ownerUserName, String ownerPassword) {
        EquipmentOwner owner = equipmentOwnerRepository.findByOwnerUserName(ownerUserName);
        if (owner != null && owner.getOwnerPassword() != null && owner.getOwnerPassword().equals(ownerPassword)) {
            return owner;
        }
        return null;
    }

    @Override
    public OwnerLoginResponse authenticateAndGenerateToken(OwnerLoginRequest ownerLoginRequest) {
        EquipmentOwner owner = login(ownerLoginRequest.getOwnerUserName(), ownerLoginRequest.getOwnerPassword());
        
        if (owner != null) {
            String token = jwtUtil.generateTokenForOwner(owner.getOwnerUserName(), owner.getOwnerId());
            return new OwnerLoginResponse(token, owner.getOwnerId(), owner.getOwnerUserName(), "เข้าสู่ระบบสำเร็จ");
        } else {
            throw new RuntimeException("ชื่อผู้ใช้หรือรหัสผ่านไม่ถูกต้อง");
        }
    }
}
