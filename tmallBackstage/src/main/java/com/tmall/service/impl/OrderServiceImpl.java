package com.tmall.service.impl;

import com.tmall.dao.OrderDOMapper;
import com.tmall.dataobject.OrderDO;
import com.tmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDOMapper orderDOMapper;
    @Override
    public void createOrder(OrderDO orderDO) {
        orderDOMapper.insert(orderDO);
    }
}
