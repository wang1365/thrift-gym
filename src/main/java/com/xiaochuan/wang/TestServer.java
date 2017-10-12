package com.xiaochuan.wang;

import com.xiaochuan.wang.tutorial.Calculator;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

public class TestServer {
    public static CalculatorHandler handler;
    public static Calculator.Processor processor;

    public static void main(String[] args) {
        handler = new CalculatorHandler();
        processor = new Calculator.Processor(handler);

        new Thread(() -> simple(processor)).start();
    }

    private static void simple(Calculator.Processor processor) {
        try {
            TServerTransport transport = new TServerSocket(9090);
            TServer server = new TSimpleServer(new TServer.Args(transport).processor(processor));

            System.out.println("server starting ...");
            server.serve();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
