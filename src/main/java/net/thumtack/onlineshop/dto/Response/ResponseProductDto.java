package net.thumtack.onlineshop.dto.Response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.thumtack.onlineshop.dto.Request.RequestProductDto;

import java.util.List;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class ResponseProductDto extends RequestProductDto {

    private Integer id;

    public ResponseProductDto(Integer id, String name, Integer price, Integer count, List<Integer> categories) {
        super(name, price, count, categories);
        this.id = id;
    }

    public ResponseProductDto(Integer id, String name, Integer price, Integer count) {
        super(id, name, price, count);
    }
}
