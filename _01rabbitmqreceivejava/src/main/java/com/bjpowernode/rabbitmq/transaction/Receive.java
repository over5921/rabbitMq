package com.bjpowernode.rabbitmq.transaction;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author z.wanqiang
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: 事务性消息接收
 * @date 2020/12/28 9:42
 */
public class Receive {
    public static void main(String[] args) {
        ConnectionFactory factory=new ConnectionFactory();
        factory.setHost("192.168.1.90");
        factory.setPort(5672);
        factory.setUsername("root");
        factory.setPassword("root");
        Connection connection=null;
        Channel channel=null;
        try {
            connection=factory.newConnection();
            channel=connection.createChannel();

            channel.queueDeclare("transactionQueue",true,false,false,null);
            channel.exchangeDeclare("directTransactionExchange","direct",true);
            channel.queueBind("transactionQueue","directTransactionExchange","transactionRoutingKey");
            /**
             * 开启事务
             * 当消费者开启事务以后，即使不作为事务的提交，那么依然可以获取队列中的
             * 消息并且将消息从队列中移除掉
             * 注意：
             *   暂时事务队列接收者没有任何的影响
             */
            channel.txSelect();
            channel.basicConsume("transactionQueue",true,"",new DefaultConsumer(channel){
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message=new String(body);
                    System.out.println("消费者 ---"+message);
                }
            });

        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
