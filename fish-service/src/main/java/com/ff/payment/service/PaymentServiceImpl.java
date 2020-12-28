package com.ff.payment.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.ff.common.util.OrderUtils;
import com.ff.order.dao.OrderMapper;
import com.ff.order.model.Order;
import com.ff.rechage.model.RechageOrder;
import com.ff.rechage.service.RechageOrderService;
import com.ff.shop.dao.ShopOrderMapper;
import com.ff.shop.model.*;
import com.ff.shop.service.*;
import com.ff.system.service.CommonService;
import com.ff.users.model.Users;
import com.ff.users.model.UsersAccount;
import com.ff.users.model.UsersAccountLog;
import com.ff.users.service.UsersAccountService;
import com.ff.users.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
public class PaymentServiceImpl implements PaymentService{

	@Autowired
	OrderMapper orderMapper;
	@Autowired
	RechageOrderService rechageOrderService;

	@Autowired
	ShopOrderMapper shopOrderMapper;

	@Autowired
	ShopService shopService;

	@Autowired
	ShopMoneyService shopMoneyService;

	@Autowired
	UsersService usersService;

	@Autowired
	UsersAccountService usersAccountService;

	@Autowired
	CommonService commonService;
	@Autowired
	ShopCouponOrderService shopCouponOrderService;
	@Autowired
	ShopCouponLogsService shopCouponLogsService;
	@Autowired
	ShopCouponService shopCouponService;
	@Override
	public boolean goodsPayNotify(String orderId, String thirdpartyTradeId) {
		Order map=new Order();
		map.setOrderId(orderId);
		Order order=orderMapper.selectOne(map);
		if(order!=null&&order.getOrderStatus()==1){
			order.setOrderStatus(2);
			order.setThirdpartyTradeId(thirdpartyTradeId);
			order.setPaymentDate(System.currentTimeMillis()/1000);
			orderMapper.updateById(order);

			//TODO 返积分给用户
			if(order.getRebateIntegral()!=null&&order.getRebateIntegral()>0){
				UsersAccountLog log=new UsersAccountLog();
				log.setUserId(order.getUserId());
				log.setExp(0);
				log.setBalance(new BigDecimal(0));
				log.setPoints(order.getRebateIntegral());
				log.setCause("您在商品订单："+order.getOrderId()+"中，返积分:"+order.getRebateIntegral());
				log.setAdminId("0");
				Long time=new Date().getTime()/1000;
				log.setDateline(Integer.parseInt(time.toString()));
				usersAccountService.updateAccount(log);
			}


			return true;
		}

		return false;
	}

	@Override
	public boolean rechargePayNotify(String orderId, String thirdpartyTradeId,String ip) {
		RechageOrder map=new RechageOrder();
		map.setOrderSn(orderId);
		RechageOrder order=rechageOrderService.findOne(map);
		if(order!=null&&order.getIsPay()==0){
			order.setIsPay(1);
			order.setPayIp(ip);
			order.setPayTime(new Date());
			order.setThirdpartyTradeId(thirdpartyTradeId);
			rechageOrderService.payment(order);
			return true;
		}
		return false;
	}

	@Override
	public boolean shopPayNotify(String orderId, String thirdpartyTradeId, String ip) {
		ShopOrder map=new ShopOrder();
		map.setOrderSn(orderId);
		ShopOrder order=shopOrderMapper.selectOne(map);
		if(order!=null&&order.getIsPay()==0){
			order.setIsPay(1);
			order.setPayIp(ip);
			order.setPayTime(new Date());
			order.setThirdpartyTradeId(thirdpartyTradeId);
			shopOrderMapper.updateById(order);


			//TODO 将支付金额写入商家余额  开启事务  商家设置结算比例
			ShopMoney shopMoney=new ShopMoney();
			shopMoney.setCreateIp("");
			shopMoney.setCreateTime(new Date());
			shopMoney.setIntro("");
			shopMoney.setMoney(order.getMoney());
			shopMoney.setOrderId(order.getOrderId());
			shopMoney.setShopId(order.getShopId());
			shopMoneyService.addLog(shopMoney);
			shopService.addMoney(order.getShopId(),order.getMoney());
			Shop shop=shopService.findByPrimaryKey(order.getShopId().toString());
			//TODO 将返利积分写入用户
			if(shop.getRate()!=null&&shop.getRate()>0&&order.getIntegral()>0){
				BigDecimal a1=order.getAllmoney().subtract(order.getAllmoney().multiply(BigDecimal.valueOf(shop.getIntegral())).divide(BigDecimal.valueOf(100)));
				BigDecimal a2=a1.multiply(BigDecimal.valueOf(shop.getRate())).divide(BigDecimal.valueOf(100));

				int integral= a2.intValue();
				if(integral>0){
					UsersAccountLog log=new UsersAccountLog();
					log.setUserId(order.getUserId());
					log.setExp(0);
					log.setBalance(new BigDecimal(0));

					log.setPoints(integral);
					log.setCause("您在"+shop.getTitle()+"消费"+order.getMoney()+"元，返积分:"+integral);
					log.setAdminId("0");
					Long time=new Date().getTime()/1000;
					log.setDateline(Integer.parseInt(time.toString()));
					usersAccountService.updateAccount(log);
				}



			}
			//TODO 推送商家
			Users user=usersService.findByPrimaryKey(shop.getUserId().toString());
			Map<String,String> obj=new HashMap<>();
			obj.put("type","fast");
			obj.put("orderId",order.getOrderId().toString());
			commonService.sendPushByRegistrationId(user.getJpushId(), "快捷支付订单", "有新的订单，请查看", obj);

			return true;
		}
		return false;
	}

	@Override
	public boolean shopCouponPayNotify(String orderId, String thirdpartyTradeId, String ip) {
		ShopCouponOrder map=new ShopCouponOrder();
		map.setOrderSn(orderId);
		ShopCouponOrder order=shopCouponOrderService.findOne(map);
		if(order!=null&&order.getIsPay()==0){
			order.setIsPay(1);
			order.setPayIp(ip);
			order.setPayTime(new Date());
			order.setThirdpartyTradeId(thirdpartyTradeId);
			shopCouponOrderService.updateByPrimaryKey(order);



			Shop shop=shopService.findByPrimaryKey(order.getShopId().toString());
			//TODO 将返利积分写入用户
			if(shop.getRate()!=null&&shop.getRate()>0&&order.getIntegral()>0){

				BigDecimal a1=order.getTotalMoney().subtract(order.getTotalMoney().multiply(BigDecimal.valueOf(shop.getIntegral())).divide(BigDecimal.valueOf(100)));
				BigDecimal a2=a1.multiply(BigDecimal.valueOf(shop.getRate())).divide(BigDecimal.valueOf(100));

				int integral= a2.intValue();
				if(integral>0){
					UsersAccountLog log=new UsersAccountLog();
					log.setUserId(order.getUserId());
					log.setExp(0);
					log.setBalance(new BigDecimal(0));
					log.setPoints(integral);
					log.setCause("您在"+shop.getTitle()+"购买代金券："+order.getMoney()+"元，返积分:"+integral);
					log.setAdminId("0");
					Long time=new Date().getTime()/1000;
					log.setDateline(Integer.parseInt(time.toString()));
					usersAccountService.updateAccount(log);
				}

			}

			ShopCoupon shopCoupon= shopCouponService.findByPrimaryKey(order.getCouponId().toString());

			ShopCouponLogs log=new ShopCouponLogs();
			log.setUserId(order.getUserId());
			log.setCouponId(order.getCouponId());
			log.setCode(OrderUtils.getOrderSN());
			log.setCreateTime(new Date());
			log.setLimitTime(shopCoupon.getEndTime());
			log.setPrice(shopCoupon.getPrice());
			log.setStatus(0);
			log.setShopId(shopCoupon.getShopId());
			try
			{
				log.setShopName( shop.getTitle());
			}
			catch (Exception e){

			}
			shopCouponLogsService.insert(log);
			shopCouponService.updateCount(order.getCouponId());

			return true;
		}
		return false;
	}
}
