package net.thumtack.onlineshop.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.thumtack.onlineshop.entities.Client;

@AllArgsConstructor
@Data
public class RequestClientDto {
    private String firstName;
    private String lastName;
    private String patronymic;
    private String email;
    private String address;
    private String phone;
    private String login;
    private String password;

    public Client createClient(){
        return new Client(firstName, patronymic, lastName, email, address, phone, login, password);
    }

}
