package net.thumtack.onlineshop.entities;

import lombok.Data;

import java.io.Serializable;

@Data
public class CartKey implements Serializable {
    private Integer userId;
    private Integer productId;
}
