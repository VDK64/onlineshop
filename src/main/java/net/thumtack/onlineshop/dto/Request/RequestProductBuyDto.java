package net.thumtack.onlineshop.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestProductBuyDto {
    private Integer id;
    private String name;
    private Integer price;
    private Integer count;

}
