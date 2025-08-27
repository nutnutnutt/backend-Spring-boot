package com.itsci.project65.service.impl;

import com.itsci.project65.model.Address;
import com.itsci.project65.repository.AddressRepository;
import com.itsci.project65.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Address createAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Address updateAddress(Address address) {
        Optional<Address> existingAddress = addressRepository.findById(address.getAddressId());
        if (existingAddress.isPresent()) {
            return addressRepository.save(address);
        } else {
            throw new RuntimeException("ไม่พบที่อยู่ที่ต้องการแก้ไข (ID: " + address.getAddressId() + ")");
        }
    }

    @Override
    public Address getAddressById(int addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("ไม่พบที่อยู่ที่ต้องการค้นหา (ID: " + addressId + ")"));
    }

    @Override
    public void deleteAddress(int addressId) {
        Optional<Address> existingAddress = addressRepository.findById(addressId);
        if (existingAddress.isPresent()) {
            addressRepository.delete(existingAddress.get());
        } else {
            throw new RuntimeException("ไม่พบที่อยู่ที่ต้องการลบ (ID: " + addressId + ")");
        }
    }
}

