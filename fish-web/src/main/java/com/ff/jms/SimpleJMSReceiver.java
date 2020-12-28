package com.ff.jms;

import org.springframework.jms.JmsException;

import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * @Author yuzhongxin
 * @Description
 * @Create 2017-08-09 20:52
 **/
public class SimpleJMSReceiver {
    public void receive(TextMessage message) throws JmsException, JMSException {
        System.out.println(message.getStringProperty("phrCode"));
        System.out.println(message.getText());
    }
}
