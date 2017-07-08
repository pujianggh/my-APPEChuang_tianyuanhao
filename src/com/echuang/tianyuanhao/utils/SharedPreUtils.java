package com.echuang.tianyuanhao.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 数据本地化
 * 
 * @ClassName: SharedPreUtils
 * @Description: TODO
 * @author 蒲江
 * @date 2016-7-8 下午5:35:48
 */
public class SharedPreUtils {
	private static final String SHARED_NAME = "Medishare";
	private SharedPreferences sp;
	private Editor editor;

	private static SharedPreUtils sharedPreUtils;

	public static SharedPreUtils getInstance(Context context) {
		if (sharedPreUtils == null) {
			sharedPreUtils = new SharedPreUtils(context);
		}
		return sharedPreUtils;
	}

	public SharedPreUtils(Context context) {
		sp = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE);
		editor = sp.edit();
	}

	// 保存是否第一次安装
	public void saveIsFirst(boolean value) {
		editor.putBoolean(SharedStr.ISFIRST, value);
		editor.commit();
	}

	// 获取是否第一次安装
	public boolean getIsFirst() {
		return sp.getBoolean(SharedStr.ISFIRST, false);
	}

	// 保存版本号
	public void saveVersionCode(int value) {
		editor.putInt(SharedStr.VERSIONCODE, value);
		editor.commit();
	}

	// 设置版本号
	public int getVersionCode() {
		return sp.getInt(SharedStr.VERSIONCODE, 0);
	}

	// 获取是否登陆
	public boolean getIsLogin() {
		return sp.getBoolean(SharedStr.ISLOGIN, false);
	}

	// 保存登陆状态
	public void saveIsLogin(boolean value) {
		editor.putBoolean(SharedStr.ISLOGIN, value);
		editor.commit();
	}

	/**************** 手势密码相关 start ***************/
	// 获取 是否存在手势密码
	public boolean getHasGesture() {
		return sp.getBoolean(SharedStr.GESTURE, false);
	}

	// 保存 是否存在手势密码
	public void saveGesture(boolean value) {
		editor.putBoolean(SharedStr.GESTURE, value);
		editor.commit();
	}

	// 下次进来是否需要设置手势密码
	public boolean isSetGestureNext() {
		return sp.getBoolean(SharedStr.GESTURE_NEXT, false);
	}

	// 下次进来是否需要设置手势密码
	public void saveSetGestureNext(boolean value) {
		editor.putBoolean(SharedStr.GESTURE_NEXT, value);
		editor.commit();
	}

	// 获取设置里手势密码开关状态
	public boolean getGestureSwitch() {
		return sp.getBoolean(SharedStr.GESTURE_SWITCH, false);
	}

	// 保存设置里手势密码开关状态
	public void saveGestureSwitch(boolean value) {
		editor.putBoolean(SharedStr.GESTURE_SWITCH, value);
		editor.commit();
	}

	// 暂时这么处理，之后从服务器获取，保存 start
	/*
	 * public String getGesturePassword(){ return sp.getString("gespassword",
	 * ""); } public void saveGesturePassword(String value){
	 * editor.putString("gespassword", value); editor.commit(); }
	 */
	// 暂时这么处理，之后从服务器获取，保存 end

	// 获取是否是第一次使用APP
	public boolean getFirstUseApp() {
		return sp.getBoolean("first_use_app", true);
	}

	// 设置是否是第一次使用APP
	public void setFirstUseApp(boolean first) {
		editor.putBoolean("first_use_app", first);
		editor.commit();
	}

	/**************** 手势密码相关 end ***************/

	// 获取是否首次进入
	public Boolean getFirstEnter() {
		return sp.getBoolean("first_enter", true);
	}

	// 保存是否首次进入
	public void saveFirstEnter(boolean value) {
		editor.putBoolean("first_enter", value);
		editor.commit();
	}

	// 获取首页专用图标
	public String getUserHomeImageUrl() {
		return sp.getString("mainPageImgUrl", "");
	}

	// 保存首页专用图标
	public void saveUserHomeImageUrl(String value) {
		editor.putString("mainPageImgUrl", value);
		editor.commit();
	}

	/** 保存登录账户 **/
	public void saveUserName(String value) {
		editor.putString(SharedStr.USERNAME, value);
		editor.commit();
	}

	/** 获取保存的登录账户 **/
	public String getUserName() {
		return sp.getString(SharedStr.USERNAME, "");
	}

	/** 保存账户是否是企业 **/
	public void saveUserIsCompany(boolean value) {
		editor.putBoolean(SharedStr.ISCOMPANY, value);
		editor.commit();
	}

	/** 获取企业 账户id **/
	public String getUserGroupCompanyId() {
		return sp.getString(SharedStr.USERGROUPCOMPANYID, "");
	}

	/** 保存企业账户id **/
	public void saveUserGroupCompanyId(String value) {
		editor.putString(SharedStr.USERGROUPCOMPANYID, value);
		editor.commit();
	}

	/** 获取账户是否是企业 **/
	public boolean getUserIsCompany() {
		return sp.getBoolean(SharedStr.ISCOMPANY, false);
	}

	/** 保存企业专享数据是否为空 **/
	public void saveCompanyListIsEmpty(boolean value) {
		editor.putBoolean(SharedStr.ISCOMPANYEMPTY, value);
		editor.commit();
	}

	/** 获取企业专享数据是否为空 **/
	public boolean getCompanyListIsEmpty() {
		return sp.getBoolean(SharedStr.ISCOMPANYEMPTY, false);
	}

	/** 保存登录账户的分销码 **/
	public void saveUserInvitationCode(String value) {
		editor.putString(SharedStr.INVITATINOCODE, value);
		editor.commit();
	}

	/** 获取保存的登录账户的分销码 **/
	public String getUserInvitationCode() {
		return sp.getString(SharedStr.INVITATINOCODE, "");
	}

	/** 保存后台返回登录账户 **/
	public void saveUserRealName(String value) {
		editor.putString(SharedStr.USERREALNAME, value);
		editor.commit();
	}

	/** 获取保存的后台返回登录账户 **/
	public String getUserRealName() {
		return sp.getString(SharedStr.USERREALNAME, "");
	}

	/** 保存是否要登录账户 **/
	public void saveNeedUserName(boolean need) {
		editor.putBoolean(SharedStr.NEEDSAVEUSERNAME, need);
		editor.commit();
	}

	/** 获取是否要保存登录账户 **/
	public boolean getNeedUserName() {
		return sp.getBoolean(SharedStr.NEEDSAVEUSERNAME, true);
	}

	/** 保存充值的batchNo **/
	public void saveBatchNo(String value) {
		editor.putString(SharedStr.BATCHNO, value);
		editor.commit();
	}

	/** 获取保存的充值batchNo **/
	public String getBatchNo() {
		return sp.getString(SharedStr.BATCHNO, "");
	}

	/** 保存首页数据 **/
	public void saveMainRequestData(String value) {
		editor.putString(SharedStr.MAINREQUESTDATA, value);
		editor.commit();
	}

	/** 获取保存的首页数据 **/
	public String getMainRequestData() {
		return sp.getString(SharedStr.MAINREQUESTDATA, "");
	}

	/** 保存首页数据请求时间 **/
	public void saveMainRequestTime(long value) {
		editor.putLong(SharedStr.MAINREQUESTTIME, value);
		editor.commit();
	}

	/** 获取保存的首页数据请求时间 **/
	public long getMainRequestTime() {
		return sp.getLong(SharedStr.MAINREQUESTTIME, 0);
	}

	/** 保存登录密码 **/
	public void savePassWord(String value) {
		editor.putString(SharedStr.PASSWORD,
				AESUtils.encrypt(StrRes._KEY, value));
		editor.commit();
	}

	/** 获取保存的登录，密码 **/
	public String getPassWord() {
		return AESUtils.decrypt(StrRes._KEY,
				sp.getString(SharedStr.PASSWORD, ""));
	}

	/** 保存用户id **/
	public void saveUserId(String value) {
		editor.putString(SharedStr.USERID, AESUtils.encrypt(StrRes._KEY, value));
		editor.commit();
	}

	/** 获取保存的用户id **/
	public String getUserId() {
		String str = null;
		str = sp.getString(SharedStr.USERID, "");
		if (str != null && str.length() > 0) {
			return AESUtils.decrypt(StrRes._KEY, str);
		} else {
			return str;
		}
	}

	/** 保存登录极光alias **/
	public void saveJpushKey(String value) {
		editor.putString(SharedStr.PUSHKEY, value);
		editor.commit();
	}

	/** 获取保存的极光alias **/
	public String getJpushKey() {
		return sp.getString(SharedStr.PUSHKEY, "null");
	}

	/** 保存登录是否要验证码 **/
	public void saveUserNeedCode(String value, boolean need) {
		editor.putBoolean(SharedStr.NEEDCODE + value, need);
		editor.commit();
	}

	/** 获取登录是否要验证码 **/
	public boolean getUserNeedCode(String value) {
		return sp.getBoolean(SharedStr.NEEDCODE + value, false);
	}

	/** 保存充值的银行卡 **/
	public void saveUserRechargeCard(String cardid) {
		editor.putString(SharedStr.NEEDBANKID + getUserId(), cardid);
		editor.commit();
	}

	/** 获取充值的银行卡 **/
	public String getUserRechargeCard() {
		return sp.getString(SharedStr.NEEDCODE + getUserId(), "");
	}

	/**
	 * 保存未登录时选择的页面index
	 */
	public void savePageIndex(int index) {
		editor.putInt(SharedStr.PAGEINDEX, index);
		editor.commit();
	}

	/**
	 * 获取未登录时选择的页面index
	 */
	public int getPageIndex() {
		return sp.getInt(SharedStr.PAGEINDEX, -1);
	}

	/** 保存是否 **/
	public void saveNoRecord(String value, boolean need) {
		editor.putBoolean(SharedStr.NORECORD + value, need);
		editor.commit();
	}

	/** 获取登录是否要验证码 **/
	public boolean getIsNoRecord(String value) {
		return sp.getBoolean(SharedStr.NORECORD + value, true);
	}

	/** 保存投资列表是否显示提示栏 **/
	public void saveIsShowInvestTips(boolean value) {
		editor.putBoolean(SharedStr.ISSHOWINVESTTIPS, value);
		editor.commit();
	}

	/** 获取投资列表是否显示提示栏 **/
	public boolean getIsShowInvestTips() {
		return sp.getBoolean(SharedStr.ISSHOWINVESTTIPS, true);
	}

	// 保存手机号码
	public void saveUserTel(String tel) {
		editor.putString(SharedStr.TEL, tel);
		editor.commit();
	}

	public String getUserTel() {
		return sp.getString(SharedStr.TEL, "");
	}

	// 保存签到状态
	public void saveSign(int counts) {
		editor.putInt(SharedStr.SIGN_STATUS, counts);
		editor.commit();
	}

	// 获得签到状态
	public int getSign() {
		return sp.getInt(SharedStr.SIGN_STATUS, 0);
	}

	/** 获取弹出提示自动更新 **/
	public boolean getShowVersionDialog() {
		return sp.getBoolean("UPDATE", true);
	}

	/** 保存弹出提示自动更新 **/
	public void setShowVersionDialog(boolean bol) {
		editor.putBoolean("UPDATE", bol);
		editor.commit();
	}

	/** 获取不提示自动更新的版本 **/
	public String getCancelUpdateVersionName() {
		return sp.getString("VERSIONNAME", "");
	}

	/** 保存不提示自动更新的版本 **/
	public void setCancelUpdateVersionName(String name) {
		editor.putString("VERSIONNAME", name);
		editor.commit();
	}

	/** 保存注册日期 **/
	public void saveRegistDate(String date) {
		editor.putString(SharedStr.REGISTDATE, date);
		editor.commit();
	}

	/** 获取注册日期 **/
	public String getRegistDate() {
		return sp.getString(SharedStr.REGISTDATE, "");
	}
}
