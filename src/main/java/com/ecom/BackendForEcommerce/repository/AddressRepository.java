package com.ecom.BackendForEcommerce.repository;

import com.ecom.BackendForEcommerce.entity.Address;
import com.ecom.BackendForEcommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findAllByUser(User user);

    void deleteAllByUser(User user);
}
