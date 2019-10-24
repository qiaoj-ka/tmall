package com.tmall.control;

import com.alibaba.druid.util.StringUtils;
import com.tmall.error.BusinessException;
import com.tmall.error.EmBusinessError;
import com.tmall.model.UserModel;
import com.tmall.response.CommenReturnType;
import com.tmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@RequestMapping("/user")
@RestController
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class UserController extends BaseController{
    @Autowired
    HttpServletRequest httpServletRequest;
    @Autowired
    private UserService userService;
    //用户获取otp短信接口
    @RequestMapping(value = "/getotp",method = {RequestMethod.POST},
            consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommenReturnType getOtp(@RequestParam(name="telphone")String telphone) throws BusinessException {
        //需要按照一定的规则生成OTP验证码
        Random random=new Random();
        int randomInt=random.nextInt(99999);
        randomInt+=10000;
        String otpCode=String.valueOf(randomInt);
        //将OTP验证码同对应用户手机号相关联,用httpsession的方式进行相关联
        httpServletRequest.getSession().setAttribute(telphone,otpCode);
        //将OPT验证码通过短信通道发送给用户
        System.out.println("telphone="+telphone+"&otpCode="+otpCode);
        return CommenReturnType.creat(null);
    }
    //用户注册接口
    @RequestMapping(value = "/register",method = {RequestMethod.POST},
            consumes = {"application/x-www-form-urlencoded"})
    @ResponseBody
    public CommenReturnType register(@RequestParam(name="telphone")String telphone,
                                     @RequestParam(name="otpCode")String otpCode,
                                     @RequestParam(name="name")String name,
                                     @RequestParam(name = "password")String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        //验证手机号对应的otpCode相符合
        String inSessionotpCode= (String)httpServletRequest.getSession().getAttribute(telphone);

        if(!com.alibaba.druid.util.StringUtils.equals(otpCode,inSessionotpCode)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"短信验证码不合法");

        }
        UserModel userModel=new UserModel();
        userModel.setName(name);
        userModel.setTelephone(telphone);
        userModel.setPassword(this.EncodeByMD5(password));
        userService.register(userModel);
        return CommenReturnType.creat(null);
    }
    //用户登陆接口
    @RequestMapping(value = "/login",method = {RequestMethod.POST},
            consumes = {"application/x-www-form-urlencoded"})
    //获取post里面请求的内容 异步请求
    @ResponseBody
    public CommenReturnType login(@RequestParam(name="telphone")String telphone,
                                  @RequestParam(name="password")String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        if (org.apache.commons.lang3.StringUtils.isEmpty(telphone)||
                StringUtils.isEmpty(password)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);

        }
        //用户登陆服务，用来校验用户登陆是否合法
        UserModel userModel=userService.login(telphone,this.EncodeByMD5(password));
        //将登陆凭证加入到用户登陆成功的Session内
        this.httpServletRequest.getSession().setAttribute("IS_LOGIN",true);
        this.httpServletRequest.getSession().setAttribute("LOGIN_USER",userModel);

        return CommenReturnType.creat(null);
    }
    public String EncodeByMD5(String str) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        //确定计算方法
        MessageDigest md5=MessageDigest.getInstance("MD5");
        BASE64Encoder base64en=new BASE64Encoder();
        //加密字符串
        String newstr=base64en.encode(md5.digest(str.getBytes("utf-8")));
        return newstr;
    }
}
