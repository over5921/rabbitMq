package com.bjpowernode.rabbitmq.service.impl;

import com.bjpowernode.rabbitmq.service.SendService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author z.wanqiang
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @date 2020/12/28 11:12
 */
@Service("sendService")
public class SendServiceImpl implements SendService {
    //注入Amqp的模板类，利用这个对象来发送和接收消息
    @Resource
    private AmqpTemplate amqpTemplate;

    public void sendMessage(String message) {
        /**
         * 发送消息
         * 参数 1 为交换机名
         * 参数 2 为RoutingKey
         * 参数 3 为我们的具体发送的消息数据
         */
        amqpTemplate.convertAndSend("bootDirectExchange","bootDirectRoutingKey",message);
    }




    public void sendFanoutMessage(String message) {
        amqpTemplate.convertAndSend("fanoutExchange","",message);
    }

    public void sendTopicMessage(String message) {
        amqpTemplate.convertAndSend("topicExchange","aa.bb.cc",message);
    }
}
