package com.ff.job;

import com.ff.shop.model.AccessToken;
import com.ff.shop.service.WxTokenService;
import com.ff.system.model.ScheduleJob;
import net.sf.json.JSONObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskWxToken implements Job {
    public static final String appID = "wxb21eef7e1688b8e9";
    public static final String appScret = "a1eb7d0e83b29b92ef63caaa197d26b7";
    @Autowired
    private WxTokenService wxTokenService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        AccessToken accessToken = getAccessToken(appID,appScret);
        String jsapiTicket="";
        try {
            jsapiTicket=getJsApiTicket(accessToken.getToken());
        }catch (Exception e){
            e.printStackTrace();
        }
        AccessToken accessToken1 = wxTokenService.findByPrimaryKey("1");
        accessToken1.setTime(accessToken.getTime());
        accessToken1.setToken(accessToken.getToken());
        accessToken1.setJsapiTicket(jsapiTicket);
        wxTokenService.updateByPrimaryKey(accessToken1);

        ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        System.out.println("任务名称 = [" + scheduleJob.getName() + "]" + " 在 " + dateFormat.format(new Date()) + " 时运行");

    }
    /**
     * 获取accessToken
     * @paramappID微信公众号凭证
     * @paramappScret微信公众号凭证秘钥
     * @return
     */
    public AccessToken getAccessToken(String appID, String appScret) {
        AccessToken token = new AccessToken();
        // 访问微信服务器
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appID + "&secret=" + appScret;
        try {
            URL getUrl=new URL(url);
            HttpURLConnection http=(HttpURLConnection)getUrl.openConnection();
            http.setRequestMethod("GET");
            http.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);

            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] b = new byte[size];
            is.read(b);

            String message = new String(b, "UTF-8");
            JSONObject json = JSONObject.fromObject(message);

            token.setToken(json.getString("access_token"));
            token.setTime(new Integer(json.getString("expires_in")));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return token;
    }


   public String getJsApiTicket(String accessToken) throws Exception {
       InputStream inputStream = null;
       HttpURLConnection connection = null;
       String message = "";
       try {
           String path = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
           path = path.replace("ACCESS_TOKEN", accessToken);
           URL url = new URL(path);
           connection = (HttpURLConnection) url.openConnection();

           //设置本次请求的方式 ， 默认是GET方式， 参数要求都是大写字母
           connection.setRequestMethod("GET");
           //设置连接超时
           connection.setConnectTimeout(50000);
           //是否打开输入流 ， 此方法默认为true
           connection.setDoInput(true);
           //是否打开输出流， 此方法默认为false
           connection.setDoOutput(true);
           connection.setUseCaches(false);
           //表示连接
           connection.connect();
           inputStream = connection.getInputStream();

           int size = inputStream.available();
           byte[] bs = new byte[size];
           inputStream.read(bs);
           message = new String(bs, "UTF-8");
       } catch (Exception e) {
           e.printStackTrace();
       } finally {
           // 关闭资源
           if (inputStream != null) {
               inputStream.close();
           }
           // 关闭远程链接
           if (connection != null) {
               connection.disconnect();
           }
       }
       JSONObject json = JSONObject.fromObject(message);
       return json.getString("ticket");

   }
}
