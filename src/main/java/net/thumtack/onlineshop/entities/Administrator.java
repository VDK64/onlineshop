package net.thumtack.onlineshop.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.thumtack.onlineshop.entities.abstractClass.User;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@SequenceGenerator(sequenceName = "admin_seq", name="seqUser", initialValue = 2, allocationSize=1000)
@Table(name = "administrator")
public class Administrator extends User {
    @NotNull
    private String position;

    public Administrator(String firstName, String patronymic, String lastName, String position, String login, String password) {
        super(firstName, patronymic, lastName, login, password);
        this.position = position;
    }

    public Administrator(String firstName, String lastName, String position, String login, String password) {
        super(firstName, lastName, login, password);
        this.position = position;
    }
}
