package net.thumtack.onlineshop.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "cookieData")
public class CookieData {
    @Column(nullable = false)
    private String status;
    @Id
    @Column(unique = true)
    private String token;
    @Column(nullable = false, unique = true)
    private String login;

}
