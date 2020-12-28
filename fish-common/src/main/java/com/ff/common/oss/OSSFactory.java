package com.ff.common.oss;

import com.ff.common.util.Constant;

public class OSSFactory {
	 	public static CloudStorageConfig cloudStorageConfig;

	    

	    public static CloudStorageService build(){

	        if(cloudStorageConfig.getType() == Constant.CloudService.QINIU.getValue()){
	            return new QiniuCloudStorageService(cloudStorageConfig);
	        }else if(cloudStorageConfig.getType() == Constant.CloudService.ALIYUN.getValue()){
	            return new AliyunCloudStorageService(cloudStorageConfig);
	        }else if(cloudStorageConfig.getType() == Constant.CloudService.QCLOUD.getValue()){
	            return new QcloudCloudStorageService(cloudStorageConfig);
	        }

	        return null;
	    }
}
