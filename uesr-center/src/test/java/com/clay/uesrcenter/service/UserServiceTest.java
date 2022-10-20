package com.clay.uesrcenter.service;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.clay.uesrcenter.model.domain.User;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**s
 * 用户服务测试
 * @author clay
 */
@SpringBootTest
class UserServiceTest  {
    @Resource
    private UserService userService;
    @Test
    public void testAddUser(){
        User user = new User();
        user.setUsername("dogYupi");
        user.setUserAccount("123");
        user.setAvatarUrl("https://static.shihuocdn.cn/admin/files/20201215/0778d5ee46bde1067a5cf7c449ee7ef5.png");
        user.setGender(0);
        user.setUserpassword("xxx");
        user.setPhone("123");
        user.setEmail("456");


        boolean save = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertEquals(true,save);
    }

    @Test
    void userRegister() {
        String userAccount = "yupi";

        String userPassword = "";

        String checkPassword = "123456";

        String planetCode ="1";

        long result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);

        Assertions.assertEquals(-1, result);



        userAccount = "yu";

        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);

        Assertions.assertEquals(-1, result);



        userAccount = "yupi";

        userPassword = "123456";

        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);

        Assertions.assertEquals(-1, result);

        userAccount = "yu pi";

        userPassword = "12345678";

        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);

        Assertions.assertEquals(-1, result);



        checkPassword = "123456789";

        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);

        Assertions.assertEquals(-1, result);



        userAccount = "dogyupi";

        checkPassword = "12345678";

        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);

        Assertions.assertEquals(-1, result);



        userAccount = "yupi";

        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);

        Assertions.assertTrue(result > 0);
    }

    @Test
    public void testSearchUserByTags(){
        List<String> strings = Arrays.asList("java", "python");
        List<User> userList = userService.searchUsersByTags(strings);
        Assert.assertNotNull(userList);
    }




}