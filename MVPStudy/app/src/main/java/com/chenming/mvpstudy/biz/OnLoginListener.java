package com.chenming.mvpstudy.biz;

import com.chenming.mvpstudy.bean.User;

/**
 * Created by ChenMing on 2017/2/14.
 */

public interface OnLoginListener {
	void loginSuccess(User user);
	void loginFailed();
}
