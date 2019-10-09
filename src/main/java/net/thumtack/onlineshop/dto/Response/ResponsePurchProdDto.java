package net.thumtack.onlineshop.dto.Response;

import lombok.Data;
import net.thumtack.onlineshop.entities.Category;
import net.thumtack.onlineshop.entities.Product;

import java.util.List;

@Data
public class ResponsePurchProdDto {
    private Integer id;
    private String name;
    private Integer price;
    private Integer count;
    private List<Category> categoryList;


    public ResponsePurchProdDto(Product product){
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.count = product.getCount();
        this.categoryList = product.getCategories();
    }
}
