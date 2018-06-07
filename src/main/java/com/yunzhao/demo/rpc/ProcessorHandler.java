package com.yunzhao.demo.rpc;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

public class ProcessorHandler implements Runnable {

    private Object service;

    private Socket socket;

    public ProcessorHandler(Object service, Socket socket) {
        this.service = service;
        this.socket = socket;
    }

    @Override
    public void run() {
        // 处理请求
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try {
            // 获取socket的输入流
            ois = new ObjectInputStream(socket.getInputStream());
            RpcRequest rpcRequest = (RpcRequest) ois.readObject();
            // 调用本地方法
            Object resultObj = invoke(rpcRequest);

            // 结果写回到客户端
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(resultObj);
            oos.flush();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Object invoke(RpcRequest rpcRequest) {
        // 通过反射调用方法
        Object[] args = rpcRequest.getParameters();
        Class<?>[] types = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            types[i] = args[i].getClass();
        }
        Object o = null;
        try {
            Method method = service.getClass().getMethod(rpcRequest.getMethodName(), types);
            o = method.invoke(service, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return o;
    }
}
