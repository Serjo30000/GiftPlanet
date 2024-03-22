package ru.shop.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.shop.entity.Product;
import ru.shop.entity.Role;
import ru.shop.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {
    @Query("SELECT * FROM users WHERE username = :userName")
    User findByUsername(@Param("userName")String username);
    @Query("SELECT * FROM users WHERE users.username!=:username")
    List<User> findUserWithNotMe(@Param("username")String username);
    @Query("SELECT roles.id,roles.name FROM roles JOIN users_roles ON roles.id = users_roles.role_id JOIN users ON" +
            " users.id = users_roles.user_id WHERE users.id=:id")
    List<Role> findUserWithRole(@Param("id")int userId);
    @Query("SELECT * FROM users ORDER BY id ASC")
    List<User> findUserOrderByIdAsc();

    @Query("SELECT * FROM users ORDER BY id DESC")
    List<User> findUserOrderByIdDesc();
    @Query("SELECT * FROM users ORDER BY id ASC")
    List<User> findUserOrderByLoginAsc();

    @Query("SELECT * FROM users ORDER BY id DESC")
    List<User> findUserOrderByLoginDesc();
    @Modifying
    @Query("UPDATE users SET username=:username, password=:password WHERE id=:id")
    void updateUser(@Param("id")int id,@Param("username")String username,@Param("password")String password);
}
