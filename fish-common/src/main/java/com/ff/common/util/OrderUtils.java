package com.ff.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderUtils {

    public static String getOrderSN(){
        String id="";
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        String temp = sf.format(new Date());
        int random=(int) (Math.random()*10000);
        id=temp+random;
        return id;
    }
}
