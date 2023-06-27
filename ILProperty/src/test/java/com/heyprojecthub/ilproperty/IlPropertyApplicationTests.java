package com.heyprojecthub.ilproperty;

import com.heyprojecthub.ilproperty.domain.User;
import com.heyprojecthub.ilproperty.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class IlPropertyApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testUserMapper() {
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }
}
