package com.heyprojecthub.ilproperty.service;

import com.heyprojecthub.ilproperty.domain.ResponseResult;
import com.heyprojecthub.ilproperty.domain.User;

public interface LoginService {
    ResponseResult login(User user);
}
