package net.thumtack.onlineshop.dao;

import net.thumtack.onlineshop.entities.abstractClass.User;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface UserBaseRepository<T extends User> extends PagingAndSortingRepository<T, Integer> {   }
