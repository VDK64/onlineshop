package net.thumtack.onlineshop.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
@SequenceGenerator(initialValue = 6, allocationSize = 1000, name = "seqCart")
@Table(name = "cart")
public class Cart {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqCart")
    private Integer id;
    private Integer count;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @ToString.Exclude
    @JoinColumn(name = "client_id")
    private Client clientCart;
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "product_id")
    private Product productCart;

    public Cart(Client clientCart, Product productCart, Integer count) {
        this.clientCart = clientCart;
        this.productCart = productCart;
        this.count = count;
    }
}
