package com.bjpowernode.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author z.wanqiang
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @date 2020/12/16 11:31
 */
public class Send {
    public static void main(String[] args) {
        //创建连接工厂
        ConnectionFactory factory=new ConnectionFactory();

        /**
         *功能描述  配置RabbitMq 的连接相关信息
         * @author z.wanqiang
         * @date 2020/12/16
         * @param  * @param null
         * @return
         */
        factory.setHost("192.168.1.90");
        factory.setPort(5672);
        factory.setUsername("root");
        factory.setPassword("root");
        Connection connection=null;
        Channel channel=null;
        try{
            connection=factory.newConnection();
            channel=connection.createChannel();
            /**
             * 声明一个队列，
             * 参数 1 为 队列名取值任意
             * 参数 2 为 是否为持久化的队列
             * 参数 3 为是否排外 如果排外则这个队列只允许一个消费者监听
             * 参数 4 是否自动删除对了，如果为true则表示当队列中没有消息，也没有消费者链接时就会自动删除这个队列
             * 参数 5 为队列的一些属性设置通常为null即可
             * 注意：
             *    1、声明队列时，这个队列名称如果已经存在则放弃声明，如果队列不存在则会声明一个新的队列
             *    2、队列名可以取值任意，但是要与消息接收时完全一致
             *    3、这行代码是可有可无的但是一定要在发送消息前确认队列名已经存在在RabbitMQ中，否则就会出现问题
             */
            channel.queueDeclare("myQueue",true,false,false,null);
            String message="我的rabbitMq测试消息5";
            /**
             * 发送消息到MQ
             * 参数 1 为交换机名称 这里为空字符串表示不使用交换机
             * 参数 2 为队列名或RoutingKey，当指定了交换机名称以后这个这个值就是RoutingKey
             * 参数 3 为消息属性信息 通常空即可
             * 参数 4 为具体的消息数据的字节数组
             * 注意：队列名必须要与接收时完全一致
             */
            channel.basicPublish("","myQueue",null,message.getBytes("utf-8"));
            System.out.println("消息发送成功");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(channel!=null){
                try {
                    channel.close();
                }  catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(connection!=null){
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
