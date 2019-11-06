//package com.muzi.test.rabbitmq.work;
//
//
//import com.muzi.test.rabbitmq.util.ConnectionUtils;
//import com.rabbitmq.client.Channel;
//import com.rabbitmq.client.Connection;
//
//import java.io.IOException;
//import java.util.concurrent.TimeoutException;
//
///**
// * @author libin
// * @date 2019/10/31 23:21
// */
//public class Send {
//
//    public static final String QUEUE="muzi";
//
//    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
//
//        Connection conection = ConnectionUtils.getConection();
//
//        Channel channel = conection.createChannel();
//
//        channel.queueDeclare(QUEUE,false,false,false,null);
//
//        /**
//         * 每个消费者发送确认消息之前，消息队列不发送下一个消息到消费者，一次只处理一个消息
//         *
//         */
//        channel.basicQos(1);
//
//
//        for(int i = 0 ;i<50 ;i++){
//            String msg = "hello" + i;
//            System.out.println("WQ send:"+ msg);
//            channel.basicPublish("",QUEUE,null,msg.getBytes());
//
//            Thread.sleep(i*5);
//        }
//
//        channel.close();
//        conection.close();
//    }
//}
