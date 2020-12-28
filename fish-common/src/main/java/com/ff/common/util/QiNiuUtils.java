package com.ff.common.util;


import com.ff.common.oss.CloudStorageConfig;
import com.ff.common.oss.OSSFactory;


import java.io.InputStream;

public class QiNiuUtils {

  static String accesskey = "vzlrtop8a83t_R_OB4rSYZpYVigmNX2d8fi2SoL7";
  static String secretkey = "Al8QIomrlbvypDNzgZZUpfnA0XbqgFUHzFcluNKj";
  static String domain = "http://abc.mzjicai.com";
  static String bucketname = "mzjicai";

  public static String uplodPicture(InputStream inputStream){

      CloudStorageConfig cfg=new CloudStorageConfig();
      cfg.setType(1);
      cfg.setQiniuAccessKey(accesskey);
      cfg.setQiniuSecretKey(secretkey);
      cfg.setQiniuBucketName(bucketname);
      cfg.setQiniuDomain(domain);

      OSSFactory.cloudStorageConfig=cfg;


      String url = OSSFactory.build().upload(inputStream);

      return url;
    }
}
