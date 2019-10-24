package com.tmall.control;

import com.tmall.control.viewobject.ItemVO;
import com.tmall.dao.ItemDOMapper;
import com.tmall.dataobject.ItemDO;
import com.tmall.model.ItemModel;
import com.tmall.model.UserModel;
import com.tmall.response.CommenReturnType;
import com.tmall.service.ItemService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/item")
@RestController
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class ItemController {
    @Autowired
    private ItemService itemService;
        @GetMapping("/getItem")
        public CommenReturnType getDetail(@RequestParam(name = "id")Integer id){
            ItemModel itemModel=itemService.getDetailInfo(id);
            ItemVO itemVO=convertFromModel(itemModel);
            return CommenReturnType.creat(itemVO);
        }

        public ItemVO convertFromModel(ItemModel itemModel){
            ItemVO itemVO=new ItemVO();
            BeanUtils.copyProperties(itemModel,itemVO);
            return itemVO;
        }
}
