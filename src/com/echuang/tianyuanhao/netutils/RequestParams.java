package com.echuang.tianyuanhao.netutils;

import java.io.Serializable;
import java.util.Map;

/**
 * 字段参数
 * 
 * @ClassName: RequestParams
 * @Description: TODO
 * @author 蒲江
 * @date 2016-7-8 下午5:53:58
 */
public class RequestParams implements Serializable {
	
	private static final long serialVersionUID = -8152996258157296029L;
	// 渠道
	private String channel;
	// 业务参数
	private Map<?, ?> params;
	// 签名
	private String sign;
	// 版本
	private String version;

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Map<?, ?> getParams() {
		return params;
	}

	public void setParams(Map<?, ?> params) {
		this.params = params;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
