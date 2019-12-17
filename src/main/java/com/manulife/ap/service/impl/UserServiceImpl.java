package com.manulife.ap.service.impl;

import com.manulife.ap.entity.UserEntity;
import com.manulife.ap.model.User;
import com.manulife.ap.repository.UserRepository;
import com.manulife.ap.service.UserService;
import com.manulife.ap.util.EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> addUser(List<User> users) {
        List<UserEntity> userEntities = EntityConverter.convertUsersToEntities(users);
        userEntities = userRepository.saveAll(userEntities);
        return EntityConverter.convertEntitiesToUsers(userEntities);
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> updateUser(List<User> users) {
        List<UserEntity> userEntities = EntityConverter.convertUsersToEntities(users);
        userEntities = userRepository.saveAll(userEntities);
        return EntityConverter.convertEntitiesToUsers(userEntities);
    }

    @Override
    public User findUserById(String id) {
        Optional<UserEntity> userEntity;
        User user;
        try {
            userEntity = userRepository.findById(id);
            user = EntityConverter.convertEntityToUser(userEntity.get());
        } catch (RuntimeException e) {
            user = new User();
        }
        return user;
    }

}
