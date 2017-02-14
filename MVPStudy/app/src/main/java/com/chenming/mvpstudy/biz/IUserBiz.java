package com.chenming.mvpstudy.biz;

/**
 * Created by ChenMing on 2017/2/14.
 */

public interface IUserBiz {
	public void login(String username, String password, OnLoginListener loginListener);
}
