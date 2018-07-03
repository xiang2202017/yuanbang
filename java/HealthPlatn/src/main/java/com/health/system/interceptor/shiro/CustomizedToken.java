package com.health.system.interceptor.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

public class CustomizedToken extends UsernamePasswordToken{
	private static final long serialVersionUID = 5120769386477509704L;
	
	//登录类型，判断是普通用户登录，教师登录还是管理员登录
    private String loginType;

    public CustomizedToken(final String username, final String password, String type) {
        super(username,password);
        this.loginType = type;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }
}
