package net.thumtack.onlineshop.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(exclude= {"carts", "purchases"} )
@NoArgsConstructor
@Data
@Entity
@SequenceGenerator(initialValue = 6, allocationSize = 1000, name = "seqP")
@Table(name = "product")
public class Product {
    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqP")
    private Integer id;
    @NotNull
    private Integer price;
    @NotNull
    private String name;
    @NotNull
    private Integer count;
    @ToString.Exclude
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(name="product_category",
            joinColumns = @JoinColumn(name="product_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name="category_id", referencedColumnName="id")
    )
    private List<Category> categories = new ArrayList<>();
    @ToString.Exclude
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "productCart", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Cart> carts = new HashSet<>();
    @ToString.Exclude
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Purchase> purchases = new HashSet<>();

    public Product(Integer price, String name, Integer count) {
        this.price = price;
        this.name = name;
        this.count = count;
    }
}
