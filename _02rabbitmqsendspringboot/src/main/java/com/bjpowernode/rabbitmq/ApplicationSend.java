package com.bjpowernode.rabbitmq;

import com.bjpowernode.rabbitmq.service.SendService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class ApplicationSend {

    public static void main(String[] args) {
        ApplicationContext ac=SpringApplication.run(ApplicationSend.class, args);
        SendService service= (SendService) ac.getBean("sendService");
//        service.sendMessage("Boot的测试数据");

//        service.sendFanoutMessage("Boot的Fanout测试数据");
        service.sendTopicMessage("Boot的Topic测数据数据key 为 aa.bb.cc");
    }

}
