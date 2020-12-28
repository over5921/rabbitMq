package com.bjpowernode.rabbitmq.transaction;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author z.wanqiang
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: 事务性消息
 * @date 2020/12/28 9:26
 */
public class Send {

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.1.90");
        factory.setPort(5672);
        factory.setUsername("root");
        factory.setPassword("root");
        Connection connection = null;
        Channel channel = null;
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare("transactionQueue", true, false, false, null);
            channel.exchangeDeclare("directTransactionExchange", "direct", true);
            channel.queueBind("transactionQueue", "directTransactionExchange", "transactionRoutingKey");
            String message = "事务性消息测试！";
            //启动一个事务，启动事务以后所有写入到队列中的消息
            //必须显示的调用 txCommit()提交事务或txRollback()回滚事务
            channel.txSelect();
            channel.basicPublish("directTransactionExchange", "transactionRoutingKey", null, message.getBytes("utf-8"));
            //提交事务，如果我们调用txSelect()方法启动了事务，那么必须显示调用事务的提交
            //否则消息不会真正的写入到队列，提交时以后会将内存中的消息写入队列并释放内存
            channel.txCommit();
            System.out.println("消息发送成功");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
            if (channel != null) {
                try {
                    //回滚事务，放弃当前事务中所有没有提交的消息，释放内存
                    channel.txRollback();
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
