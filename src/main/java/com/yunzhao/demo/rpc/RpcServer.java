package com.yunzhao.demo.rpc;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RpcServer {

    // 线程池
    //private static final ExecutorService executorService = Executors.newCachedThreadPool();

    // 发布服务
    public void publisher(final Object service, final int port){

        // 服务端ServerSocket
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port);

            // 循环监听
            while (true) {
                Socket socket = serverSocket.accept();// 监听服务

                // 开启线程处理请求
                // executorService.execute(new ProcessorHandler(service, socket));
                new Thread(new ProcessorHandler(service, socket)).start();

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
