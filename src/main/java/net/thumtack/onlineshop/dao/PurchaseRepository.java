package net.thumtack.onlineshop.dao;

import net.thumtack.onlineshop.entities.Client;
import net.thumtack.onlineshop.entities.Product;
import net.thumtack.onlineshop.entities.Purchase;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseRepository extends PagingAndSortingRepository<Purchase, Integer> {
    Optional<Purchase> findByClientAndProduct(Client client, Product product);
    List<Purchase> findById(Integer id, Pageable page);
}
