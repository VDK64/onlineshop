package net.thumtack.onlineshop.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.thumtack.onlineshop.entities.Client;

@AllArgsConstructor
@Data
public class ResponseClientChangePassDto {
    private Integer id;
    private String firstName;
    private String patronymic;
    private String lastName;
    private String email;
    private String address;
    private String phone;
    private Integer deposit;

    public ResponseClientChangePassDto(Client client){
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.patronymic = client.getPatronymic();
        this.lastName = client.getLastName();
        this. email = client.getEmail();
        this.address = client.getAddress();
        this.phone = client.getPhone();
        this.deposit = client.getDeposit();
    }
}
