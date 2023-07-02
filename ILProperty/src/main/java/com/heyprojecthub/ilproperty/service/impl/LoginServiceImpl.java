package com.heyprojecthub.ilproperty.service.impl;

import com.heyprojecthub.ilproperty.domain.LoginUser;
import com.heyprojecthub.ilproperty.domain.ResponseResult;
import com.heyprojecthub.ilproperty.domain.User;
import com.heyprojecthub.ilproperty.service.LoginService;
import com.heyprojecthub.ilproperty.utils.JwtUtil;
import com.heyprojecthub.ilproperty.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(authenticate))
            throw new RuntimeException("Failed Log in");
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        Long userId = loginUser.getUser().getId();
        String jwt = JwtUtil.createJWT(userId.toString());
        // STORE JWT TOKEN TO REDIS
        Map<String, String> map = new HashMap<>();
        map.put("token", jwt);
        redisCache.setCacheObject("login:" + userId, loginUser);
        return new ResponseResult(200, "Successfully Logged in!", map);
    }

    @Override
    public ResponseResult logOut() {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();
        redisCache.deleteObject("login:" + userId);
        return new ResponseResult(200, "User logged out.");
    }
}
