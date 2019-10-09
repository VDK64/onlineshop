package net.thumtack.onlineshop.dao;

import net.thumtack.onlineshop.entities.CookieData;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface CookieDataRepository extends PagingAndSortingRepository<CookieData, String> {
    Optional<CookieData> findByLogin(String login);
    void deleteByLogin(String login);
}
