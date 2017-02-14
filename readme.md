#Android MVP框架学习
###github地址：https://github.com/chenmingIT/Android-MVP
***
##Android MVC框架

MVC全名是Model View Controller，是模型(model)－视图(view)－控制器(controller)。其中M层处理数据，业务逻辑等；V层处理界面的显示结果；C层起到桥梁的作用，来控制V层和M层通信以此来达到分离视图显示和业务逻辑层。  
在Android开发中，比较流行的开发框架模式采用的是MVC框架模式，采用MVC模式的好处是便于UI界面部分的显示和业务逻辑，数据处理分开。    
在Android项目中，业务逻辑，数据处理等担任了Model（模型）角色，XML界面显示等担任了View（视图）角色，Activity担任了Contronller（控制器）角色。contronller（控制器）是一个中间桥梁的作用，通过接口通信来协同 View（视图）和Model（模型）工作，起到了两者之间的通信作用。  
在MVC模式中我们发现，其实控制器Activity主要是起到解耦作用，将View视图和Model模型分离，虽然Activity起到交互作用，但是找Activity中有很多关于视图UI的显示代码，因此View视图和Activity控制器并不是完全分离的，也就是说一部分View视图和Contronller控制器Activity是绑定在一个类中的。
##Android MVP框架
###MVC架构  
View：对应于布局文件  
Model：业务逻辑和实体模型  
Controllor：对应于Activity
###MVP架构
View：对应于Activity，负责View的绘制以及与用户交互  
Model：依然是业务逻辑和实体模型  
Presenter：负责完成View于Model间的交互  
简化了Activity中的代码，将复杂的逻辑代码提取到了Presenter中进行处理。与之对应的好处就是，耦合度更低，更方便的进行测试。
###实践
以简单的登陆为例子。  
1、先做出界面

	<?xml version="1.0" encoding="utf-8"?>
	<LinearLayout
    	xmlns:android="http://schemas.android.com/apk/res/android"
    	android:id="@+id/activity_main"
    	android:layout_width="match_parent"
    	android:layout_height="match_parent"
    	android:padding="15dp"
    	android:orientation="vertical">
    	<EditText
    	    android:id="@+id/username"
    	    android:layout_width="match_parent"
    	    android:layout_height="wrap_content"
    	    android:hint="用户名"/>
    	<EditText
    	    android:id="@+id/password"
    	    android:layout_width="match_parent"
    	    android:layout_height="wrap_content"
    	    android:hint="密码"/>
    	<LinearLayout
    	    android:layout_width="match_parent"
    	    android:layout_height="wrap_content"
    	    android:orientation="horizontal">
    	    <Button
    	        android:id="@+id/login"
    	        android:layout_width="0dp"
    	        android:layout_height="wrap_content"
    	        android:layout_weight="1"
    	        android:text="登陆"/>
    	    <Button
    	        android:id="@+id/cancel"
    	        android:layout_width="0dp"
    	        android:layout_height="wrap_content"
    	        android:layout_weight="1"
    	        android:text="取消"/>
    	</LinearLayout>
	</LinearLayout>
2、首先做Model层，Model层主要是包括数据类型bean和业务逻辑biz。创建两个包bean用以放数据类型和biz用以放Model层的业务逻辑。在bean包下面创建User类，包括username和password两个成员并提供set和get方法，比较简单。  
3、在biz包中创建一个IUserBiz接口用以放User类的业务逻辑，并创建UserBiz作为IUserBiz接口的实现类，用以放Model层的业务逻辑。  
整理逻辑主要有一个登陆的业务逻辑，同时，登陆需要回调，所以在biz下面添加一个回调接口OnLoginListener，下面放成功和失败的方法。

	public interface OnLoginListener {
		void loginSuccess(User user);
		void loginFailed();
	}
在IUserBiz加入登陆业务逻辑方法。

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
4、View层  
Presenter与View交互是通过接口，因此要新建一个ILoginView接口，用途是放View层的方法。  
首先获取用户输入的用户名密码需要方法，登陆按钮对应登陆成功和失败的方法，和取消登录需要清空用户名和密码的方法，因此ILoginView如下。  

	public interface ILoginView {
		public String getUsername() ;
		public String getPassword() ;
		public void loginSuccess(User user) ;
		public void loginFail() ;
		public void clearUsername() ;
		public void clearPassword() ;
	}
之后写Activity，需要实现ILoginView。

	public class LoginActivity extends AppCompatActivity implements ILoginView{

		EditText username,password ;
		Button login,cancel ;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_main);
			username = (EditText)findViewById(R.id.username) ;
			password = (EditText)findViewById(R.id.password) ;
			login = (Button)findViewById(R.id.login) ;
			cancel = (Button)findViewById(R.id.cancel) ;	
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
5、Presenter层  
已经有了Model层和View层了，现在需要有Presenter层。新建presenter包，下面新建一个ILoginPresenter接口和LoginPresenter实现（接口的意义就在于便于扩展和维护）。   
ILoginPresenter接口方法无非就两个，登陆和取消。  

	public interface ILoginPresenter {
		public void login() ;
		public void clear() ;
	}
实现中，Presenter层就像粘合剂，因此需要引入IUserBiz和ILoginView成员。

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
6、回到Activity，需要加入ILoginPresenter成员。交互的时候，需要调用它的方法。  

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
###总结
MVP中把Layout布局和Activity作为View层，增加了Presenter，Presenter层与Model层进行业务的交互，完成后再与View层交互（也就是Activity）进行回调来刷新UI。这样一来，所有业务逻辑的工作都交给了Presenter中进行，使得View层与Model层的耦合度降低，Activity中的工作也进行了简化。  
MVP之Model  
模型这一层之中做的工作是具体业务逻辑处理的实现，都伴随着程序中各种数据的处理，复杂一些的就明显需要实现一个Interface来松耦合了。一般分为数据类型bean和业务逻辑处理与数据库交互等等biz（business缩写）。  
MVP之View  
视图这一层体现的很轻薄，负责显示数据、提供友好界面跟用户交互就行。MVP下Activity和Fragment体现在了这一 层，Activity一般也就做加载UI视图、设置监听再交由Presenter处理的一些工作，所以也就需要持有相应Presenter的引用。例 如，Activity上滚动列表时隐藏或者显示Acionbar（Toolbar），这样的UI逻辑时也应该在这一层。另外在View上输入的数据做一些 判断时，例如，EditText的输入数据，假如是简单的非空判断则可以作为View层的逻辑，而当需要对EditText的数据进行更复杂的比较时，如 从数据库获取本地数据进行判断时明显需要经过Model层才能返回了，所以这些细节需要自己掂量。即这一层主要是View显示和View操作控件操作，如监听按钮然后把事件交给Presenter，由它进行分发。  
MVP之Presenter  
Presenter这一层处理着程序各种逻辑的分发，收到View层UI上的反馈命令、定时命令、系统命令等指令后分发处理逻辑交由Model层做具体的业务操作。  
可以看见，这样能脱离View进行调试，而且代码结构更清楚，当然，代码量也变多了。






