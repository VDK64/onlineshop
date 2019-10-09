package net.thumtack.onlineshop.entities.abstractClass;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@MappedSuperclass
@Data
public abstract class User {
    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqUser")
    private Integer id;
    @NotNull
    private String firstName;
    private String patronymic;
    @NotNull
    private String lastName;
    @NotNull
    @Column(unique = true)
    private String login;
    @NotNull
    private String password;

    public User(String firstName, String patronymic, String lastName, String login, String password) {
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
    }

    public User(String firstName, String lastName, String login, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
    }
}
