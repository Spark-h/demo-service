package com.manulife.ap.util;

import com.manulife.ap.entity.UserEntity;
import com.manulife.ap.model.User;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class EntityConverter {

    public static UserEntity convertUserToEntity(User user) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        return userEntity;
    }

    public static List<UserEntity> convertUsersToEntities(List<User> users) {
        List<UserEntity> userEntities = new ArrayList<>();
        for (User user : users) {
            userEntities.add(convertUserToEntity(user));
        }
        return userEntities;
    }

    public static User convertEntityToUser(UserEntity userEntity) {
        User user = new User();
        BeanUtils.copyProperties(userEntity, user);
        return user;
    }

    public static List<User> convertEntitiesToUsers(List<UserEntity> userEntities) {
        List<User> users = new ArrayList<>();
        for (UserEntity userEntity : userEntities) {
            users.add(convertEntityToUser(userEntity));
        }
        return users;
    }

}
