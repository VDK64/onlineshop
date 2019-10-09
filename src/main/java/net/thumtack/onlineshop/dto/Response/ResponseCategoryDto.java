package net.thumtack.onlineshop.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseCategoryDto {
    private Integer id;
    private String name;
    private Integer parentId;       // только для подкатегорий
    private String parentName;      // только для подкатегорий

}
