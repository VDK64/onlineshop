package net.thumtack.onlineshop.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseClientPurchaseDto {
    private Integer id;
    private Integer count;
    private Integer totalPrice;
    private String productName;

}
