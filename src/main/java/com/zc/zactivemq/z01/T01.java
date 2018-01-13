package com.zc.zactivemq.z01;

import org.apache.activemq.broker.BrokerService;

public class T01
{
    public static void main(String[] args) throws Exception
    {
        BrokerService broker = new BrokerService();
        broker.addConnector("tcp://localhost:61616");
        broker.start();
    }
}
