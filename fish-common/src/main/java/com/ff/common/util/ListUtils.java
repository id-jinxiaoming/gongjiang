package com.ff.common.util;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {
    //删除重复数据
    public static List removeDuplicate(List list){
        List listTemp = new ArrayList();
        for(int i=0;i<list.size();i++){
            if(!listTemp.contains(list.get(i))){
                listTemp.add(list.get(i));
            }
        }
        return listTemp;
    }
}
