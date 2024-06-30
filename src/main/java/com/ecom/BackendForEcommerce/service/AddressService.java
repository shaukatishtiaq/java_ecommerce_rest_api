package com.ecom.BackendForEcommerce.service;

import com.ecom.BackendForEcommerce.entity.Address;
import com.ecom.BackendForEcommerce.entity.User;

import java.util.List;

public interface AddressService {

    Address getAddressById(String userEmail, Long addressId);

    Address createAddress(String userEmail, Address address);

    List<Address> getAllUserAddressesByUserEmail(String userEmail);

    void deleteByUser(User user);
}
