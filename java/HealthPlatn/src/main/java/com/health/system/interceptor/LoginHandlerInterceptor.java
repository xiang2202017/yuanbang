package com.health.system.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.health.entity.system.Member;
import com.health.entity.system.User;
import com.health.system.util.Const;
import com.health.system.util.Jurisdiction;
/**
 * 
* 类名称：LoginHandlerInterceptor.java
* 类描述： 
* @version 1.6
 */
public class LoginHandlerInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		String path = request.getServletPath();
		if(path.matches(Const.NO_INTERCEPTOR_PATH)){
			return true;
		}else{
			if(path.matches("/web/member/")){//前台会员功能过滤
				//shiro管理的session
				Subject currentUser = SecurityUtils.getSubject();  
				Session session = currentUser.getSession();
				Member member = (Member)session.getAttribute(Const.SESSION_MEMBER);
				if(member!=null){
					return true;
				}else{
					//登陆过滤
					response.sendRedirect(request.getContextPath() + Const.MEMBER_LOGIN);
					return false;		
				}
			}else{//后台管理员路径过滤
				//shiro管理的session
				Subject currentUser = SecurityUtils.getSubject();  
				Session session = currentUser.getSession();
				User user = (User)session.getAttribute(Const.SESSION_USER);
				if(user!=null){
					path = path.substring(1, path.length());
					boolean b = Jurisdiction.hasJurisdiction(path);
					if(!b){
						response.sendRedirect(request.getContextPath() + Const.LOGIN);
					}
					return b;
				}else{
					//登陆过滤
					response.sendRedirect(request.getContextPath() + Const.LOGIN);
					return false;		
					//return true;
				}
			}
		}
	}
	
}
