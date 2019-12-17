package com.manulife.ap.delegate;

import com.manulife.ap.api.UserApiDelegate;
import com.manulife.ap.exception.ParameterException;
import com.manulife.ap.model.User;
import com.manulife.ap.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApiDelegateImpl.class)
public class UserApiDelegateImplTest {

    @Autowired
    UserApiDelegate userApiDelegate;

    @MockBean
    UserService userService;

    @Test
    public void givenHasEmptyIdUsersWhenAddUserThrowParameterException() {
        List<User> userList = buildEmptyIdUserList();
        try {
            userApiDelegate.addUser(userList);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof ParameterException);
            Assert.assertEquals("invalid user id", e.getMessage());
        }
    }

    @Test
    public void givenHasEmptyNameUsersWhenAddUserThrowParameterException() {
        List<User> userList = buildEmptyNameUserList();
        try {
            userApiDelegate.addUser(userList);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof ParameterException);
            Assert.assertEquals("invalid user name", e.getMessage());
        }
    }

    @Test
    public void givenHasEmptyEmailUsersWhenAddUserThrowParameterException() {
        List<User> userList = buildEmptyEmailUserList();
        try {
            userApiDelegate.addUser(userList);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof ParameterException);
            Assert.assertEquals("invalid user email", e.getMessage());
        }
    }

    @Test
    public void givenDuplicateUserWhenAddUserThrowParameterException() {
        List<User> userList = buildUserList();
        Mockito.when(userService.findUserById(Mockito.anyString())).thenReturn(userList.get(0));
        try {
            userApiDelegate.addUser(userList);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof ParameterException);
            Assert.assertEquals("duplicate user id", e.getMessage());
        }
    }

    @Test
    public void givenCorrectUsersWhenAddUserReturnUsers() {
        List<User> userList = buildUserList();
        Mockito.when(userService.addUser(Mockito.any())).thenReturn(userList);
        Mockito.when(userService.findUserById(Mockito.anyString())).thenReturn(new User());
        ResponseEntity<List<User>> responseEntity = userApiDelegate.addUser(userList);
        Assert.assertNotNull(responseEntity);
        Assert.assertTrue(responseEntity.getBody() instanceof List);
        Assert.assertEquals(9, responseEntity.getBody().size());
    }

    @Test
    public void testDeleteUser() {
        Mockito.when(userService.findUserById(Mockito.anyString())).thenReturn(buildUser());
        Mockito.doNothing().when(userService).deleteUser(Mockito.anyString());
        ResponseEntity<Void> responseEntity = userApiDelegate.deleteUser("testId");
        Mockito.verify(userService).deleteUser("testId");
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNull(responseEntity.getBody());
    }

    @Test
    public void givenNonexistentIdWhenGetUserByIdReturnNotFound() {
        Mockito.when(userService.findUserById("testId")).thenReturn(new User());
        ResponseEntity<User> responseEntity = userApiDelegate.getUserById("testId");
        Assert.assertNull(responseEntity.getBody().getId());
        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void givenCorrectIdWhenGetUserByIdReturnUser() {
        User user = buildUser();
        Mockito.when(userService.findUserById("testId")).thenReturn(user);
        ResponseEntity<User> responseEntity = userApiDelegate.getUserById("testId");
        Assert.assertEquals("testId", responseEntity.getBody().getId());
        Assert.assertEquals("testName", responseEntity.getBody().getName());
        Assert.assertEquals("testEmail", responseEntity.getBody().getEmail());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void givenNonexistentIdWhenUpdateUserThrowException() {
        List<User> userList = buildUserList();
        Mockito.when(userService.findUserById(Mockito.anyString())).thenReturn(new User());
        try {
            userApiDelegate.updateUser(userList);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof ParameterException);
            Assert.assertEquals("invalid user id", e.getMessage());
        }
    }

    @Test
    public void givenCorrectUserListWhenUpdateUserReturnUserList() {
        List<User> userList = buildUserList();
        Mockito.when(userService.findUserById(Mockito.anyString())).thenReturn(userList.get(0));
        Mockito.when(userService.updateUser(Mockito.any())).thenReturn(userList);
        ResponseEntity<List<User>> responseEntity = userApiDelegate.updateUser(userList);
        Assert.assertNotNull(responseEntity);
        Assert.assertTrue(responseEntity.getBody() instanceof List);
        Assert.assertEquals(9, responseEntity.getBody().size());
    }

    private User buildUser() {
        return buildUserList().get(0);
    }

    private List<User> buildUserList() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            User user = new User();
            user.setId("testId");
            user.setEmail("testEmail");
            user.setName("testName");
            users.add(user);
        }
        return users;
    }

    private List<User> buildEmptyIdUserList() {
        List<User> users = buildUserList();
        users.get(0).setId("");
        return users;
    }

    private List<User> buildEmptyNameUserList() {
        List<User> users = buildUserList();
        users.get(0).setName("");
        return users;
    }

    private List<User> buildEmptyEmailUserList() {
        List<User> users = buildUserList();
        users.get(0).setEmail("");
        return users;
    }
}
