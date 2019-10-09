package net.thumtack.onlineshop.dto.Response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResponseCartDto {
    private List<ResponseProductBuyDto> cart = new ArrayList<>();

    @Override
    public String toString() {
        return "{" + cart +
                '}';
    }
}
