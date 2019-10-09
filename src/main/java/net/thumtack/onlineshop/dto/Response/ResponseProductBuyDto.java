package net.thumtack.onlineshop.dto.Response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.thumtack.onlineshop.dto.Request.RequestProductBuyDto;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class ResponseProductBuyDto extends RequestProductBuyDto {
    public ResponseProductBuyDto(Integer id, String name, Integer price, Integer count){
        super(id, name, price, count);
    }
}