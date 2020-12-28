package com.ff.api.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ff.common.model.ResponseData;
import com.ff.common.model.WeixinPayConfig;
import com.ff.common.util.IpAddress;
import com.ff.common.util.JsonUtils;
import com.ff.payment.service.PaymentService;
import com.ff.shop.model.PaymentMethod;
import com.ff.shop.service.PaymentMethodService;
import com.ff.users.model.Users;
import com.ff.users.service.UsersService;
import com.jpay.ext.kit.HttpKit;
import com.jpay.ext.kit.IpKit;
import com.jpay.ext.kit.PaymentKit;
import com.jpay.ext.kit.StrKit;
import com.jpay.weixin.api.WxPayApi;
import com.jpay.weixin.api.WxPayApiConfig;
import com.jpay.weixin.api.WxPayApiConfigKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Api(value="微信支付接口",description="微信支付接口")
@Controller
@RequestMapping(value="/api/wepPay")
public class WxPayController extends WxPayApiController {
    private String appId;
    private String mchId;
    private String paternerKey;


    @Reference
    private PaymentService paymentService;


    @Reference
    private PaymentMethodService paymentMethodService;
    String notify_url;
    @Override
    public WxPayApiConfig getApiConfig() {
        PaymentMethod map=new PaymentMethod();
        map.setPcode("weixinpay");
        PaymentMethod paymentMethod= paymentMethodService.findOne(map);
        WeixinPayConfig cfg= JsonUtils.jsonToPojo(paymentMethod.getParams(), WeixinPayConfig.class);
        //notify_url = "http://127.0.0.1/api/wepPay/pay_notify";
        notify_url =cfg.getUrl();
        return WxPayApiConfig.New()
                .setAppId(cfg.getAppId())
                .setMchId(cfg.getMchId())
                .setPaternerKey(cfg.getPaternerKey())
                .setPayModel(WxPayApiConfig.PayModel.BUSINESSMODEL);
    }


   @Reference
    private UsersService usersService;
    /**
     * 微信APP支付
     */
    @ApiOperation(value="微信APP支付",notes="微信APP支付", httpMethod = "GET")
    @RequestMapping(value="/appPay",method= RequestMethod.GET)
    @ResponseBody
    public ResponseData weixinPay(HttpServletRequest request,
                                   @ApiParam(required=true,name="token",value="token")@RequestParam(value="token",required=false)String token){
        ResponseData result = new ResponseData();
        Users model=usersService.findUserByToken(token);
        if(model==null)
        {
            result.setState(500);
            result.setMessage("用户不存在或已禁用");
            return result;
        }

        //不用设置授权目录域名
        //统一下单地址 https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_1#

        String ip = IpKit.getRealIp(request);
        if (StrKit.isBlank(ip)) {
            ip = "127.0.0.1";
        }

        WxPayApiConfigKit.putApiConfig(getApiConfig());

        Map<String, String> params = WxPayApiConfigKit.getWxPayApiConfig()
                .setAttach("51易购支付")
                .setBody("51易购支付测试")
                .setSpbillCreateIp(ip)
                .setTotalFee("100")
                .setTradeType(WxPayApi.TradeType.APP)
                .setNotifyUrl(notify_url)
                .setOutTradeNo(String.valueOf(System.currentTimeMillis()))
                .build();

        String xmlResult =  WxPayApi.pushOrder(false,params);

        Map<String, String> results = PaymentKit.xmlToMap(xmlResult);

        String return_code = results.get("return_code");
        String return_msg = results.get("return_msg");
        if (!PaymentKit.codeIsOK(return_code)) {
            result.setMessage(return_msg);
            result.setState(500);
            return result;
        }
        String result_code = results.get("result_code");
        if (!PaymentKit.codeIsOK(result_code)) {
            result.setMessage(result_code);
            result.setState(500);
            return result;

        }
        // 以下字段在return_code 和result_code都为SUCCESS的时候有返回
        String prepay_id = results.get("prepay_id");
        //封装调起微信支付的参数 https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_12
        Map<String, String> packageParams = new HashMap<String, String>();
        packageParams.put("appid", WxPayApiConfigKit.getWxPayApiConfig().getAppId());
        packageParams.put("mch_id", WxPayApiConfigKit.getWxPayApiConfig().getMchId());
        packageParams.put("prepayid", prepay_id);
        packageParams.put("package", "Sign=WXPay");
        packageParams.put("noncestr", System.currentTimeMillis() + "");
        packageParams.put("timestamp", System.currentTimeMillis() / 1000 + "");
        String packageSign = PaymentKit.createSign(packageParams, WxPayApiConfigKit.getWxPayApiConfig().getPaternerKey());
        packageParams.put("sign", packageSign);

        result.setDatas(packageParams);
        result.setMessage("");
        result.setState(200);
        return result;


    }


    @RequestMapping(value = "/pay_notify",method={RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public String pay_notify(HttpServletRequest request) {
        // 支付结果通用通知文档: https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_7
        String xmlMsg = HttpKit.readData(request);
        System.out.println("支付通知="+xmlMsg);
        Map<String, String> params = PaymentKit.xmlToMap(xmlMsg);
//		String appid  = params.get("appid");
//		//商户号
//		String mch_id  = params.get("mch_id");
        String result_code  = params.get("result_code");
//		String openId      = params.get("openid");
//		//交易类型
//		String trade_type      = params.get("trade_type");
//		//付款银行
//		String bank_type      = params.get("bank_type");
//		// 总金额
//		String total_fee     = params.get("total_fee");
//		//现金支付金额
//		String cash_fee     = params.get("cash_fee");
//		// 微信支付订单号
		String transaction_id      = params.get("transaction_id");
//		// 商户订单号
		String out_trade_no      = params.get("out_trade_no");
//		// 支付完成时间，格式为yyyyMMddHHmmss
//		String time_end      = params.get("time_end");

        /////////////////////////////以下是附加参数///////////////////////////////////

        String attach      = params.get("attach");
//		String fee_type      = params.get("fee_type");
//		String is_subscribe      = params.get("is_subscribe");
//		String err_code      = params.get("err_code");
//		String err_code_des      = params.get("err_code_des");
        // 注意重复通知的情况，同一订单号可能收到多次通知，请注意一定先判断订单状态
        // 避免已经成功、关闭、退款的订单被再次更新
//		Order order = Order.dao.getOrderByTransactionId(transaction_id);
//		if (order==null) {
        WxPayApiConfigKit.putApiConfig(getApiConfig());
        if(PaymentKit.verifyNotify(params, WxPayApiConfigKit.getWxPayApiConfig().getPaternerKey())){
            if (("SUCCESS").equals(result_code)) {
                //更新订单信息
                //
                String[] order=out_trade_no.split("-");
                Boolean flag=false;
                switch (order[0]){
                    case "goods"://商品订单
                        flag=paymentService.goodsPayNotify(order[1],transaction_id);
                        break;
                    case "recharge"://充值订单
                        flag=paymentService.rechargePayNotify(order[1],transaction_id, IpAddress.getIp(request));
                        break;
                    case "shop"://商家订单
                        flag=paymentService.shopPayNotify(order[1],transaction_id, IpAddress.getIp(request));
                        break;
                    case "coupon"://代金券购买订单
                        flag=paymentService.shopCouponPayNotify(order[1],transaction_id, IpAddress.getIp(request));
                        break;
                }
                if(flag){
                    //发送通知等
                    Map<String, String> xml = new HashMap<String, String>();
                    xml.put("return_code", "SUCCESS");
                    xml.put("return_msg", "OK");
                    return PaymentKit.toXml(xml);
                }

            }
        }
//		}

        return null;
    }

}
