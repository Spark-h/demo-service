package com.manulife.ap.service;

import com.manulife.ap.entity.UserEntity;
import com.manulife.ap.model.User;
import com.manulife.ap.repository.UserRepository;
import com.manulife.ap.service.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserServiceImpl.class)
public class UserServiceImplTest {

    @Autowired
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @Test
    public void testAddUser(){
        List<User> userList = buildUserList();
        List<UserEntity> userEntities = buildUserEntities();
        Mockito.when(userRepository.saveAll(Mockito.any())).thenReturn(userEntities);
        List<User> returnUsers = userService.addUser(userList);
        Assert.assertNotNull(returnUsers);
        Assert.assertEquals(userList.size(), returnUsers.size());
        for (int i=0;i<userList.size();i++){
            Assert.assertEquals(userList.get(i).getId(), returnUsers.get(i).getId());
            Assert.assertEquals(userList.get(i).getEmail(), returnUsers.get(i).getEmail());
            Assert.assertEquals(userList.get(i).getName(), returnUsers.get(i).getName());
        }
    }

    @Test
    public void testDeleteUser(){
        Mockito.doNothing().when(userRepository).deleteById(Mockito.anyString());
        userService.deleteUser("testId");
        Mockito.verify(userRepository).deleteById("testId");
    }

    @Test
    public void testUpdateUser(){
        List<User> userList = buildUserList();
        List<UserEntity> userEntities = buildUserEntities();
        Mockito.when(userRepository.saveAll(Mockito.any())).thenReturn(userEntities);
        List<User> returnUsers = userService.updateUser(userList);
        Assert.assertNotNull(returnUsers);
        Assert.assertEquals(userList.size(), returnUsers.size());
        for (int i=0;i<userList.size();i++){
            Assert.assertEquals(userList.get(i).getId(), returnUsers.get(i).getId());
            Assert.assertEquals(userList.get(i).getEmail(), returnUsers.get(i).getEmail());
            Assert.assertEquals(userList.get(i).getName(), returnUsers.get(i).getName());
        }
    }

    @Test
    public void givenCorrectIdWhenFindUserByIdReturnUser(){
        User user = buildUser();
        UserEntity userEntity = buildUserEntity();
        Mockito.when(userRepository.getOne(Mockito.anyString())).thenReturn(userEntity);
        User returnUser = userService.findUserById("testId");
        Assert.assertEquals(user.getId(), returnUser.getId());
        Assert.assertEquals(user.getName(), returnUser.getName());
        Assert.assertEquals(user.getEmail(), returnUser.getEmail());
    }

    @Test
    public void givenNonexistentIdWhenFindUserByIdReturnNullUser(){
        Mockito.doThrow(new RuntimeException()).when(userRepository).getOne(Mockito.anyString());
        User returnUser = userService.findUserById("testId");
        Assert.assertNull(returnUser.getEmail());
        Assert.assertNull(returnUser.getName());
        Assert.assertNull(returnUser.getId());
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

    private List<UserEntity> buildUserEntities(){
        List<UserEntity> userEntities = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            UserEntity user = new UserEntity();
            user.setId("testId");
            user.setEmail("testEmail");
            user.setName("testName");
            userEntities.add(user);
        }
        return userEntities;
    }

    private User buildUser(){
        return buildUserList().get(0);
    }

    private UserEntity buildUserEntity(){
        return buildUserEntities().get(0);
    }

}
