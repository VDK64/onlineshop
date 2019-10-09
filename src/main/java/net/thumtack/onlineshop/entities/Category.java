package net.thumtack.onlineshop.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(exclude = "products")
@NoArgsConstructor
@Data
@Entity
@SequenceGenerator(initialValue = 7, allocationSize = 1000, name = "seqC")
@Table(name = "category")
public class Category {
    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqC")
    private Integer id;
    @NotNull
    @Column(unique = true)
    private String name;
    private Integer parentId;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "categories")
    private List<Product> products = new ArrayList<>();

    public Category(String name, Integer parentId) {
        this.name = name;
        this.parentId = parentId;
    }
}
