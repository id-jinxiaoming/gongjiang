package com.ff.common.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Yzx on 2017/5/16.
 */
public class UploadUtil {

    /** 上传文件处理本地*/
    public static String uploadFile(MultipartFile file, String path) {
        //创建子目录 取当前日期
        DateFormat df= new SimpleDateFormat("yyyyMMdd");
        String dirs=df.format(new Date());

        //取原始文件名
        String fileName = file.getOriginalFilename();
        //取文件后缀名
        String prefix=fileName.substring(fileName.lastIndexOf(".")+1);
        //保存的文件名
        fileName = dirs+"/"+new Date().getTime()+"."+prefix;

        File targetFile = new File(path, fileName);

        if(!targetFile.exists()){
            targetFile.mkdirs();
        }

        //保存
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;

    }


}
