package com.bjpowernode.rabbitmq.service.impl;

import com.bjpowernode.rabbitmq.service.ReceiveService;
import com.rabbitmq.http.client.domain.ExchangeType;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author z.wanqiang
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @date 2020/12/28 11:02
 */
@Service("receiveService")
public class ReceiveServiceImpl implements ReceiveService {

    @Resource
    private AmqpTemplate amqpTemplate;
    /**
     * 这里个接收不是不间断接收消息，每执行一次这个方法只能接收一次消息，如果有新消息进入则不会自动接收消息
     * 不建议使用
     */
    @Override
    public void receive() {
        /*String message =(String)amqpTemplate.receiveAndConvert("bootDirectQueue");
        System.out.println(message);*/

    }

    /**
     * @RabbitListener 注解用于标记当前方法是一个RabbitMQ的消息监听方法，作用是持续性的自动接收消息
     * 这个方法不需要手动调用Spring会自动运行这个监听
     * 属性
     *   queues 用于指定一个已经存在的队列名，用于进行队列的监听
     * @param message  接收到的具体的消息数据
     *
     * 注意：如果当前监听方法正常结束Spring就会自动确认消息，如果出现异常则不会确认消息
     *      因此在消息处理时我们需要做好消息的防止重复处理工作
     */
    @RabbitListener(queues = {"bootDirectQueue"})
    public void directReceive(String message) {
        System.out.println("监听器接收的消息----"+message);
    }

    @RabbitListener(bindings={
                            @QueueBinding(//@QueueBinding注解要完成队列和交换机的
                                          value = @Queue(),//@Queue创建一个队列（没有指定参数则表示创建一个随机队列）
                                          exchange=@Exchange(value="fanoutExchange",type="fanout")//创建一个交换机
                                          )
                            }
                   )
    public void fanoutReceive01(String message){
        System.out.println("fanoutReceive01监听器接收的消息----"+message);
    }




    @RabbitListener(bindings={
            @QueueBinding(//@QueueBinding注解要完成队列和交换机的
                    value = @Queue(),//@Queue创建一个队列（没有指定参数则表示创建一个随机队列）
                    exchange=@Exchange(value="fanoutExchange",type= ExchangeTypes.FANOUT)//创建一个交换机
            )
    }
    )
    public void fanoutReceive02(String message){
        System.out.println("fanoutReceive02监听器接收的消息----"+message);
    }

        @RabbitListener(bindings = {@QueueBinding(value=@Queue("topic01"),key = ("aa"),exchange =@Exchange(value = "topicExchange",type = "topic"))})
    public void  topicReceive01(String message){
        System.out.println("topic01消费者 ---aa---"+message );
    }
    @RabbitListener(bindings = {@QueueBinding(value=@Queue("topic02"),key = ("aa.*"),exchange =@Exchange(value = "topicExchange",type = "topic"))})
    public void  topicReceive02(String message){
        System.out.println("topic02消费者 ---aa.*---"+message );
    }
    @RabbitListener(bindings = {@QueueBinding(value=@Queue("topic03"),key = ("aa.#"),exchange =@Exchange(value = "topicExchange",type = "topic"))})
    public void  topicReceive03(String message) {
        System.out.println("topic03消费者 ---aa。#---" + message);
    }

}
