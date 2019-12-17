package com.manulife.ap.api;

import java.util.List;
import com.manulife.ap.model.User;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * A delegate to be called by the {@link UserApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */

public interface UserApiDelegate {

    /**
     * @see UserApi#addUser
     */
    ResponseEntity<List<User>> addUser(List<User> users);

    /**
     * @see UserApi#deleteUser
     */
    ResponseEntity<Void> deleteUser(String id);

    /**
     * @see UserApi#getUserById
     */
    ResponseEntity<User> getUserById(String id);

    /**
     * @see UserApi#updateUser
     */
    ResponseEntity<List<User>> updateUser(List<User> users);

}
