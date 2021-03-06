package com.zc.zactivemq.z03;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * <一句话功能简述>消息生产者
 * <功能详细描述>这个包演示的是 队列模式
 * 
 * @author  zhangcheng
 * @version  [版本号, 2017年7月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class AppProducer
{
    //61616为activeMQ默认端口   
    private static final String url = "tcp://127.0.0.1:61616";

    private static final String queueName = "queue-test";
    public static void main(String[] args) throws JMSException
    {
        //1.创建ConnectionFactory
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        //2.创建Connection
        Connection connection = connectionFactory.createConnection();
        //3.启动连接
        connection.start();
        //4.创建会话(是否使用事务)
        Session session = connection.createSession(false , Session.AUTO_ACKNOWLEDGE);
        //5.创建一个目标
        Destination destination = session.createQueue(queueName);
        //6.创建一个生产者
        MessageProducer producer = session.createProducer(destination);
        for(int i=0;i<100;i++){
            //7.创建消息
            TextMessage textMessage = session.createTextMessage("testMessage"+i);
            //8.发布消息
            producer.send(textMessage);
            System.out.println("发送消息"+textMessage.getText());
        }
        //9.关闭连接
        connection.close();
        //运行本程序之前先访问 浏览器看是否有消息,且启动activeMQ服务
        //运行本程序之后,再次查看浏览器,发送了100条消息,消费了0条
    }
}
