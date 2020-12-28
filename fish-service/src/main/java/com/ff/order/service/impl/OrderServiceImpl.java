package com.ff.order.service.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseServiceImpl;
import com.ff.order.dao.OrderConsigneeMapper;
import com.ff.order.dao.OrderGoodsMapper;
import com.ff.order.dao.OrderGoodsOptionalMapper;
import com.ff.order.dao.OrderMapper;
import com.ff.order.model.Order;
import com.ff.order.model.OrderConsignee;
import com.ff.order.model.OrderGoods;
import com.ff.order.model.OrderGoodsOptional;
import com.ff.order.model.ex.Cart;
import com.ff.order.model.ex.Optional;
import com.ff.order.service.OrderService;
import com.ff.shop.model.Area;
import com.ff.shop.model.City;
import com.ff.shop.model.Goods;
import com.ff.shop.model.Province;
import com.ff.shop.service.AreaService;
import com.ff.shop.service.CityService;
import com.ff.shop.service.GoodsService;
import com.ff.shop.service.ProvinceService;
import com.ff.users.dao.UsersAccountLogMapper;
import com.ff.users.model.UsersAccountLog;
import com.ff.users.model.UsersConsignee;
import com.ff.users.service.UsersAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Service("orderService")
public class OrderServiceImpl extends BaseServiceImpl<Order> implements OrderService {

	@Autowired
    protected OrderMapper mapper;
    @Autowired
    protected OrderGoodsMapper orderGoodsMapper;
    @Autowired
    protected OrderGoodsOptionalMapper orderGoodsOptionalMapper;
    @Autowired
    protected OrderConsigneeMapper orderConsigneeMapper;

    @Autowired
    protected ProvinceService provinceService;

    @Autowired
    protected AreaService areaService;

    @Autowired
    protected CityService cityService;

    @Autowired
    protected GoodsService goodsService;


    @Autowired
    protected UsersAccountService usersAccountService;


    @Override
    public Page<Map<String, Object>> selectPageByOrderId(Page<Map<String, Object>> page, String orderId) {
        page.setRecords(mapper.selectPageByOrderId(page, orderId));
        return page;
    }

    @Override
    @Transactional
    public String createOrder(Order order, List<Cart> list, UsersConsignee usersConsignee) {
        //创建订单

        if(mapper.insert(order)>0){
            //添加订单商品
            for(Cart c:list){
                OrderGoods goods=new OrderGoods();
                goods.setGoodsId(c.getGoodsId());
                goods.setGoodsImage(c.getImage());
                goods.setGoodsName(c.getTitle());
                goods.setGoodsPrice(c.getPrice());
                goods.setGoodsQty(c.getNum());
                goods.setIsReviewed(0);
                goods.setOrderId(order.getOrderId());
                orderGoodsMapper.insert(goods);
                //添加商品OPT
                if(c.getOpt()!=null&&c.getOpt().size()>0){
                    for(Optional o:c.getOpt()){
                        OrderGoodsOptional orderGoodsOptional =new OrderGoodsOptional();
                        orderGoodsOptional.setMapId(goods.getId());
                        orderGoodsOptional.setOptId(o.getOptId());
                        orderGoodsOptional.setOptText(o.getOptText());
                        orderGoodsOptional.setOptType(o.getOptType());
                        orderGoodsOptionalMapper.insert(orderGoodsOptional);
                    }
                }
                //修改库存
                Goods good=goodsService.findByPrimaryKey(c.getGoodsId().toString());
                good.setStockQty(good.getStockQty()-c.getNum());
                goodsService.updateByPrimaryKey(good);

            }
            //添加订单收货地址
            OrderConsignee oc=new OrderConsignee();
            oc.setAddress(usersConsignee.getAddress());
            oc.setMobile(usersConsignee.getMobile());
            oc.setReceiver(usersConsignee.getReceiver());
            oc.setOrderId(order.getOrderId());

            Province province= provinceService.findByProvince(usersConsignee.getProvince().toString());
            City city= cityService.selectByCity(usersConsignee.getCity().toString());
            Area area= areaService.selectByArea(usersConsignee.getArea().toString());

            oc.setArea( area.getArea());
            oc.setCity( city.getCity());
            oc.setProvince(province.getProvince());
            orderConsigneeMapper.insert(oc);
            return order.getOrderId();

        }
        else{
            return "";
        }

    }

    @Override
    @Transactional
    public Boolean cancelOrder(String orderId) {
        Order orderMap=new Order();
        orderMap.setOrderId(orderId);
        Order order=mapper.selectOne(orderMap);
        order.setOrderStatus(0);
        Integer reuslt= mapper.updateById(order);
        //更新库存
        Map<String,Object> map=new HashMap<>();
        map.put("order_id",orderId);
        List<OrderGoods> list= orderGoodsMapper.selectByMap(map);
        for(OrderGoods o:list){
            Goods goods= goodsService.findByPrimaryKey(o.getGoodsId().toString());
            if(goods!=null){
                goods.setStockQty(goods.getStockQty()+o.getGoodsQty());
                goodsService.updateByPrimaryKey(goods);
            }
        }
        //退回积分
        UsersAccountLog log=new UsersAccountLog();
        log.setUserId(order.getUserId());
        log.setExp(0);
        log.setBalance(new BigDecimal(0));
        log.setPoints(order.getIntegral());
        log.setCause("用户取消订单,返还积分:"+order.getIntegral());
        log.setAdminId("0");
        Long time=new Date().getTime()/1000;
        log.setDateline(Integer.parseInt(time.toString()));
        usersAccountService.updateAccount(log);
        return true;
    }

    @Override
    public Boolean deliveredOrder(String orderId) {
        Order map =new Order();
        map.setOrderStatus(3);
        map.setOrderId(orderId);
        Order order= mapper.selectOne(map);
        if(order!=null){
            order.setOrderStatus(4);
            mapper.updateById(order);
            return true;
        }else{
            return false;
        }

    }

    @Override
    public Map<String, Object> findByOrderId(String orderId) {
        return mapper.findOrderByID(orderId);
    }

    @Override
    public Order findOrder(String orderId, Integer userId) {
        Order map =new Order();
        map.setOrderId(orderId);
        map.setUserId(userId);

        return  mapper.selectOne(map);
    }

    @Override
    public int selectCount(Integer status,Integer userId) {
        EntityWrapper<Order> ew =new EntityWrapper<>();
        ew.eq("order_status",status);
        ew.eq("user_id",userId);
        return  mapper.selectCount(ew);
    }
}
