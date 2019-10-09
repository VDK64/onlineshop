package net.thumtack.onlineshop.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponsePurchaseDto {
    private Integer id;
    private Integer count;
    private Integer totalPrice;
    private ResponseClientDto client;
    private ResponsePurchProdDto product;

}
