package com.itsci.project65.service;

import com.itsci.project65.model.EquipmentOwner;
import com.itsci.project65.dto.OwnerLoginRequest;
import com.itsci.project65.dto.OwnerLoginResponse;

public interface EquipmentOwnerService {
    public EquipmentOwner createEquipmentOwner(EquipmentOwner equipmentOwner);
    public EquipmentOwner updateEquipmentOwner(EquipmentOwner equipmentOwner);
    public EquipmentOwner getEquipmentOwnerById(int ownerId);
    public void deleteEquipmentOwner(int ownerId);
    public EquipmentOwner login(String ownerUserName, String ownerPassword);
    public OwnerLoginResponse authenticateAndGenerateToken(OwnerLoginRequest ownerLoginRequest);
}

