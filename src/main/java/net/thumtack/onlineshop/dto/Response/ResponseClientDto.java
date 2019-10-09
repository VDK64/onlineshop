package net.thumtack.onlineshop.dto.Response;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.thumtack.onlineshop.entities.Client;

@NoArgsConstructor
@Data
public class ResponseClientDto {

    private Integer id;
    private String firstName;
    private String patronymic;
    private String lastName;
    private String email;
    private String address;
    private String phone;
    private Integer deposit;

    public ResponseClientDto(Client client) {
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.patronymic = client.getPatronymic();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.address = client.getAddress();
        this.phone = client.getPhone();
        this.deposit = client.getDeposit();
    }

    @Override
    public String toString() {
        return "{" + '\n' +
                "  id: " + id + '\n' +
                "  firstName: " + firstName + ", " + '\n' +
                "  patronymic: " + patronymic + ", " + '\n' +
                "  lastName: " + lastName + ", " + '\n' +
                "  email: " + email + ", " + '\n' +
                "  address: " + address + ", " + '\n' +
                "  phone: " + phone + ", " + '\n' +
                "  deposit: " + deposit + ", " + '\n'+
                '}';
    }
}
