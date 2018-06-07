package com.yunzhao.demo.client;

import java.lang.reflect.Proxy;

public class RpcClientProxy {

    public <T> T buildClientProxy(final Class<T> interfaceCls,
                                  final String host,
                                  final int port) {
        return (T) Proxy.newProxyInstance(interfaceCls.getClassLoader(), new Class[]{interfaceCls},
                new RemoteInvocationHandler(host, port));
    }
}
