//package com.muzi.test.rabbitmq.work;
//
//import com.muzi.test.rabbitmq.util.ConnectionUtils;
//import com.rabbitmq.client.*;
//
//import java.io.IOException;
//import java.util.concurrent.TimeoutException;
//
//*
// * @author libin
// * @date 2019/10/31 23:29
//
//
//public class Resvl {
//
//    public static void main(String[] args) throws IOException, TimeoutException {
//
//        Connection conection = ConnectionUtils.getConection();
//
//        Channel channel = conection.createChannel();
//
//        channel.queueDeclare(Send.QUEUE,false,false,false,null);
//
//
//        channel.basicQos(1);
//
//
//
//        Consumer consumer =new DefaultConsumer(channel){
//            @Override
//            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//                String msg = new String(body,"utf-8");
//                System.out.println("[1]"+msg);
//
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }finally {
//                    System.out.println("[1]御坂美琴");
//                    channel.basicAck(envelope.getDeliveryTag(),false);
//                }
//
//            }
//        };
//
//        channel.basicConsume(Send.QUEUE,false,consumer);
//
//
//
//    }
//}
