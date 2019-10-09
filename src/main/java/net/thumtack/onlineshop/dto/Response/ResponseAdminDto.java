package net.thumtack.onlineshop.dto.Response;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.thumtack.onlineshop.entities.Administrator;

@NoArgsConstructor
@Data
public class ResponseAdminDto {
    private Integer id;
    private String firstName;
    private String patronymic;
    private String lastName;
    private String position;

    public ResponseAdminDto(Administrator admin){
        this.id = admin.getId();
        this.firstName = admin.getFirstName();
        this.patronymic = admin.getPatronymic();
        this.lastName = admin.getLastName();
        this.position = admin.getPosition();
    }

    @Override
    public String toString() {
        return "{" + '\n' +
                "  id: " + id +", " + '\n' +
                "  firstName: " + firstName + ", " + '\n' +
                "  patronymic: " + patronymic + ", " + '\n' +
                "  lastName: " + lastName + ", " + '\n' +
                "  position: " + position + ", " + '\n' +
                '}';
    }
}
