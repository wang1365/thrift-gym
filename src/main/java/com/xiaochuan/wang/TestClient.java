package com.xiaochuan.wang;

import com.xiaochuan.wang.tutorial.*;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class TestClient {
    public static void main(String[] args) {
        TTransport transport = new TSocket("localhost", 9090);
        try {
            transport.open();

            TProtocol protocol = new TBinaryProtocol(transport);
            Calculator.Client client = new Calculator.Client(protocol);
            perform(client);

            transport.close();
        } catch (TException e) {
            e.printStackTrace();
        }

    }

    private static void perform(Calculator.Client client) throws TException {
        client.ping();
        System.out.println("client ping()");

        int sum = client.add(10, 20);
        System.out.println("10 + 20 = " + sum);

        Work work = new Work();
        work.op = Operation.DIVIDE;
        work.num1 = 15;
        work.num2 = 0;

        try {
            int diff = client.calculate(1, work);
            System.out.println("Whoa, we can divide by 0");
        } catch (InvalidOperation io) {
            System.out.println("Invalid operation: " + work.op);
        } catch (Exception e) {
            System.out.println("..........");
            e.printStackTrace();
        }

        SharedStruct log = client.getStruct(1);
        System.out.println("check log: " + log.value);
    }
}
