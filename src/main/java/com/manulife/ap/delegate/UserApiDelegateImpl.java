package com.manulife.ap.delegate;

import com.manulife.ap.api.UserApiDelegate;
import com.manulife.ap.exception.ParameterException;
import com.manulife.ap.model.User;
import com.manulife.ap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
public class UserApiDelegateImpl implements UserApiDelegate {

    @Autowired
    UserService userService;

    @Override
    public ResponseEntity<List<User>> addUser(List<User> users) {
        validateUser(users, "add");
        return ResponseEntity.ok(userService.addUser(users));
    }

    @Override
    public ResponseEntity<Void> deleteUser(String id) {
        User user = userService.findUserById(id);
        if (!StringUtils.isEmpty(user)) {
            userService.deleteUser(id);
        }
        return ResponseEntity.ok(null);
    }

    @Override
    public ResponseEntity<User> getUserById(String id) {
        User user = userService.findUserById(id);
        if (StringUtils.isEmpty(user.getId())) {
            return new ResponseEntity<User>(user, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<List<User>> updateUser(List<User> users) {
        validateUser(users, "update");
        return ResponseEntity.ok(userService.updateUser(users));
    }

    private void validateUser(List<User> users, String type) {
        for (User user : users) {
            if (StringUtils.isEmpty(user.getId())) {
                throw new ParameterException("invalid user id");
            }
            if (StringUtils.isEmpty(user.getName())) {
                throw new ParameterException("invalid user name");
            }
            if (StringUtils.isEmpty(user.getEmail())) {
                throw new ParameterException("invalid user email");
            }

            User validateUser = userService.findUserById(user.getId());
            if ("add".equals(type)) {
                if (!StringUtils.isEmpty(validateUser.getId())) {
                    throw new ParameterException("duplicate user id");
                }
            }
            if ("update".equals(type)) {
                if (StringUtils.isEmpty(validateUser.getId())) {
                    throw new ParameterException("invalid user id");
                }
            }
        }
    }
}
