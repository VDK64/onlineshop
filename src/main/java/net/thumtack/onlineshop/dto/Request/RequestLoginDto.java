package net.thumtack.onlineshop.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class RequestLoginDto {

    private String login;
    private String password;
}
