package ru.shop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.shop.entity.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role,Integer> {
}
