package com.itsci.project65.repository;

import com.itsci.project65.model.EquipmentOwner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentOwnerRepository extends JpaRepository<EquipmentOwner,Integer> {
    EquipmentOwner findByOwnerUserName(String ownerUserName);
}
