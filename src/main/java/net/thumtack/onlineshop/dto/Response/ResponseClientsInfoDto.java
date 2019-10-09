package net.thumtack.onlineshop.dto.Response;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.thumtack.onlineshop.entities.Client;

@NoArgsConstructor
@Data
public class ResponseClientsInfoDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String email;
    private String address;
    private String phone;
    private static final String userType = "client";

    public ResponseClientsInfoDto(Client client){
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.patronymic = client.getPatronymic();
        this.email = client.getEmail();
        this.address = client.getAddress();
        this.phone = client.getPhone();

    }
}
