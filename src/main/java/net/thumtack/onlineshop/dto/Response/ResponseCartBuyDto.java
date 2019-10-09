package net.thumtack.onlineshop.dto.Response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResponseCartBuyDto {
    private List<ResponseProductBuyDto> bought = new ArrayList<>();
    private List<ResponseProductBuyDto> remaining = new ArrayList<>();
}
