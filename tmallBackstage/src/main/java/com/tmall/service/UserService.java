package com.tmall.service;

import com.tmall.error.BusinessException;
import com.tmall.model.UserModel;

public interface UserService {
    void register(UserModel userModel) throws BusinessException;
    UserModel login(String telephone, String password) throws BusinessException;
}
