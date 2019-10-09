package net.thumtack.onlineshop.dto.Request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RequestChangePassClientDto {

    private String firstName;
    private String lastName;
    private String patronymic;
    private String email;
    private String address;
    private String phone;
    private String oldPassword;
    private String newPassword;
    @JsonIgnore
    private String login;

    public RequestChangePassClientDto(String firstName, String lastName, String patronymic, String email, String address, String phone, String oldPassword, String newPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}
