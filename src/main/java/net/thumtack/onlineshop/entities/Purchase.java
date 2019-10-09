package net.thumtack.onlineshop.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
@Entity
@SequenceGenerator(name="seqPurchase", initialValue = 9, allocationSize=1000)
@Table(name = "purchase")
public class Purchase {
    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqPurchase")
    private Integer id;
    @NotNull
    private Integer count;
    @NotNull
    private Integer totalPrice;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "client_id")
    private Client client;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "product_id")
    private Product product;

    public Purchase(Integer count, Integer totalPrice, Client client, Product product) {
        this.count = count;
        this.totalPrice = totalPrice;
        this.client = client;
        this.product = product;
    }
}
