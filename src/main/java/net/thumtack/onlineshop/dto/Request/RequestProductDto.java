package net.thumtack.onlineshop.dto.Request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.thumtack.onlineshop.entities.Product;

import java.util.List;

@NoArgsConstructor
@Data
public class RequestProductDto {
    @JsonIgnore
    private Integer id;
    private String name;
    private Integer price;
    private Integer count;
    private List<Integer> categories;

    public RequestProductDto(String name, Integer price, Integer count, List<Integer> categories) {
        this.name = name;
        this.price = price;
        this.count = count;
        this.categories = categories;
    }

    public RequestProductDto(Integer id, String name, Integer price, Integer count) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.count = count;
    }

    public Product createProductWithoutCat(){
        return new Product(this.price, this.name, this.count);
    }
}
