package com.echuang.tianyuanhao.model;

/**
 * JSON解析第一层数据
 * 
 * @ClassName: ErrorMsgModel
 * @Description: TODO 主要用于数据校验是否正确
 * @author 蒲江
 * @date 2016-7-12 下午2:36:05
 */
public class ErrorMsgModel {
	private String errorMsg = "";
	private String msg = "";
	private boolean isSuccess = false;
	private String errorCode = "";

	private boolean isPayPwdSet = true;

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public boolean isPayPwdSet() {
		return isPayPwdSet;
	}

	public void setPayPwdSet(boolean isPayPwdSet) {
		this.isPayPwdSet = isPayPwdSet;
	}
}
