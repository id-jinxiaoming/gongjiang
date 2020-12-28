package com.ff.common.util;


public class CraftsmanUtils {


    public static double getNumberByIntegral(Integer integral){
        double number=0;
        if(0<integral && integral<=10){
            number=1.0;
        }else if (10 < integral && integral <= 20){
            number=2;
        }else if (20 < integral && integral <= 50){
            number=3;
        }else if (50 < integral && integral <= 70){
            number=3.5;
        }else if (70 < integral && integral <= 100){
            number=4;
        }else if (100 < integral && integral <= 150){
            number=4.5;
        }else{
            number=5;
        }
        return number;
    }

}
