package net.thumtack.onlineshop.dto.Response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class ResponseProductInfoDto {
    private Integer id;
    private String name;
    private Integer count;
    private Integer price;
    private List<String> categoryName = new ArrayList<>();

    public ResponseProductInfoDto(Integer id, String name, Integer count, Integer price) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.price = price;
    }
}
