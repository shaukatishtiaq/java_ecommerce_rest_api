package com.ecom.BackendForEcommerce.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_item_id")
    private Long orderItemId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "product_quantity")
    private Integer productQuantity;

    @Column(name = "total_product_price")
    private Double totalProductsPrice;

    public void calculateAndSetProductsPrice() {

        totalProductsPrice = productQuantity * product.getProductPrice();
    }
}
