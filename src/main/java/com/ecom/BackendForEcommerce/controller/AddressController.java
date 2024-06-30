package com.ecom.BackendForEcommerce.controller;

import com.ecom.BackendForEcommerce.dto.address.AddressCreateDto;
import com.ecom.BackendForEcommerce.dto.address.AddressResponseDto;
import com.ecom.BackendForEcommerce.entity.Address;
import com.ecom.BackendForEcommerce.service.AddressService;
import com.ecom.BackendForEcommerce.utility.SecurityContextUtility;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;
    private final ModelMapper modelMapper;

    @PostMapping("/address")
    public ResponseEntity<AddressResponseDto> createAddress(@Valid @RequestBody AddressCreateDto addressPayload) {
        String userEmail = SecurityContextUtility.getUserEmailFromSecurityContext();

        Address address = modelMapper.map(addressPayload, Address.class);

        Address savedAddress = addressService.createAddress(userEmail, address);

        AddressResponseDto result = modelMapper.map(savedAddress, AddressResponseDto.class);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/address")
    public ResponseEntity<List<AddressResponseDto>> getAllUserAddressesByUserId() {
        String userEmail = SecurityContextUtility.getUserEmailFromSecurityContext();

        List<Address> addresses = addressService.getAllUserAddressesByUserEmail(userEmail);

        List<AddressResponseDto> result = addresses.stream().map((address) -> {
            return modelMapper.map(address, AddressResponseDto.class);
        }).toList();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/address/{addressId}")
    public ResponseEntity<AddressResponseDto> getAddressById(@PathVariable("addressId") Long addressId) {

        String userEmail = SecurityContextUtility.getUserEmailFromSecurityContext();

        Address savedAddress = addressService.getAddressById(userEmail, addressId);

        AddressResponseDto result = modelMapper.map(savedAddress, AddressResponseDto.class);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
