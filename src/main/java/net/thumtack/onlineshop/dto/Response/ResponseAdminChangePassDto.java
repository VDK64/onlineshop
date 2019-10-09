package net.thumtack.onlineshop.dto.Response;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.thumtack.onlineshop.entities.Administrator;

@NoArgsConstructor
@Data
public class ResponseAdminChangePassDto {
    private String firstName;
    private String lastName;
    private String patronymic;
    private String position;
    private String oldPassword;
    private String newPassword;

    public ResponseAdminChangePassDto(Administrator admin){
        this.firstName = admin.getFirstName();
        this.lastName = admin.getLastName();
        this.patronymic = admin.getPatronymic();
        this.position = admin.getPosition();
        this.newPassword = admin.getPassword();
    }
}
