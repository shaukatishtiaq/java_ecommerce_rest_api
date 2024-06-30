package com.ecom.BackendForEcommerce.service.impl;

import com.ecom.BackendForEcommerce.entity.Address;
import com.ecom.BackendForEcommerce.entity.User;
import com.ecom.BackendForEcommerce.exception.customexception.CommonCustomException;
import com.ecom.BackendForEcommerce.repository.AddressRepository;
import com.ecom.BackendForEcommerce.service.AddressService;
import com.ecom.BackendForEcommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final UserService userService;

    @Override
    public Address getAddressById(String userEmail, Long addressId) {
        User user = userService.findByEmail(userEmail);

        Optional<Address> addressOptional = addressRepository.findById(addressId);

        if (addressOptional.isEmpty()) {
            throw new CommonCustomException(HttpStatus.NOT_FOUND.value(), "Address with id = " + addressId + " doesn't exist.");
        }

        Address address = addressOptional.get();

        if (address.getUser().equals(user)) {
            return address;
        } else {
            throw new CommonCustomException(HttpStatus.FORBIDDEN.value(), "You can't access this resource.");
        }
    }

    @Override
    public Address createAddress(String userEmail, Address address) {
        User user = userService.findByEmail(userEmail);

        address.setUser(user);
        return addressRepository.save(address);
    }

    @Override
    public List<Address> getAllUserAddressesByUserEmail(String userEmail) {
        User user = userService.findByEmail(userEmail);

        return addressRepository.findAllByUser(user);
    }

    @Override
    public void deleteByUser(User user) {
        addressRepository.deleteAllByUser(user);
    }
}
