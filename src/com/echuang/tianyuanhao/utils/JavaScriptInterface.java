package com.echuang.tianyuanhao.utils;

import android.os.Handler;
import android.webkit.JavascriptInterface;

/**
 * Script跳转相关类
 * 
 * @ClassName: JavaScriptInterface
 * @Description: TODO
 *
 */
public final class JavaScriptInterface {
	private CallBackListener callBackListener;
	private Handler mHandler = new Handler();
	/** 退出 **/
	public static final int JS_returnLogoutMsg = 90002;
	/** 跳转登录 **/
	public static final int JS_returnLoginPageMsg = 90003;
	/** 注册 **/
	public static final int JS_returnRegisterMsg = 90004;
	/** 登录验证 **/
	public static final int JS_returnLoginValidateMsg = 90005;
	/** 注册验证 **/
	public static final int JS_returnRegisterValidateMsg = 90006;
	/** 检查更新 **/
	public static final int JS_returnUpdateMsg = 90007;
	/** 检查是否可以下拉刷新 **/
	public static final int JS_returnCanRefresh = 90008;

	/** 查看投资记录 **/
	public static final int JS_returnInvestHistory = 90009;
	/** 返回 **/
	public static final int JS_continueInvest = 900010;
	/** 秒杀登录 **/
	public static final int JS_seckillPageLogin = 900011;
	/** 秒杀投资 **/
	public static final int JS_seckillPageToInvest = 900012;
	/** 充值 **/
	public static final int JS_toCharge = 900013;
	/** 品宣主题页去投资 **/
	public static final int JS_bestPageToInvest = 900014;
	/** 推荐有礼页去分享 **/
	public static final int JS_recommendPageToPromotion = 900015;
	/**
	 * final back分支上加了个新人三重礼页面, 页面上有一个立即投资按钮, 要求: （登录状态：跳转到新客专区；未登录状态：跳转到登录界面）
	 **/
	public static final int JS_goToInvest = 900016;

	/**
	 * 返回退出结果
	 * 
	 * @param msg
	 */
	@JavascriptInterface
	public void returnLogoutMsg() {
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				if (null != callBackListener) {
					callBackListener.callBack("", JS_returnLogoutMsg);
				}
			}
		});
	}

	/**
	 * 返回跳转登录结果
	 * 
	 * @param msg
	 */
	@JavascriptInterface
	public void returnLoginPageMsg() {
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				if (null != callBackListener) {
					callBackListener.callBack("", JS_returnLoginPageMsg);
				}
			}
		});
	}

	/**
	 * 返回注册结果
	 * 
	 * @param msg
	 */
	@JavascriptInterface
	public void returnRegisterMsg(final String msg) {
		LogAPP.i("", msg);
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				if (null != callBackListener) {
					callBackListener.callBack(msg, JS_returnRegisterMsg);
				}
			}
		});
	}

	/**
	 * 返回登录验证结果
	 * 
	 * @param msg
	 */
	@JavascriptInterface
	public void returnLoginValidateMsg(final String msg) {
		LogAPP.i("", msg);
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				if (null != callBackListener) {
					callBackListener.callBack(msg, JS_returnLoginValidateMsg);
				}
			}
		});
	}

	/**
	 * 返回注册验证结果
	 * 
	 * @param msg
	 */
	@JavascriptInterface
	public void returnSendMessage(final String msg) {
		LogAPP.i("", msg);
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				if (null != callBackListener) {
					callBackListener
							.callBack(msg, JS_returnRegisterValidateMsg);
				}
			}
		});
	}

	/**
	 * 返回检查更新结果
	 * 
	 * @param msg
	 */
	@JavascriptInterface
	public void returnUpdate() {
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				if (null != callBackListener) {
					callBackListener.callBack("", JS_returnUpdateMsg);
				}
			}
		});
	}

	/**
	 * 返回是否可以下拉刷新
	 * 
	 * @param msg
	 */
	@JavascriptInterface
	public void returnCanRefresh(final String msg) {
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				if (null != callBackListener) {
					callBackListener.callBack(msg, JS_returnCanRefresh);
				}
			}
		});
	}

	/**
	 * 查看投资记录
	 * 
	 * @param msg
	 */
	@JavascriptInterface
	public void returnInvestHistory() {
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				if (null != callBackListener) {
					callBackListener.callBack("", JS_returnInvestHistory);
				}
			}
		});
	}

	/**
	 * 返回
	 * 
	 * @param msg
	 */
	@JavascriptInterface
	public void continueInvest() {
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				if (null != callBackListener) {
					callBackListener.callBack("", JS_continueInvest);
				}
			}
		});
	}

	/**
	 * 去充值
	 * 
	 * @param msg
	 */
	@JavascriptInterface
	public void toCharge() {
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				if (null != callBackListener) {
					callBackListener.callBack("", JS_toCharge);
				}
			}
		});
	}

	/**
	 * 秒杀切登录
	 * 
	 * @param msg
	 */
	@JavascriptInterface
	public void seckillPageLogin() {
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				if (null != callBackListener) {
					callBackListener.callBack("", JS_seckillPageLogin);
				}
			}
		});
	}

	/**
	 * 秒杀切投资
	 * 
	 * @param msg
	 */
	@JavascriptInterface
	public void seckillPageToInvest() {
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				if (null != callBackListener) {
					callBackListener.callBack("", JS_seckillPageToInvest);
				}
			}
		});
	}

	/**
	 * 品宣主题页去投资
	 */
	@JavascriptInterface
	public void bestPageToInvest() {
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				if (null != callBackListener) {
					callBackListener.callBack("", JS_bestPageToInvest);
				}
			}
		});
	}

	/**
	 * 推荐有礼页去分享
	 */
	@JavascriptInterface
	public void recommendPageToPromotion() {
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				if (null != callBackListener) {
					callBackListener.callBack("", JS_recommendPageToPromotion);
				}
			}
		});
	}

	/**
	 * final back分支上加了个新人三重礼页面, 页面上有一个立即投资按钮, 要求: （登录状态：跳转到新客专区；未登录状态：跳转到登录界面）
	 */
	@JavascriptInterface
	public void goToInvest() {
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				if (null != callBackListener) {
					callBackListener.callBack("", JS_goToInvest);
				}
			}
		});
	}

	/**
	 * 返回JS回调
	 * 
	 * @param msg
	 */
	@JavascriptInterface
	public void returnJS(final String msg, final int type) {
		LogAPP.i("", msg);
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				if (null != callBackListener) {
					callBackListener.callBack(msg, type);
				}
			}
		});
	}

	public CallBackListener getCallBackListener() {
		return callBackListener;
	}

	public void setCallBackListener(CallBackListener callBackListener) {
		this.callBackListener = callBackListener;
	}

	public interface CallBackListener {
		void callBack(String msg, int type);
	}

}
