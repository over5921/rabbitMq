package com.bjpowernode.rabbitmq.service;

/**
 * @author z.wanqiang
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @date 2020/12/28 11:01
 */
public interface ReceiveService {
    void receive();
    void directReceive(String message);

}
