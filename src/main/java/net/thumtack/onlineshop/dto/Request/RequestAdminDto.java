package net.thumtack.onlineshop.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.thumtack.onlineshop.entities.Administrator;

@AllArgsConstructor
@Data
public class RequestAdminDto {
    private String firstName;
    private String patronymic;
    private String lastName;
    private String position;
    private String login;
    private String password;

    public Administrator buildAdmin(){
        return new Administrator(firstName, patronymic, lastName, position, login, password);
    }

}
