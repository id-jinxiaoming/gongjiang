package com.ff.api.controller;

import com.ff.common.base.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ff.common.qrcode.QRCodeUtil;


@RequestMapping("/api/test")
public class QrCodeController extends BaseController {



    public static void main(String[] args) throws Exception {
        // 存放在二维码中的内容
        String text ="http://mzgj.mzjicai.com/#/home/login?code=123456";
        // 嵌入二维码的图片路径
        String imgPath = "F:\\qrCode\\ycy.jpg";
        // 生成的二维码的路径及名称
        //String destPath = "F:\\qrCode\\haha.jpg";
        //生成二维码
        //QRCodeUtil.encode(text, imgPath, destPath, true);
        String url = QRCodeUtil.encode(text, imgPath, true);
        // 解析二维码
        //String str = QRCodeUtil.decode(destPath);
        // 打印出解析出的内容
        //System.out.println(str);
        System.out.printf(url);
    }


}
