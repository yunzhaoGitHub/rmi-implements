package com.yunzhao.demo.client;

/**
 * 客户端
 */
public class ClientDemo {

    public static void main(String[] args) {

        // 创建代理对象
        RpcClientProxy rpcClientProxy = new RpcClientProxy();
        IUserservice userservice = rpcClientProxy.buildClientProxy(IUserservice.class, "localhost", 8888);
        // 调用服务
        System.out.println(userservice.login("123", "123"));
    }
}
