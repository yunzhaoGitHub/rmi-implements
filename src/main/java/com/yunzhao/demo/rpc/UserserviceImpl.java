package com.yunzhao.demo.rpc;

public class UserserviceImpl implements IUserservice {

    @Override
    public String login(String username, String password) {
        if ("tom".equalsIgnoreCase(username) && "1234".equals(password)) {
            return "登录成功" + username;
        }
        return "登录失败";
    }
}
