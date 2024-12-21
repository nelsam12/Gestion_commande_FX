package com.projet.services;

import java.util.List;

import com.projet.entities.User;

public interface IUserService {
    User getUserByLoginAndPassword(String login, String password);
    List<User> findAll();
    User getUserByLogin(String login);
    void save(User user);
}
