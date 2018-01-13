package com.zc.zactivemq.z03;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * <一句话功能简述>消费者 p2p模式
 * <功能详细描述>
 * 
 * @author  zhangcheng
 * @version  [版本号, 2017年7月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
//运行两个AppConsumer(消费者)main方法,再运行生产者生产消息,查看控制台输出,发现两个消费者均匀的接收生产者的消息
public class AppConsumer
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
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5.创建一个目标
        Destination destination = session.createQueue(queueName);
        //===========================================
        //6.创建一个消费者
        MessageConsumer consumer = session.createConsumer(destination);

        //7.创建一个监听器
        consumer.setMessageListener(new MessageListener()
        {

            public void onMessage(Message message)
            {
                TextMessage textMessage = (TextMessage) message;
                try
                {
                    //8.接收消息
                    System.out.println("消费者1消费了:"+textMessage.getText());
                }
                catch (JMSException e)
                {
                    e.printStackTrace();
                }
            }
        });
        //9.关闭连接,注意,消费者监听接收消息是一个异步的过程,这个过程中不能关闭连接,会接收不到消息
        //connection.close();
        //运行本程序之前先访问 浏览器看是否有消息,且启动activeMQ服务,且现在AppProducer中生产消息
        //运行本程序之后,再次查看浏览器,消费了100条消息.再次运行,已没有消息
    }
}
