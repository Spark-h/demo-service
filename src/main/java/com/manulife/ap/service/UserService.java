package com.manulife.ap.service;

import com.manulife.ap.model.User;

import java.util.List;

public interface UserService {

    List<User> addUser(List<User> users);

    void deleteUser(String id);

    List<User> updateUser(List<User> users);

    User findUserById(String id);
}
