package net.thumtack.onlineshop.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequestCategoryDto {

    private String name;
    private Integer parentId;

}
