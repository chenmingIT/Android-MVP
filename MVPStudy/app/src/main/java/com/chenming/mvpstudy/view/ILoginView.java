package com.chenming.mvpstudy.view;

import com.chenming.mvpstudy.bean.User;

/**
 * Created by ChenMing on 2017/2/14.
 */

public interface ILoginView {
	public String getUsername() ;
	public String getPassword() ;
	public void loginSuccess(User user) ;
	public void loginFail() ;
	public void clearUsername() ;
	public void clearPassword() ;
}
