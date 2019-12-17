package com.manulife.ap.api;

import java.util.List;
import com.manulife.ap.model.User;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import java.util.List;

@Controller
public class UserApiController implements UserApi {

    private final UserApiDelegate delegate;

    @org.springframework.beans.factory.annotation.Autowired
    public UserApiController(UserApiDelegate delegate) {
        this.delegate = delegate;
    }
    public ResponseEntity<List<User>> addUser(@ApiParam(value = "user information" ,required=true )  @Valid @RequestBody List<User> users) {
        return delegate.addUser(users);
    }

    public ResponseEntity<Void> deleteUser(@ApiParam(value = "delete user",required=true) @PathVariable("id") String id) {
        return delegate.deleteUser(id);
    }

    public ResponseEntity<User> getUserById(@ApiParam(value = "user to return",required=true) @PathVariable("id") String id) {
        return delegate.getUserById(id);
    }

    public ResponseEntity<List<User>> updateUser(@ApiParam(value = "update user" ,required=true )  @Valid @RequestBody List<User> users) {
        return delegate.updateUser(users);
    }

}
