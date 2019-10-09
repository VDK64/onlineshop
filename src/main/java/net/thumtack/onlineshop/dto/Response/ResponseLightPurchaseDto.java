package net.thumtack.onlineshop.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseLightPurchaseDto {
    private Integer id;
    private Integer count;
    private Integer totalPrice;
    private String clientLogin;
    private String productName;
    private Integer productId;
}
