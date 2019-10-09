package net.thumtack.onlineshop.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.thumtack.onlineshop.entities.Cart;
import net.thumtack.onlineshop.entities.Client;
import net.thumtack.onlineshop.entities.Purchase;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseClientsDetailingDto {
    private Integer id;
    private String firstName;
    private String patronymic;
    private String lastName;
    private String email;
    private String address;
    private String phone;
    private String login;
    private Integer deposit;
    private List<ResponseProductBuyDto> carts;
    private List<ResponseClientPurchaseDto> purchases;

    public ResponseClientsDetailingDto(Integer id, String firstName, String patronymic, String lastName,
                                       String email, String address, String phone, String login, Integer deposit) {
        this.id = id;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.login = login;
        this.deposit = deposit;
    }

    public ResponseClientsDetailingDto(Client client){
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.patronymic = client.getPatronymic();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.address = client.getAddress();
        this.phone = client.getPhone();
        this.login = client.getLogin();
        this.deposit = client.getDeposit();
        carts = new ArrayList<>();
        purchases = new ArrayList<>();
        for (Cart cart: client.getCarts()) {
            this.carts.add(new ResponseProductBuyDto(cart.getId(), cart.getProductCart().getName(),
                    cart.getProductCart().getPrice(), cart.getCount()));
        }
        for (Purchase purchase:client.getPurchases()){
            this.purchases.add(new ResponseClientPurchaseDto(purchase.getId(), purchase.getCount(),
                    purchase.getTotalPrice(), purchase.getProduct().getName()));
        }
    }


}
