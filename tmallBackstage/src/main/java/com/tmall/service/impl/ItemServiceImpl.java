package com.tmall.service.impl;

import com.tmall.dao.ItemDOMapper;
import com.tmall.dao.ItemInfoDOMapper;
import com.tmall.dataobject.ItemDO;
import com.tmall.dataobject.ItemInfoDO;
import com.tmall.model.ItemModel;
import com.tmall.service.ItemService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemDOMapper itemDOMapper;
    @Autowired
    private ItemInfoDOMapper itemInfoDOMapper;

    @Override
    public ItemModel getDetailInfo(Integer id) {
        ItemDO itemDO=itemDOMapper.selectByPrimaryKey(id);
        ItemInfoDO itemInfoDO=itemInfoDOMapper.selectByItemId(id);
        ItemModel itemModel=convertFromDo(itemDO,itemInfoDO);
        return itemModel;
    }
    public ItemModel convertFromDo(ItemDO itemDO,ItemInfoDO itemInfoDO){
        ItemModel itemModel=new ItemModel();
        BeanUtils.copyProperties(itemDO,itemModel);
        itemModel.setImage1(itemInfoDO.getImage1());
        itemModel.setImage2(itemInfoDO.getImage2());
        itemModel.setImage3(itemInfoDO.getImage3());
        itemModel.setImage4(itemInfoDO.getImage4());
        itemModel.setState(itemInfoDO.getState());
        itemModel.setOriginPrice(itemInfoDO.getOriginPrice());
        return itemModel;
    }
}
