package com.chenming.mvpstudy.presenter;

import com.chenming.mvpstudy.bean.User;
import com.chenming.mvpstudy.biz.IUserBiz;
import com.chenming.mvpstudy.biz.OnLoginListener;
import com.chenming.mvpstudy.biz.UserBiz;
import com.chenming.mvpstudy.view.ILoginView;

/**
 * Created by ChenMing on 2017/2/14.
 */

public class LoginPresenter implements ILoginPresenter {
	IUserBiz userBiz ;
	ILoginView iLoginView ;
	public LoginPresenter(ILoginView iLoginView){
		this.userBiz = new UserBiz() ;
		this.iLoginView = iLoginView ;
	}

	@Override
	public void login() {
		String username = iLoginView.getUsername() ;
		String password = iLoginView.getPassword() ;
		userBiz.login(username, password, new OnLoginListener() {
			@Override
			public void loginSuccess(User user) {
				iLoginView.loginSuccess(user);
			}

			@Override
			public void loginFailed() {
				iLoginView.loginFail();
			}
		});
	}

	@Override
	public void clear() {
		iLoginView.clearPassword();
		iLoginView.clearUsername();
	}
}
