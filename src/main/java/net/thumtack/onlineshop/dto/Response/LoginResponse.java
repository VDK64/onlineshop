package net.thumtack.onlineshop.dto.Response;

import lombok.Data;

@Data
public class LoginResponse {
    private ResponseClientDto respClient;
    private ResponseAdminDto respAdmin;

    public String whoIs(){
        if (respClient != null)
            return "client";
        else return "admin";
    }

    @Override
    public String toString() {
        if (respAdmin != null) {
            return "" +
                    "" + respAdmin;
        } else
            return "" +
                "" + respClient;
    }

}
