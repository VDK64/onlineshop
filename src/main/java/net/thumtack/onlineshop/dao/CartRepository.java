package net.thumtack.onlineshop.dao;

import net.thumtack.onlineshop.entities.Cart;
import net.thumtack.onlineshop.entities.Client;
import net.thumtack.onlineshop.entities.Product;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends PagingAndSortingRepository<Cart, Integer> {
    Optional<Cart> findByClientCartAndProductCart(Client clientCart, Product productCart);
}
