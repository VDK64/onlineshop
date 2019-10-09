package net.thumtack.onlineshop.dto.Request;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RequestDepositDto {

    private Integer deposit;

    public RequestDepositDto(Integer deposit) {
        this.deposit = deposit;
    }
}
