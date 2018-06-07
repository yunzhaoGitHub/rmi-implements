package com.yunzhao.demo.rpc;

/**
 * 发布接口
 */
public class ServerDemo {

    public static void main(String[] args) {

        IUserservice iUserservice = new UserserviceImpl();
        // 发布
        RpcServer pcServer = new RpcServer();
        pcServer.publisher(iUserservice,8888);
    }
}
