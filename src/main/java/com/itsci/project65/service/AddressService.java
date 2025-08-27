package com.itsci.project65.service;

import com.itsci.project65.model.Address;

public interface AddressService {
    public Address createAddress(Address address);
    public Address updateAddress(Address address);
    public Address getAddressById(int addressId);
    public void deleteAddress(int addressId);
}
