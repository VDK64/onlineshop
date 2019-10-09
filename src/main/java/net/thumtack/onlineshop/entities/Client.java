package net.thumtack.onlineshop.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.thumtack.onlineshop.entities.abstractClass.User;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = { "carts", "purchases" } )
@Data
@Entity
@SequenceGenerator(sequenceName = "client_seq", name="seqUser", initialValue = 6, allocationSize=1000)
@Table(name = "client")
public class Client extends User {
    @NotNull
    private String email;
    @NotNull
    private String address;
    @NotNull
    private String phone;
    private Integer deposit;
    @ToString.Exclude
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "clientCart", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Cart> carts = new HashSet<>();
    @ToString.Exclude
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "client", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Purchase> purchases = new HashSet<>();

    public Client(String firstName, String patronymic, String lastName, String email, String address,
                  String phone, String login, String password) {
        super(firstName, patronymic, lastName, login, password);
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.deposit = 0;
    }

    public Client(String firstName, String lastName, String email, String address, String phone, String login, String password) {
        super(firstName, lastName, login, password);
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.deposit = 0;
    }
}
