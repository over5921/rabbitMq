package com.bjpowernode.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author z.wanqiang
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @date 2020/12/18 9:32
 */
public class Receive {

    public static void main(String[] args) {

        ConnectionFactory factory =new ConnectionFactory();
        factory.setHost("192.168.1.90");
        factory.setPort(5672);
        factory.setUsername("root");
        factory.setPassword("root");
        Connection connection=null;
        Channel channel=null;
        try{
            connection=factory.newConnection();
            channel=connection.createChannel();
            channel.queueDeclare("myQueue",true,false,false,null);
            /**
             * 接收消息
             * 参数 1 为当前消费者需要监听的队列名 ，队列名必须要与发送时的队列名完全一致否则接收不到消息
             * 参数 2 为消息是否自动确认，true表示自动确认接收完消息以后会自动将消息从队列中移除
             * 参数 3 为消息接收者的标签，用于当多个消费者同时监听一个队列时用于确认不通消费者，通常为空字符串即可
             * 参数 4 为消息接收的回调方法这个方法中具体完成对消息的处理代码
             * 注意：使用了basicConsume方法以后，会启动一个线程在持续的监听队列，如果队列中有信息的数据进入则会自动接收消息
             *      因此不能关闭连接和通道对象
             */

            channel.basicConsume("myQueue",true,"",new DefaultConsumer(channel){
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message=new String (body,"utf-8");
                    System.out.println("消费者--  "+message);
                }
            });
            //不能关闭通道和链接，如果一旦关闭可能会造成接收时抛出异常，或无法接收到消息
//            channel.close();
//            connection.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
