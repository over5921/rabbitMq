package com.bjpowernode.rabbitmq.exchange.topic;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Receive03 {
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

            channel.queueDeclare("topicQueue03",true,false,false,null);
            channel.exchangeDeclare("topicExchange","topic",true);
            channel.queueBind("topicQueue03","topicExchange","aa.#");
            channel.basicConsume("topicQueue03",true,"",new DefaultConsumer(channel){
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message=new String(body);
                    System.out.println("Receive03消费者aa.# ---"+message);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
