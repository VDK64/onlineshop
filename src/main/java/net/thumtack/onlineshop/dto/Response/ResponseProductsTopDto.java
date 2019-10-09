package net.thumtack.onlineshop.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.thumtack.onlineshop.entities.Category;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseProductsTopDto {
    private Integer productId;
    private String productName;
    private Integer revenue;
    private Integer unitSales;
    private List<Category> categoryList;
}
