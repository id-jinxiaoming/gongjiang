package com.ff.users.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.ff.common.base.BaseServiceImpl;
import com.ff.users.dao.UsersMapper;
import com.ff.users.model.Users;
import com.ff.users.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;


@Service
public class  UsersServiceImpl extends BaseServiceImpl<Users> implements UsersService {

	@Autowired
    protected UsersMapper mapper;
    @Autowired
    protected JmsTemplate jmsTemplate;

    @Override
    public int insert(Users t) {
        mapper.insert(t);
        Integer id=t.getUserId();
        return id;
    }

    @Override
    public void testJMS() {

        jmsTemplate.send(new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                TextMessage msg = session.createTextMessage();
                // 设置消息属性
                msg.setStringProperty("phrCode", "C001");
                // 设置消息内容
                msg.setText("Hello World!");
                return msg;
            }
        });

    }

    @Override
    public Users findUserByToken(String token) {
        if(token==null||token.equals("")){
            return  null;
        }
        Users map=new Users();
        map.setToken(token);
        map.setStatus(1);
        return  mapper.selectOne(map);
    }


}
