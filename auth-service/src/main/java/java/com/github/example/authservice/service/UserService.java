package java.com.github.example.authservice.service;

import java.com.github.example.authservice.entity.User;

import java.util.List;

public interface UserService {

    User create(User user);
    List findAll();
    String findbyName(String name);
    User update(Integer id, User user);
    User findUserById(Integer id);
}