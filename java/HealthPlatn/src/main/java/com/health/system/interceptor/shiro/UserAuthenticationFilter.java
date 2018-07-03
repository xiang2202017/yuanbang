package com.health.system.interceptor.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import com.health.system.util.Const;

public class UserAuthenticationFilter extends FormAuthenticationFilter{
	public static final String LOGIN_TYPE = Const.LOGIN_TYPE_USER.toString();
	private int maxSessions = 1;
	private boolean errorIfMaximumExceeded = false;

	@Override
	protected CustomizedToken createToken(ServletRequest request, ServletResponse response) {
		String username = getUsername(request);
		String password = getPassword(request);
		String host = getHost(request);

		return new CustomizedToken(username, password, LOGIN_TYPE);
	}

	public int getMaxSessions() {
		return maxSessions;
	}

	public void setMaxSessions(int maxSessions) {
		this.maxSessions = maxSessions;
	}

	public boolean isErrorIfMaximumExceeded() {
		return errorIfMaximumExceeded;
	}

	public void setErrorIfMaximumExceeded(boolean errorIfMaximumExceeded) {
		this.errorIfMaximumExceeded = errorIfMaximumExceeded;
	}


}
