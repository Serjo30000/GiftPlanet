package ru.shop.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.shop.entity.Relation;

@Repository
public interface RelationRepository extends CrudRepository<Relation,Integer> {
    @Modifying
    @Query("UPDATE users_roles SET user_id=:userId, role_id=:roleId WHERE user_id=:userId")
    void updateRelation(@Param("userId")int userId, @Param("roleId")int roleId);
}
