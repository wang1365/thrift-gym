package com.xiaochuan.wang;


import com.xiaochuan.wang.tutorial.Calculator;
import com.xiaochuan.wang.tutorial.InvalidOperation;
import com.xiaochuan.wang.tutorial.SharedStruct;
import com.xiaochuan.wang.tutorial.Work;
import org.apache.thrift.TException;

import java.util.HashMap;
import java.util.Map;

public class CalculatorHandler implements Calculator.Iface {
    private Map<Integer, SharedStruct> log = new HashMap<>();

    @Override
    public void ping() throws TException {
        System.out.println("ping()");
    }

    @Override
    public int add(int num1, int num2) throws TException {
        System.out.println("" + num1 + " + " + num2 + " = " + (num1 + num2));
        return num1 + num2;
    }

    @Override
    public int calculate(int logid, Work w) throws InvalidOperation, TException {
        System.out.println(String.format("logid: %s, work.op:%d, work.num1:%d, work.num2:%d", logid, w.op, w.num1, w.num2));
        int val = 0;
        switch (w.op) {
            case ADD:
                val = w.num1 + w.num2;
                break;
            case SUBTRACT:
                val = w.num1 - w.num2;
                break;
            case MULTIPLY:
                val = w.num1 * w.num2;
                break;
            case DIVIDE:
                if (w.num2 == 0) {
                    InvalidOperation io = new InvalidOperation();
                    io.whatOp = w.op.getValue();
                    io.why = "Cannot divide by 0";
                    throw io;
                }
                val = w.num1 / w.num2;
                break;
            default:
                InvalidOperation io = new InvalidOperation();
                io.whatOp = w.op.getValue();
                io.why = "Unknown operation";
                throw io;
        }

        SharedStruct entry = new SharedStruct();
        entry.key = logid;
        entry.value = Integer.toString(val);
        log.put(logid, entry);
        return  val;
    }

    @Override
    public void zip() throws TException {
        System.out.println("zip()");
    }

    @Override
    public SharedStruct getStruct(int key) throws TException {
        SharedStruct entry = log.get(key);
        System.out.println(String.format("getStruct: %d -> (%d, %s)", key, entry.key, entry.value));
        return entry;
    }
}
