package com.ff.shop.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseServiceImpl;
import com.ff.common.model.ResponseData;
import com.ff.common.util.JsonUtils;
import com.ff.shop.dao.ShippingMethodMapper;
import com.ff.shop.dao.ShopFavoritesMapper;
import com.ff.shop.model.ShippingMethod;
import com.ff.shop.model.ShopFavorites;
import com.ff.shop.model.bo.ShopFavoritesBO;
import com.ff.shop.model.ex.ShipingParams;
import com.ff.users.dao.UsersConsigneeMapper;
import com.ff.users.model.UsersConsignee;
import com.ff.users.service.UsersConsigneeService;
import net.sf.json.util.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


@Service
public class ShippingMethodServiceImpl extends BaseServiceImpl<ShippingMethod> implements ShippingMethodService{

    @Autowired
    ShippingMethodMapper shippingMethodMapper;

    @Autowired
    UsersConsigneeMapper usersConsigneeMapper;
    @Override
    public ResponseData getFreight(Integer cid, Integer shippingId,BigDecimal cartWeight,Integer cartNum) {
        ResponseData result=new ResponseData();
        UsersConsignee usersConsignee= usersConsigneeMapper.selectById(cid.toString());
        if(usersConsignee==null){
            result.setState(500);
            result.setMessage("收货地址不存在");
            return result;
        }
        ShippingMethod shippingMethod= shippingMethodMapper.selectById(shippingId);
        if(shippingMethod==null){
            result.setState(500);
            result.setMessage("配送方式不存在");
            return result;
        }
        ShipingParams param=null;
        List<ShipingParams> params= JsonUtils.jsonToList(shippingMethod.getParams(), ShipingParams.class);
        for(ShipingParams p:params){
            String[] areas=p.getArea();
            for (String area:areas){
                if(area.equals("0")|| area.equals(usersConsignee.getProvince().toString())){
                    param=p;
                    break;
                }
            }
        }

        if(param==null){
            result.setState(500);
            result.setMessage("没有匹配的配送费");
            return result;
        }
        BigDecimal amount;
        switch (param.getType()){
            case "fixed":
                amount=param.getCharges();
                break;
            case "weight":
                if(cartWeight.compareTo(param.getFirstWeight())>0)
                {
                    amount=param.getFirstCharges().add( param.getAddCharges().multiply((cartWeight.subtract(param.getFirstWeight())).divide(param.getAddWeight())));

                }
                else
                {
                    amount = param.getFirstCharges();
                }
                break;
            case "piece":
                if(cartNum >param.getFirstPiece())
                {
                    amount=param.getFirstCharges().add( param.getAddCharges().multiply(new BigDecimal(((cartNum-param.getFirstPiece())/param.getAddPiece()))));

                }
                else
                {
                    amount = param.getFirstCharges();
                }
                break;
                default:
                    amount=new BigDecimal(0);

        }
        result.setState(200);
        result.setDatas(amount);
        return result;

    }
}
