package net.thumtack.onlineshop.dao;

import net.thumtack.onlineshop.entities.Administrator;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdministratorRepository extends UserBaseRepository<Administrator> {
    Optional<Administrator> findByLogin(String login);
}
