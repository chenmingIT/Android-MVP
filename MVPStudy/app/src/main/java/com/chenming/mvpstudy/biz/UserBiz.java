package com.chenming.mvpstudy.biz;

import com.chenming.mvpstudy.bean.User;

/**
 * Created by ChenMing on 2017/2/14.
 */

public class UserBiz implements IUserBiz {
	@Override
	public void login(String username, String password, OnLoginListener loginListener) {
		if(username.equals("username")&&password.equals("password")){
			User user = new User() ;
			user.setPassword(password);
			user.setUsername(username);
			loginListener.loginSuccess( user );
		}else {
			loginListener.loginFailed();
		}
	}
}
