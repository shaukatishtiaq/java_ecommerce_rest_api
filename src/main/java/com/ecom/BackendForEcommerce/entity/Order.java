package com.ecom.BackendForEcommerce.entity;

import com.ecom.BackendForEcommerce.enums.OrderStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Long orderId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address orderAddress;

    private Double totalOrderPrice;

    @Column(name = "mobile_number", nullable = false)
    private String mobileNumber;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum orderStatusEnum;

//    // Instance initializer block
//    {
//        if (orderItems == null) {
//            this.orderItems = new ArrayList<>();
//        }
//    }

    //    Methods
    public void calculateAndSetTotalOrderPrice() {
        double price = 0;

        for (OrderItem orderItem : orderItems) {
            price += orderItem.getTotalProductsPrice();
        }
        this.totalOrderPrice = price;
    }

}

