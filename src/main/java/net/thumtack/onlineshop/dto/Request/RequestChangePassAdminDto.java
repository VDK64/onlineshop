package net.thumtack.onlineshop.dto.Request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RequestChangePassAdminDto {

    private String firstName;
    private String lastName;
    private String patronymic;
    private String position;
    private String oldPassword;
    private String newPassword;
    @JsonIgnore
    private String login;

    public RequestChangePassAdminDto(String firstName, String lastName, String patronymic, String position, String oldPassword, String newPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.position = position;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public RequestChangePassAdminDto(String firstName, String lastName, String position, String oldPassword, String newPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}
