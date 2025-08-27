package com.itsci.project65.repository;

import com.itsci.project65.model.Equipment;
import com.itsci.project65.model.EquipmentOwner;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentRepository extends JpaRepository<Equipment,Integer> {
    List<Equipment> findByEquipmentOwner(EquipmentOwner owner);
    List<Equipment> findByEquipmentType_EquipmentTypeId(int equipmentTypeId);
}
