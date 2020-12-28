package com.ff.api.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.ff.common.model.AliPayConfig;
import com.ff.common.model.ResponseData;
import com.ff.common.model.WeixinPayConfig;
import com.ff.common.util.IpAddress;
import com.ff.common.util.JsonUtils;
import com.ff.order.model.Order;
import com.ff.order.service.OrderService;
import com.ff.payment.service.PaymentService;
import com.ff.rechage.model.RechageOrder;
import com.ff.rechage.service.RechageOrderService;
import com.ff.shop.model.PaymentMethod;
import com.ff.shop.model.ShopCoupon;
import com.ff.shop.model.ShopCouponOrder;
import com.ff.shop.model.ShopOrder;
import com.ff.shop.service.PaymentMethodService;
import com.ff.shop.service.ShopCouponOrderService;
import com.ff.shop.service.ShopOrderService;
import com.ff.users.model.Users;
import com.ff.users.service.UsersService;
import com.jpay.alipay.AliPayApi;
import com.jpay.alipay.AliPayApiConfig;
import com.jpay.alipay.AliPayApiConfigKit;
import com.jpay.util.StringUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@Controller
@RequestMapping("/api/alipay")
public class AliPayController extends AliPayApiController {
    private static final Logger log = LoggerFactory.getLogger(AliPayController.class);
	@Reference
	private UsersService usersService;
	@Reference
	private RechageOrderService rechageOrderService;
	@Reference
	private PaymentService paymentService;
	@Reference
	private PaymentMethodService paymentMethodService;
	private String notify_url ;

	@Reference
	private ShopOrderService shopOrderService;
	@Reference
	ShopCouponOrderService shopCouponOrderService;
	@Reference
	private OrderService orderService;

	@Override
	public AliPayApiConfig getApiConfig() {
		PaymentMethod map=new PaymentMethod();
		map.setPcode("alipay");
		PaymentMethod paymentMethod= paymentMethodService.findOne(map);
		AliPayConfig cfg= JsonUtils.jsonToPojo(paymentMethod.getParams(),AliPayConfig.class);
		notify_url=cfg.getUrl();
		return AliPayApiConfig.New()
				.setAppId(cfg.getAppId())
				.setAlipayPublicKey(cfg.getAlipayPublicKey())
				.setCharset("UTF-8")
				.setPrivateKey(cfg.getPrivateKey())
				.setServiceUrl("https://openapi.alipaydev.com/gateway.do")
				.setSignType("RSA2")
				.build();
	}



		/**
         * app支付
         */
	@ApiOperation(value="支付宝APP支付",notes="支付宝APP支付")
	@RequestMapping(value="/appPay",method= RequestMethod.GET)
	@ResponseBody
	public ResponseData appPay(
								@ApiParam(required=true,name="token",value="token")@RequestParam(value="token",required=false)String token,
							    @ApiParam(required=true,name="orderSn",value="orderSn")@RequestParam(value="orderSn",required=false)String orderSn,
								@ApiParam(required=true,name="orderType",value="orderType")@RequestParam(value="orderType",required=false)String orderType
							   ) {
		ResponseData result = new ResponseData();
		Users model=usersService.findUserByToken(token);

		AliPayApiConfigKit.putApiConfig(getApiConfig());

		AlipayTradeAppPayModel alipayTradeAppPayModel = new AlipayTradeAppPayModel();
		if(model==null)
		{
			result.setState(500);
			result.setMessage("用户不存在或已禁用");
			return result;
		}
		try {


			switch (orderType){
				case "goods"://商品订单
					Order order= orderService.findOrder(orderSn,model.getUserId());
					if(order==null){

						result.setState(500);
						result.setMessage("订单不存在");
						return result;
					}
					alipayTradeAppPayModel.setSubject("商品支付");
					alipayTradeAppPayModel.setTotalAmount(order.getOrderAmount().toString());
					alipayTradeAppPayModel.setOutTradeNo("goods-"+order.getOrderId());
					break;
				case "recharge"://充值订单
					RechageOrder mapRechageOrder=new RechageOrder();
					mapRechageOrder.setOrderSn(orderSn);
					mapRechageOrder.setUserId(model.getUserId());
					RechageOrder rechageOrder= rechageOrderService.findOne(mapRechageOrder);
					if(rechageOrder==null){

						result.setState(500);
						result.setMessage("订单不存在");
						return result;
					}
					alipayTradeAppPayModel.setSubject("充值支付");
					alipayTradeAppPayModel.setTotalAmount(rechageOrder.getMoney().toString());
					alipayTradeAppPayModel.setOutTradeNo("recharge-"+rechageOrder.getOrderSn());
					break;
				case "shop"://商家订单
					ShopOrder map=new ShopOrder();
					map.setOrderSn(orderSn);
					map.setUserId(model.getUserId());
					ShopOrder shopOrder= shopOrderService.findOne(map);
					if(shopOrder==null){

						result.setState(500);
						result.setMessage("订单不存在");
						return result;
					}
					alipayTradeAppPayModel.setSubject("快捷支付");
					alipayTradeAppPayModel.setTotalAmount(shopOrder.getMoney().toString());
					alipayTradeAppPayModel.setOutTradeNo("shop-"+shopOrder.getOrderSn());

					break;
				case "coupon"://代金券购买
					ShopCouponOrder shopCouponOrderMap=new ShopCouponOrder();
					shopCouponOrderMap.setOrderSn(orderSn);
					shopCouponOrderMap.setUserId(model.getUserId());
					ShopCouponOrder shopCouponOrder= shopCouponOrderService.findOne(shopCouponOrderMap);
					if(shopCouponOrder==null){

						result.setState(500);
						result.setMessage("订单不存在");
						return result;
					}
					alipayTradeAppPayModel.setSubject("代金券购买");
					alipayTradeAppPayModel.setTotalAmount(shopCouponOrder.getMoney().toString());
					alipayTradeAppPayModel.setOutTradeNo("coupon-"+shopCouponOrder.getOrderSn());

					break;
			}
			alipayTradeAppPayModel.setBody("天天聚乐补支付平台");

			alipayTradeAppPayModel.setTimeoutExpress("30m");

			alipayTradeAppPayModel.setPassbackParams("");
			alipayTradeAppPayModel.setProductCode("");

			String orderInfo = AliPayApi.startAppPay(alipayTradeAppPayModel, notify_url);
			result.setState(200);
			result.setDatas(orderInfo);
		} catch (AlipayApiException e) {
			e.printStackTrace();

		}
		return result;
	}
	
	
//	@RequestMapping(value = "/wapPay")
//	@ResponseBody
//	public void wapPay(HttpServletResponse response) {
//		String body = "我是测试数据-By Javen";
//		String subject = "Javen Wap支付测试";
//		String totalAmount = "1";
//		String passbackParams = "1";
//		String returnUrl = aliPayBean.getDomain() + "/alipay/return_url";
//		String notifyUrl = aliPayBean.getDomain() + "/alipay/notify_url";
//
//		AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
//		model.setBody(body);
//		model.setSubject(subject);
//		model.setTotalAmount(totalAmount);
//		model.setPassbackParams(passbackParams);
//		String outTradeNo = StringUtils.getOutTradeNo();
//		System.out.println("wap outTradeNo>"+outTradeNo);
//		model.setOutTradeNo(outTradeNo);
//		model.setProductCode("QUICK_WAP_PAY");
//
//		try {
//			AliPayApi.wapPay(response, model, returnUrl, notifyUrl);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//
//	/**
//	 * PC支付
//	 */
//	@RequestMapping(value = "/pcPay")
//	@ResponseBody
//	public void pcPay(HttpServletResponse response){
//		try {
//			String totalAmount = "88.88";
//			String outTradeNo =StringUtils.getOutTradeNo();
//			log.info("pc outTradeNo>"+outTradeNo);
//
//			String returnUrl = aliPayBean.getDomain() + "/alipay/return_url";
//			String notifyUrl = aliPayBean.getDomain() + "/alipay/notify_url";
//			AlipayTradePayModel model = new AlipayTradePayModel();
//
//			model.setOutTradeNo(outTradeNo);
//			model.setProductCode("FAST_INSTANT_TRADE_PAY");
//			model.setTotalAmount(totalAmount);
//			model.setSubject("Javen PC支付测试");
//			model.setBody("Javen IJPay PC支付测试");
//
//			AliPayApi.tradePage(response,model , notifyUrl, returnUrl);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//
//
//	/**
//	 * 条形码支付
//	 * https://doc.open.alipay.com/docs/doc.htm?spm=a219a.7629140.0.0.Yhpibd&
//	 * treeId=194&articleId=105170&docType=1#s4
//	 */
//	@RequestMapping(value ="/tradePay")
//	@ResponseBody
//	public String  tradePay(@RequestParam("auth_code") String authCode) {
//		String subject = "Javen 支付宝条形码支付测试";
//		String totalAmount = "100";
//		String notifyUrl = aliPayBean.getDomain() + "/alipay/notify_url";
//
//		AlipayTradePayModel model = new AlipayTradePayModel();
//		model.setAuthCode(authCode);
//		model.setSubject(subject);
//		model.setTotalAmount(totalAmount);
//		model.setOutTradeNo(StringUtils.getOutTradeNo());
//		model.setScene("bar_code");
//		try {
//			return AliPayApi.tradePay(model,notifyUrl);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return null;
//	}
//
//	/**
//	 * 声波支付
//	 * https://doc.open.alipay.com/docs/doc.htm?treeId=194&articleId=105072&docType=1#s2
//	 */
//	@RequestMapping(value ="/tradeWavePay")
//	@ResponseBody
//	public String tradeWavePay(@RequestParam("auth_code") String authCode) {
//		String subject = "Javen 支付宝声波支付测试";
//		String totalAmount = "100";
//		String notifyUrl = aliPayBean.getDomain() + "/alipay/notify_url";
//
//		AlipayTradePayModel model = new AlipayTradePayModel();
//		model.setAuthCode(authCode);
//		model.setSubject(subject);
//		model.setTotalAmount(totalAmount);
//		model.setOutTradeNo(StringUtils.getOutTradeNo());
//		model.setScene("wave_code");
//		try {
//			return AliPayApi.tradePay(model,notifyUrl);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return  null;
//	}
//
//	/**
//	 * 扫码支付
//	 */
//	@RequestMapping(value ="/tradePrecreatePay")
//	@ResponseBody
//	public String tradePrecreatePay() {
//		String subject = "Javen 支付宝扫码支付测试";
//		String totalAmount = "86";
//		String storeId = "123";
//		String notifyUrl = aliPayBean.getDomain() + "/alipay/notify_url";
//
//		AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
//		model.setSubject(subject);
//		model.setTotalAmount(totalAmount);
//		model.setStoreId(storeId);
//		model.setTimeoutExpress("5m");
//		model.setOutTradeNo(StringUtils.getOutTradeNo());
//		try {
//			String resultStr = AliPayApi.tradePrecreatePay(model, notifyUrl);
//			JSONObject jsonObject = JSONObject.parseObject(resultStr);
//			return jsonObject.getJSONObject("alipay_trade_precreate_response").getString("qr_code");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//	/**
//	 * 单笔转账到支付宝账户
//	 * https://doc.open.alipay.com/docs/doc.htm?spm=a219a.7629140.0.0.54Ty29&
//	 * treeId=193&articleId=106236&docType=1
//	 */
//	@RequestMapping(value = "/transfer")
//	@ResponseBody
//	public boolean transfer() {
//		boolean isSuccess = false;
//		String total_amount = "66";
//		AlipayFundTransToaccountTransferModel model = new AlipayFundTransToaccountTransferModel();
//		model.setOutBizNo(StringUtils.getOutTradeNo());
//		model.setPayeeType("ALIPAY_LOGONID");
//		model.setPayeeAccount("abpkvd0206@sandbox.com");
//		model.setAmount(total_amount);
//		model.setPayerShowName("测试退款");
//		model.setPayerRealName("沙箱环境");
//		model.setRemark("javen测试单笔转账到支付宝");
//
//		try {
//			isSuccess = AliPayApi.transfer(model);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return isSuccess;
//	}
//
//	/**
//	 * 下载对账单
//	 */
//	@RequestMapping(value = "/dataDataserviceBill")
//	@ResponseBody
//	public String dataDataserviceBill(@RequestParam("billDate") String billDate) {
//		try {
//			AlipayDataDataserviceBillDownloadurlQueryModel model = new AlipayDataDataserviceBillDownloadurlQueryModel();
//			model.setBillType("trade");
//			model.setBillDate(billDate);
//			return AliPayApi.billDownloadurlQuery(model);
//		} catch (AlipayApiException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//	/**
//	 * 退款
//	 */
//	@RequestMapping(value = "/tradeRefund")
//	@ResponseBody
//	public String tradeRefund() {
//
//		try {
//			AlipayTradeRefundModel model = new AlipayTradeRefundModel();
////			model.setOutTradeNo("042517111114931");
////			model.setTradeNo("2017042521001004200200236813");
//			model.setOutTradeNo("081014283315023");
//			model.setTradeNo("2017081021001004200200273870");
//			model.setRefundAmount("86.00");
//			model.setRefundReason("正常退款");
//			return AliPayApi.tradeRefund(model);
//		} catch (AlipayApiException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	/**
//	 * 交易查询
//	 */
//	@RequestMapping(value = "/tradeQuery")
//	@ResponseBody
//	public boolean tradeQuery() {
//		boolean isSuccess = false;
//		try {
//			AlipayTradeQueryModel model = new AlipayTradeQueryModel();
//			model.setOutTradeNo("081014283315023");
//			model.setTradeNo("2017081021001004200200273870");
//
//			isSuccess = AliPayApi.isTradeQuery(model);
//		} catch (AlipayApiException e) {
//			e.printStackTrace();
//		}
//		return isSuccess;
//	}
//	@RequestMapping(value = "/tradeQueryByStr")
//	@ResponseBody
//	public String  tradeQueryByStr(@RequestParam("out_trade_no") String out_trade_no) {
//		AlipayTradeQueryModel model = new AlipayTradeQueryModel();
//		model.setOutTradeNo(out_trade_no);
//
//		try {
//			return AliPayApi.tradeQueryToResponse(model).getBody();
//		} catch (AlipayApiException e) {
//			e.printStackTrace();
//		}
//
//		return null;
//	}
//
//	/**
//	 * 创建订单
//	 * {"alipay_trade_create_response":{"code":"10000","msg":"Success","out_trade_no":"081014283315033","trade_no":"2017081021001004200200274066"},"sign":"ZagfFZntf0loojZzdrBNnHhenhyRrsXwHLBNt1Z/dBbx7cF1o7SZQrzNjRHHmVypHKuCmYifikZIqbNNrFJauSuhT4MQkBJE+YGPDtHqDf4Ajdsv3JEyAM3TR/Xm5gUOpzCY7w+RZzkHevsTd4cjKeGM54GBh0hQH/gSyhs4pEN3lRWopqcKkrkOGZPcmunkbrUAF7+AhKGUpK+AqDw4xmKFuVChDKaRdnhM6/yVsezJFXzlQeVgFjbfiWqULxBXq1gqicntyUxvRygKA+5zDTqE5Jj3XRDjVFIDBeOBAnM+u03fUP489wV5V5apyI449RWeybLg08Wo+jUmeOuXOA=="}
//	 */
//	@RequestMapping(value = "/tradeCreate")
//	@ResponseBody
//	public String tradeCreate(@RequestParam("out_trade_no") String outTradeNo){
//
//		String notifyUrl = aliPayBean.getDomain()+ "/alipay/notify_url";
//
//		AlipayTradeCreateModel model = new AlipayTradeCreateModel();
//		model.setOutTradeNo(outTradeNo);
//		model.setTotalAmount("88.88");
//		model.setBody("Body");
//		model.setSubject("Javen 测试统一收单交易创建接口");
//		model.setBuyerLogonId("abpkvd0206@sandbox.com");//买家支付宝账号，和buyer_id不能同时为空
//		try {
//			AlipayTradeCreateResponse response = AliPayApi.tradeCreateToResponse(model, notifyUrl);
//			return response.getBody();
//		} catch (AlipayApiException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	/**
//	 * 撤销订单
//	 */
//	@RequestMapping(value = "/tradeCancel")
//	@ResponseBody
//	public boolean tradeCancel() {
//		boolean isSuccess = false;
//		try {
//			AlipayTradeCancelModel model = new AlipayTradeCancelModel();
//			model.setOutTradeNo("081014283315033");
//			model.setTradeNo("2017081021001004200200274066");
//
//			isSuccess = AliPayApi.isTradeCancel(model);
//		} catch (AlipayApiException e) {
//			e.printStackTrace();
//		}
//		return isSuccess;
//	}
//
//	/**
//	 * 关闭订单
//	 */
//	@RequestMapping(value = "/tradeClose")
//	@ResponseBody
//	public String tradeClose(@RequestParam("out_trade_no") String outTradeNo,@RequestParam("trade_no") String tradeNo){
//		try {
//			AlipayTradeCloseModel model = new AlipayTradeCloseModel();
//			model.setOutTradeNo(outTradeNo);
//
//			model.setTradeNo(tradeNo);
//
//			return AliPayApi.tradeCloseToResponse(model).getBody();
//		} catch (AlipayApiException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	/**
//	 * 结算
//	 */
//	@RequestMapping(value = "/tradeOrderSettle")
//	@ResponseBody
//	public String  tradeOrderSettle(@RequestParam("trade_no") String tradeNo){
//		try {
//			AlipayTradeOrderSettleModel model = new AlipayTradeOrderSettleModel();
//			model.setOutRequestNo(StringUtils.getOutTradeNo());
//			model.setTradeNo(tradeNo);
//
//			return AliPayApi.tradeOrderSettleToResponse(model ).getBody();
//		} catch (AlipayApiException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	@RequestMapping(value = "/return_url")
//	@ResponseBody
//	public String return_url(HttpServletRequest request) {
//		try {
//			// 获取支付宝GET过来反馈信息
//			Map<String, String> map = AliPayApi.toMap(request);
//			for (Map.Entry<String, String> entry : map.entrySet()) {
//				System.out.println(entry.getKey() + " = " + entry.getValue());
//			}
//
//			boolean verify_result = AlipaySignature.rsaCheckV1(map, aliPayBean.getPublicKey(), "UTF-8",
//					"RSA2");
//
//			if (verify_result) {// 验证成功
//				// TODO 请在这里加上商户的业务逻辑程序代码
//				System.out.println("return_url 验证成功");
//
//				return "success";
//			} else {
//				System.out.println("return_url 验证失败");
//				// TODO
//				return "failure";
//			}
//		} catch (AlipayApiException e) {
//			e.printStackTrace();
//			return "failure";
//		}
//	}
//
//
//
	@RequestMapping(value = "/notify_url",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String  notify_url(HttpServletRequest request) {
		try {
			AliPayApiConfigKit.putApiConfig(getApiConfig());
			// 获取支付宝POST过来反馈信息
			Map<String, String> params = AliPayApi.toMap(request);

			for (Map.Entry<String, String> entry : params.entrySet()) {
				System.out.println(entry.getKey() + " = " + entry.getValue());
			}

			boolean verify_result = AlipaySignature.rsaCheckV1(params, getApiConfig().getAlipayPublicKey(), "UTF-8",
					"RSA2");

			if (verify_result) {// 验证成功
				// TODO 请在这里加上商户的业务逻辑程序代码 异步通知可能出现订单重复通知 需要做去重处理
				System.out.println("notify_url 验证成功succcess");


				String trade_status=params.get("trade_status");
				//交易支付成功
				if(trade_status.equals("TRADE_SUCCESS")){
					//更新订单信息
					//
					String transaction_id=params.get("trade_no");
					String out_trade_no=params.get("out_trade_no");
					String[] order=out_trade_no.split("-");

					switch (order[0]){
						case "goods"://商品订单
							paymentService.goodsPayNotify(order[1],transaction_id);
							break;
						case "recharge"://充值订单
							paymentService.rechargePayNotify(order[1],transaction_id, IpAddress.getIp(request));
							break;
						case "shop"://商家订单
							paymentService.shopPayNotify(order[1],transaction_id, IpAddress.getIp(request));
							break;
						case "coupon"://商家订单
							paymentService.shopCouponPayNotify(order[1],transaction_id, IpAddress.getIp(request));
							break;
					}
				}

				return "success";
			} else {
				System.out.println("notify_url 验证失败");
				// TODO
				return "failure";
			}
		} catch (AlipayApiException e) {
			e.printStackTrace();
			return "failure";
		}
	}
}
