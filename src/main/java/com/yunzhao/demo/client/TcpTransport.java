package com.yunzhao.demo.client;


import java.io.*;
import java.net.Socket;

public class TcpTransport {

    private int port;
    private String host;

    public TcpTransport(int port, String host) {
        this.port = port;
        this.host = host;
    }


    public Object send(RpcRequest rpcRequest) {

        // 创建一个socket
        Socket clientSocket = null;
        // 写对象
        ObjectOutputStream oops = null;
        // 读对象
        ObjectInputStream oips = null;
        // 返回的Object
        Object obj = null;
        try {
            clientSocket = createClientSocket();
            // 获取输出流，将客户端需要调用的远程方法发送
            oops = new ObjectOutputStream(clientSocket.getOutputStream());
            oops.writeObject(rpcRequest);
            oops.flush();

            // 输入流
            oips = new ObjectInputStream(clientSocket.getInputStream());
            obj = oips.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (oips != null) {
                try {
                    oips.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (oops != null) {
                try {
                    oops.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return obj;
    }

    /**
     * 创建一个socket
     */
    private Socket createClientSocket() {

        Socket socket;

        try {
            socket = new Socket(host, port);
            return socket;
        } catch (Exception e) {
            throw new RuntimeException("创建客户端socket失败");
        }

    }
}
