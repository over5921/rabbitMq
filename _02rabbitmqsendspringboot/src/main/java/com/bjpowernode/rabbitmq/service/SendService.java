package com.bjpowernode.rabbitmq.service;

/**
 * @author z.wanqiang
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @date 2020/12/28 11:11
 */
public interface SendService {

    void sendMessage(String message);
    void sendFanoutMessage(String message);
    void sendTopicMessage(String message);
}
