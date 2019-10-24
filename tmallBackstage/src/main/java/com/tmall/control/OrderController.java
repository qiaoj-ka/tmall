package com.tmall.control;

import com.tmall.dataobject.OrderDO;
import com.tmall.error.BusinessException;
import com.tmall.error.EmBusinessError;
import com.tmall.model.OrderModel;
import com.tmall.model.UserModel;
import com.tmall.response.CommenReturnType;
import com.tmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import static javax.print.attribute.standard.MediaPrintableArea.MM;

@RequestMapping("/order")
@RestController
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private HttpServletRequest httpServletRequest;
    //判断是否登陆
    @RequestMapping(value = "/judge",method = {RequestMethod.POST},
            consumes = {"application/x-www-form-urlencoded"})
    @ResponseBody
    public CommenReturnType judge() throws BusinessException {
        Boolean isLogin= (Boolean) httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if(isLogin==null||!isLogin.booleanValue()){
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN,"用户还未登陆,不能下单");
        }
        return CommenReturnType.creat(null);
    }
    //封装下单请求
    @RequestMapping(value = "/createorder",method = {RequestMethod.POST},
            consumes = {"application/x-www-form-urlencoded"})
    @ResponseBody
    public CommenReturnType createOrderToCart(@RequestParam(name = "itemId")Integer itemId,
                                        @RequestParam(name = "amount")Integer amount,
                                        @RequestParam(name = "sumPrice") BigDecimal sumPrice
                                        ) throws BusinessException {
        Boolean isLogin= (Boolean) httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if(isLogin==null||!isLogin.booleanValue()){
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN,"用户还未登陆,不能下单");
        }
        //获取用户的登陆信息
        UserModel userModel=(UserModel)httpServletRequest.getSession().getAttribute("LOGIN_USER");
        OrderDO orderDO=new OrderDO();
        orderDO.setAmount(amount);
        orderDO.setItemId(itemId);
        Format format = new SimpleDateFormat("yyyyMMddHHmmss");
        String id=format.format(new Date());
        StringBuffer stringBuffer=new StringBuffer(id);
        String tail=String.valueOf((int)(100000+Math.random()*100000));
        StringBuffer stringBuffer1=new StringBuffer(tail);
        stringBuffer.append(stringBuffer1);
        orderDO.setId(stringBuffer.toString());
        orderDO.setStatus(0);
        orderDO.setUserId(userModel.getId());
        orderService.createOrder(orderDO);
        return CommenReturnType.creat(null);
    }
}
