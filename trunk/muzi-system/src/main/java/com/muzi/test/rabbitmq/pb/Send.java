package com.muzi.test.rabbitmq.pb;

import com.muzi.test.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author libin
 * @date 2019/11/3 12:01
 */
public class Send {

    public static final String EXCHANGE_NAME = "test_exchange_muzi";


    public static void main(String[] args) throws IOException, TimeoutException {

        Connection conection = ConnectionUtils.getConection();

        Channel channel = conection.createChannel();

        //声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");

        //发送消息
        String msg = "君的名字";

        channel.basicPublish(EXCHANGE_NAME,"",null,msg.getBytes());

        System.out.println(msg);

        channel.close();
        conection.close();

    }


}
