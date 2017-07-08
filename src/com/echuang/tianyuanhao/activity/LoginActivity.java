package com.echuang.tianyuanhao.activity;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.echuang.tianyuanhao.R;
import com.echuang.tianyuanhao.application.AppAppliction;
import com.echuang.tianyuanhao.base.BaseFragmentActivity;
import com.echuang.tianyuanhao.data.DecodeJsonManage;
import com.echuang.tianyuanhao.instance.LBInstance;
import com.echuang.tianyuanhao.model.LBLoginModel;
import com.echuang.tianyuanhao.model.LBMainAssetsModel;
import com.echuang.tianyuanhao.model.LBRecycleViewPagerImg;
import com.echuang.tianyuanhao.netutils.NetUtils;
import com.echuang.tianyuanhao.netutils.NetUtils.HttpCallback;
import com.echuang.tianyuanhao.netutils.UrlManage;
import com.echuang.tianyuanhao.utils.LogAPP;
import com.echuang.tianyuanhao.utils.RegexValidateUtil;
import com.echuang.tianyuanhao.utils.SPUtils;
import com.echuang.tianyuanhao.utils.StrRes;
import com.echuang.tianyuanhao.utils.Utils;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

/**
 * 登陆界面
 * 
 * @ClassName: LoginActivity
 * @Description: TODO
 */
public class LoginActivity extends BaseFragmentActivity implements
		OnClickListener {
	protected static final int LOGIN_IS_SUCCESS = 0;
	private boolean AgreeFlag = true;// 默认是选择
	private TimeCount timeCount;// 计时器 倒计时
	private int TimeImmediate = 60000;// 计时器 倒计时
	private Drawable mDrawableNo, mDrawableYes;
	private Button mLogin, mGetPhonecode, mAgreeRule;
	private EditText mPhoneCode, mMessageCode;
	private Handler mhandler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {

			case LOGIN_IS_SUCCESS:
				LBLoginModel loginModel = (LBLoginModel) msg.obj;// 这里可以拿到一些用户的信息
				
				
				
				// 这里才是真正的判断登陆成功或失败
				// 用户uid uid大于0表示登陆成功 等于零失败
				int uid = Integer.parseInt(loginModel.id);

				if (uid > 0) {
					// 登陆成功
					//首选项存储数据
					SPUtils.put(LoginActivity.this, "user_name", loginModel.user.user_name);
					SPUtils.put(LoginActivity.this, "user_address", loginModel.user.user_address);
					SPUtils.put(LoginActivity.this, "user_age", loginModel.user.user_age);
					SPUtils.put(LoginActivity.this, "user_company", loginModel.user.user_company);
					SPUtils.put(LoginActivity.this, "user_head", loginModel.user.user_head);
					SPUtils.put(LoginActivity.this, "user_id", loginModel.user.user_id);
					SPUtils.put(LoginActivity.this, "user_key", loginModel.user.user_key);
					SPUtils.put(LoginActivity.this, "user_mask", loginModel.user.user_mask);
					SPUtils.put(LoginActivity.this, "user_number", loginModel.user.user_number);
					SPUtils.put(LoginActivity.this, "user_perty", loginModel.user.user_perty);
					SPUtils.put(LoginActivity.this, "user_phone", loginModel.user.user_phone);
					SPUtils.put(LoginActivity.this, "user_phones", loginModel.user.user_phones);
					SPUtils.put(LoginActivity.this, "user_price", loginModel.user.user_price);
					SPUtils.put(LoginActivity.this, "user_pwd", loginModel.user.user_pwd);
					SPUtils.put(LoginActivity.this, "user_sex", loginModel.user.user_sex);
					SPUtils.put(LoginActivity.this, "user_tag", loginModel.user.user_tag);
					SPUtils.put(LoginActivity.this, "user_time", loginModel.user.user_time);
					//开启主界面
					Intent intent = new Intent(LoginActivity.this,
							MainActivity.class);
					startActivity(intent);
				} else {
					// 登陆失败
					toast("登陆失败！请检查你的账号或验证码");
				}

				break;

			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppAppliction.getInstance().addActivity(this);
		setContentView(R.layout.act_login);
		initView();
		setAgreeRule(AgreeFlag);

		mPhoneCode.setText("15527482433");
		mMessageCode.setText("");
	}

	@Override
	protected void initView() {
		mDrawableNo = getResources().getDrawable(R.drawable.ic_radio_no);
		mDrawableYes = getResources().getDrawable(R.drawable.ic_radio_yes);
		mDrawableNo.setBounds(0, 0, mDrawableNo.getMinimumWidth(),
				mDrawableNo.getMinimumHeight());
		mDrawableYes.setBounds(0, 0, mDrawableYes.getMinimumWidth(),
				mDrawableYes.getMinimumHeight());
		mLogin = (Button) findViewById(R.id.btn_login);
		// 获取验证码
		mGetPhonecode = (Button) findViewById(R.id.btn_get_phonecode);
		mAgreeRule = (Button) findViewById(R.id.btn_agree_rule);
		mPhoneCode = (EditText) findViewById(R.id.edt_phone_code);
		// 设置验证码
		mMessageCode = (EditText) findViewById(R.id.edt_message_code);
		mLogin.setOnClickListener(this);
		mAgreeRule.setOnClickListener(this);
		mGetPhonecode.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.btn_login:
			/*************临时代码块****************始*/
			//去掉登录验证
			SPUtils.put(LoginActivity.this, "user_name", "13621955552");
			SPUtils.put(LoginActivity.this, "user_id", "58");
			//开启主界面
			Intent intent = new Intent(LoginActivity.this,
					MainActivity.class);
			startActivity(intent);
			/*************临时代码块****************终*/
			
			if (TextUtils.isEmpty(mPhoneCode.getText().toString())) {
				toast("手机号不能为空");
				return;
			}
			if (TextUtils.isEmpty(mMessageCode.getText().toString())) {
				toast("验证码不能为空");
				return;
			}
			if (!AgreeFlag) {
				toast("需要同意使用条款");
				return;
			}
			if (invalitePhone()) {
				toast("请输入11位手机号");
				return;
			}
			// 保存账户
			sharedPreUtils.saveUserName("15000008899");
			sharedPreUtils.saveUserRealName("蒲江");
			sharedPreUtils.saveNeedUserName(true);
			sharedPreUtils.saveUserTel("15000008899");
			// 保存用户id
			sharedPreUtils.saveUserId("888");
			// 保存密码
			sharedPreUtils.savePassWord("000000");

			// 根据验证码判断是否登陆成功
			isLoginSuccess();

			break;
		case R.id.btn_agree_rule:
			if (AgreeFlag) {
				AgreeFlag = false;
				setAgreeRule(false);
			} else {
				AgreeFlag = true;
				setAgreeRule(true);
			}
			break;
		case R.id.btn_get_phonecode:
			if (invalitePhone()) {
				toast("请输入11位手机号");
				return;
			}
			getCode();
			break;
		case R.id.imageButton_left:
			back();
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			back();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void back() {
		finish();
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}

	@Override
	protected void next() {
	}

	/**
	 * 设置同意，不同意按钮
	 * 
	 * @Title: setAgreeRule
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 */
	private void setAgreeRule(boolean agree) {
		if (agree) {
			mAgreeRule.setCompoundDrawables(mDrawableYes, null, null, null);
		} else {
			mAgreeRule.setCompoundDrawables(mDrawableNo, null, null, null);
		}
	}

	/**
	 * 不符合返回true
	 * 
	 * @Title: invalitePhone
	 * @Description: TODO
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	private boolean invalitePhone() {
		boolean flag = false;
		String phone = mPhoneCode.getText().toString().trim();
		flag = !RegexValidateUtil.isMobileNO(phone);
		return flag;
	}

	/**
	 * 获取验证码
	 * 
	 * @Title: getCode
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 */
	private void getCode() {
		LogAPP.i("pj", "@-------->getCode");
		timeCount = new TimeCount(TimeImmediate, 5000);
		timeCount.start();
		// --------------------GET请求验证码---------------------
		// // 创建okHttpClient对象
		// OkHttpClient mOkHttpClient = new OkHttpClient();
		// // 创建一个Request
		// final Request request = new Request.Builder().url(
		// url)
		// .build();
		// // new call
		// Call call = mOkHttpClient.newCall(request);
		// // 请求加入调度
		// call.enqueue(new Callback() {
		//
		// @Override
		// public void onFailure(Call arg0, IOException arg1) {
		// // toast(R.string.linked_server_error);
		// System.out.print("onFailure："+"验证码已发送失败！");
		// }
		//
		// @Override
		// public void onResponse(Call arg0, Response response)
		// throws IOException {
		// String roomMessage = response.body().string();
		// // timeCount = new TimeCount(TimeImmediate, 1000);
		// // timeCount.start();
		// // toast("验证码已发送到手机，请注意查收！");
		// System.out.print("验证码已发送到手机，请注意查收！"+roomMessage);
		//
		// }
		// });

		// --------------------POST请求验证码---------------------
		startDialog();
		// 以前用的地址 ： String url = UrlManage.BASE_URL;
		String phoneNumber = mPhoneCode.getText().toString().trim();
		String url = UrlManage.LOGIN_MESSAGE_CODE + phoneNumber;
		// 这里导入的是http 的请求参数的包
		RequestParams params = new RequestParams();
		HashMap<String, String> map = new HashMap<String, String>();
		// Utils.addParams(map) 返回一个json字符串发送给服务器
		params.put(StrRes.message, Utils.addParams(map));
		NetUtils.getInstance().post(this, url, params, new HttpCallback() {

			@Override
			public void onSuccess(String content) {
				stopDialog();
				if (DecodeJsonManage.isSuccess(content)) {
					timeCount = new TimeCount(TimeImmediate, 1000);
					timeCount.start();
					System.out.print("验证码已发送到手机，请注意查收" + content);
					toast("验证码已发送到手机，请注意查收！");
				} else {
					toast(DecodeJsonManage.getErrorMsg(content));
				}
			}

			@Override
			public void onFailure(String content, int statusCode) {
				stopDialog();
				toast(R.string.linked_server_error);
			}
		});
		// --------------------分割线---------------------

	}

	/**
	 * 倒计时的类
	 * 
	 * @ClassName: TimeCount
	 * @Description: TODO
	 * @author 蒲江
	 * @date 2016-7-21 下午3:07:10
	 */
	public class TimeCount extends CountDownTimer {
		// 倒计时文案
		private String mCountdown;

		int textSizeRuning;
		int textSizeStop;

		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			mCountdown = getString(R.string.base_countdown_text);
			textSizeRuning = getResources().getDimensionPixelOffset(
					R.dimen.textsize_countdown);
			textSizeStop = getResources().getDimensionPixelOffset(
					R.dimen.textsize_little);
		}

		@Override
		public void onFinish() {
			mGetPhonecode.setText(getResources().getString(R.string.b_getcode));
			mGetPhonecode.setEnabled(true);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			mGetPhonecode.setEnabled(false);
			String text = String.format(mCountdown, millisUntilFinished / 1000);
			mGetPhonecode.setText(text);
		}

	}

	private void isLoginSuccess() {
		// 后面的数字为验证码
		String verifyCode = mMessageCode.getText().toString().trim();
		String phoneNumber = mPhoneCode.getText().toString().trim();
		String url = UrlManage.LOGIN_MESSAGE_ISSUCCESS + "phone=" + phoneNumber
				+ "&verify=" + verifyCode;
		System.out.print("你的电话号码为：" + phoneNumber + "    " + verifyCode);
		// 这里导入的是http 的请求参数的包
		RequestParams params = new RequestParams();
		HashMap<String, String> map = new HashMap<String, String>();
		// Utils.addParams(map) 返回一个json字符串发送给服务器
		params.put(StrRes.message, Utils.addParams(map));
		NetUtils.getInstance().post(this, url, params, new HttpCallback() {

			@Override
			public void onSuccess(String content) {
				stopDialog();

				System.out.print("验证码已发送到手机，请注意查收" + content);
				/*
				 * if (DecodeJsonManage.isSuccess(content)) { } else {
				 * toast(DecodeJsonManage.getErrorMsg(content)); }
				 */

				final LBLoginModel loginModel = new Gson().fromJson(content,
						LBLoginModel.class);
				Message msg = mhandler.obtainMessage();
				msg.what = LOGIN_IS_SUCCESS;
				msg.obj = loginModel;
				mhandler.sendMessage(msg);

			}

			@Override
			public void onFailure(String content, int statusCode) {
				stopDialog();
				toast(R.string.linked_server_error);
			}
		});

	}

}