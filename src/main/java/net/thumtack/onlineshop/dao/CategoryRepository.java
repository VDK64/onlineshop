package net.thumtack.onlineshop.dao;


import net.thumtack.onlineshop.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {
    Boolean existsByName(String name);
    void deleteByParentId(Integer parentId);
    Iterable<Category> findByParentId(Integer parentId);
    Page<Category> findAll(Pageable pageable);
    Iterable<Category> findByParentId(Integer parentId, Sort name);
    List<Category> findByParentId(Integer parentId, Pageable page);

}
