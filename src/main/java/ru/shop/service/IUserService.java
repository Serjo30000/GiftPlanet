package ru.shop.service;

import org.springframework.stereotype.Service;
import ru.shop.entity.User;

import java.util.List;

@Service
public interface IUserService {
    boolean updateUser(User user);
    List<User> sortUser(List<User>lstUs, String sortName, String sortBy);
}
