package com.tmall.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.tmall.dao.UserDOMapper;
import com.tmall.dao.UserPasswordDOMapper;
import com.tmall.dataobject.UserDO;
import com.tmall.dataobject.UserPasswordDO;
import com.tmall.error.BusinessException;
import com.tmall.error.EmBusinessError;
import com.tmall.model.UserModel;
import com.tmall.service.UserService;
import com.tmall.validator.ValidationResult;
import com.tmall.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDOMapper userDOMapper;
    @Autowired
    private UserPasswordDOMapper userPasswordDOMapper;
    @Autowired
    private ValidatorImpl validator;
    @Override
    public void register(UserModel userModel) throws BusinessException {
        if(userModel==null){
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
            }
        ValidationResult result=validator.validate(userModel);
        UserDO userDO1=userDOMapper.selectByTelephone(userModel.getTelephone());
        if(userDO1!=null){
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST,"该手机号已经被注册");
        }
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }
        UserDO userDO=new UserDO();
        userDO=convertFromDataObject(userModel);
        userDOMapper.insertSelective(userDO);
        userModel.setId(userDO.getId());
        UserPasswordDO userPasswordDO= new UserPasswordDO();
        userPasswordDO=convertPasswordFromDateObject(userModel);
        userPasswordDOMapper.insertSelective(userPasswordDO);
    }

    @Override
    public UserModel login(String telephone, String password) throws BusinessException {
        UserDO userDO=userDOMapper.selectByTelephone(telephone);
        if(userDO==null){
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        UserPasswordDO userPasswordDO=userPasswordDOMapper.selectByUserId(userDO.getId());
        UserModel userModel=convertModelFromDataObject(userDO,userPasswordDO);
        if(!StringUtils.equals(password,userModel.getPassword())){
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        return userModel;
    }

    private UserModel convertModelFromDataObject(UserDO userDO,UserPasswordDO userPasswordDO){
        if (userDO==null||userPasswordDO==null){
            return null;
        }
        UserModel userModel=new UserModel();
        userModel.setName(userDO.getName());
        userModel.setTelephone(userDO.getTelephone());
        userModel.setPassword(userPasswordDO.getPassword());
        return userModel;
    }
    private UserDO convertFromDataObject(UserModel userModel){
        if(userModel==null){
            return null;
        }
        UserDO userDO=new UserDO();
        BeanUtils.copyProperties(userModel,userDO);
        return userDO;
    }
    private UserPasswordDO convertPasswordFromDateObject(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserPasswordDO userPasswordDO = new UserPasswordDO();
        BeanUtils.copyProperties(userModel, userPasswordDO);
        userPasswordDO.setUserId(userModel.getId());
        return userPasswordDO;
    }
}
