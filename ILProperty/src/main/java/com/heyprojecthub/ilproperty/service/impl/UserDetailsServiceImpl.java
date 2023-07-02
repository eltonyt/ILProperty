package com.heyprojecthub.ilproperty.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.heyprojecthub.ilproperty.domain.LoginUser;
import com.heyprojecthub.ilproperty.domain.ResponseResult;
import com.heyprojecthub.ilproperty.domain.User;
import com.heyprojecthub.ilproperty.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUserName, username);
        User user = userMapper.selectOne(lambdaQueryWrapper);
        if (Objects.isNull(user))
            throw new RuntimeException("Wrong Username");
        List<String> permissions = new ArrayList<>(Arrays.asList("test", "admin"));
        return new LoginUser(user, permissions);
    }
}
