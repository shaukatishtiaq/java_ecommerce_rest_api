package com.ecom.BackendForEcommerce.service.impl;

import com.ecom.BackendForEcommerce.entity.User;
import com.ecom.BackendForEcommerce.service.AddressService;
import com.ecom.BackendForEcommerce.service.DeletionUserService;
import com.ecom.BackendForEcommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class DeletionUserServiceImpl implements DeletionUserService {
    private final OrderService orderService;
    private final AddressService addressService;

    @Override
    @Transactional
    public void deleteUser(User user) {
        orderService.deleteOrdersByUser(user);
        addressService.deleteByUser(user);
    }
}
