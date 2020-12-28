package com.ff.common.util;


import org.apache.log4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.DateFormat;
import java.util.Date;


/**
 * @Author yuzhongxin
 * @Description
 * @Create 2017-07-17 10:11
 **/
public class MysqlUtil {
    static Logger logger = Logger.getLogger(MysqlUtil.class);

    /**
     * 备份数据库
     */
    public static void exportDataBase() {


        //HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Date now = new Date();
        DateFormat df = DateFormat.getDateTimeInstance();
        String dbName = df.format(now) + ".sql";
        dbName = dbName.replaceAll(":", "_");
        dbName = dbName.replaceAll(" ", "_");
        PropertiesUtil pr = new PropertiesUtil("config.properties");
        String user = pr.readProperty("jdbc_username");
        String password = pr.readProperty("jdbc_password");
        String database = pr.readProperty("jdbc_database");
        String filepath = pr.readProperty("jdbc_backupPath") + dbName;


        // 创建执行的批处理
        FileOutputStream fout=null;
        OutputStreamWriter writer=null;
        try{

            String batFile = pr.readProperty("mysql_basePathBatPath");


            fout = new FileOutputStream(batFile);
            writer = new OutputStreamWriter(fout, "utf8");
            StringBuffer sb = new StringBuffer("");
            sb.append(pr.readProperty("mysql_basePath")+" \r\n");
            sb.append("cd "+pr.readProperty("mysql_binPath")+" \r\n");
            sb.append("mysqldump -u"+user+" -p"+password+" "+database+" >"+filepath+"\r\n");
            sb.append("exit");
            String outStr = sb.toString();
            writer.write(outStr);
            writer.flush();
            writer.close();
            fout.close();


            Runtime.getRuntime().exec(" cmd /c start " + batFile);
            logger.info("备份数据库成功！");
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("备份数据库失败！", e);
        } finally{
            writer=null;
            fout=null;
        }

    }
}
