package com.chenming.mvpstudy.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chenming.mvpstudy.R;
import com.chenming.mvpstudy.bean.User;
import com.chenming.mvpstudy.presenter.ILoginPresenter;
import com.chenming.mvpstudy.presenter.LoginPresenter;

public class LoginActivity extends AppCompatActivity implements ILoginView{

	EditText username,password ;
	Button login,cancel ;
	ILoginPresenter loginPresenter ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		username = (EditText)findViewById(R.id.username) ;
		password = (EditText)findViewById(R.id.password) ;
		login = (Button)findViewById(R.id.login) ;
		cancel = (Button)findViewById(R.id.cancel) ;
		loginPresenter = new LoginPresenter(this) ;
		login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				loginPresenter.login();
			}
		});
		cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				loginPresenter.clear();
			}
		});
	}

	@Override
	public String getPassword() {
		return password.getText().toString() ;
	}

	@Override
	public String getUsername() {
		return username.getText().toString() ;
	}

	@Override
	public void clearPassword() {
		password.setText("");
	}

	@Override
	public void clearUsername() {
		username.setText("");
	}

	@Override
	public void loginFail() {
		Toast.makeText(this,"登陆失败",Toast.LENGTH_SHORT).show();
	}

	@Override
	public void loginSuccess(User user) {
		Toast.makeText(this,"登陆成功",Toast.LENGTH_SHORT).show();
	}
}
