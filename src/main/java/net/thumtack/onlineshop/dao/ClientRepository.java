package net.thumtack.onlineshop.dao;

import net.thumtack.onlineshop.entities.Client;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends UserBaseRepository<Client> {
    Optional<Client> findByLogin(String login);
}
