package com.ff.common.util;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

import java.util.Map;

public class JPush {
    static JPushClient jPushClient=null;
    public static void  initJpush(String appKey,String masterSecret){
        if(jPushClient==null)
        jPushClient =new JPushClient(masterSecret, appKey);
    }

    /**
     * 发送推送  registrationId
     * @param registrationId
     * @param title
     * @param sendMessage
     * @param map
     * @return
     */
    public static Boolean sendPushRegistrationId(String registrationId, String title, String sendMessage, Map<String, String> map) {

        PushPayload payload=PushPayload.newBuilder()
                .setPlatform(Platform.all())//设置接受的平台
                .setAudience(Audience.registrationId(registrationId))//Audience设置为registrationId，说明采用广播方式推送，指定用户都可以接收到
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.newBuilder()//安卓的推送设置
                                .setTitle(title)
                                .setAlert(sendMessage)
                                .addExtras(map).build())
                        .addPlatformNotification(IosNotification.newBuilder()//IOS的推送设置
                                .incrBadge(1)
                                .setBadge(0)
                                .setSound("default")
                                .setAlert(sendMessage)
                                .addExtras(map).build()).build())
                .setOptions(Options.newBuilder()
                        .setApnsProduction(true)
                        .build())
                .build();
        try {
            PushResult sendPush = jPushClient.sendPush(payload);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 发送推送 alias
     * @param alias
     * @param title
     * @param sendMessage
     * @param map
     * @return
     */
    public static boolean sendPushAlias(String alias,String title,String sendMessage,Map<String, String> map) {
        PushPayload payload=PushPayload.newBuilder()
                .setPlatform(Platform.all())//设置接受的平台
                .setAudience(Audience.alias(alias))//Audience设置为alias，说明采用广播方式推送，指定用户都可以接收到
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.newBuilder()//安卓的推送设置
                                .setTitle(title)
                                .setAlert(sendMessage)
                                .addExtras(map).build())
                        .addPlatformNotification(IosNotification.newBuilder()//IOS的推送设置
                                .incrBadge(1)
                                .setBadge(0)
                                .setSound("default")
                                .setAlert(sendMessage)
                                .addExtras(map).build()).build())
                .setOptions(Options.newBuilder()
                        .setApnsProduction(true)
                        .build())
                .build();
        try {
            PushResult sendPush = jPushClient.sendPush(payload);
        } catch (Exception e) {
            return false;
        }
        return true;
    }





}
