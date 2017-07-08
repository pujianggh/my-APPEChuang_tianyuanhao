package com.echuang.tianyuanhao.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import com.alibaba.fastjson.JSONObject;

/**
 * 工具类
 * 
 * @ClassName: AppUtil
 * @Description: TODO 主要用于与后端交互调用
 * @author 蒲江
 * @date 2016-7-8 下午5:59:41
 */
public class AppUtil {
	/**
	 * 判断
	 * 
	 * @Title: isnull
	 * @Description: str空判断
	 * @param @param str
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public static boolean isnull(String str) {
		if (null == str || str.equalsIgnoreCase("null") || str.equals("")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 待签名
	 * 
	 * @Title: genSignData
	 * @Description: TODO 生成待签名串
	 * @param @param map
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String genSignData(Map map) {
		JSONObject jsonObject = (JSONObject) JSONObject.toJSON(map);
		StringBuffer content = new StringBuffer();
		// 按照key做首字母升序排列
		List<String> keys = new ArrayList<String>(jsonObject.keySet());
		Collections.sort(keys, String.CASE_INSENSITIVE_ORDER);
		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);
			String value = jsonObject.getString(key);
			// 空串不参与签名
			if (isnull(value)) {
				continue;
			}
			if (value.contains("[")) {
				List<?> li = JSONAlibabaUtil.toList(value, Object.class);
				if (li instanceof List<?>) {
					continue;
				}
			}
			content.append((i == 0 ? "" : "&") + key + "=" + value);
		}
		String signSrc = content.toString();
		if (signSrc.startsWith("&")) {
			signSrc = signSrc.replaceFirst("&", "");
		}
		return signSrc;
	}

	/**
	 * 加签
	 * 
	 * @Title: addSign
	 * @Description: 加签
	 * @param @param sign_src
	 * @param @param md5_key
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String addSign(String sign_src, String md5_key) {
		if (sign_src == null) {
			return "";
		}
		return addSignMD5(sign_src, md5_key);
	}

	/**
	 * 签名
	 * 
	 * @Title: checkSign
	 * @Description: 签名验证
	 * @param @param sign
	 * @param @param map
	 * @param @param md5_key
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public static boolean checkSign(String sign, Map map, String md5_key) {
		if (map == null) {
			return false;
		}
		return checkSignMD5(sign, map, md5_key);
	}

	/**
	 * 签名
	 * 
	 * @Title: checkSignMD5
	 * @Description: TODO MD5签名验证
	 * @param @param sign
	 * @param @param map
	 * @param @param md5_key
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	private static boolean checkSignMD5(String sign, Map map, String md5_key) {
		if (map == null) {
			return false;
		}
		// 生成待签名串
		String sign_src = genSignData(map);
		sign_src += "&key=" + md5_key;
		try {
			if (sign.equals(Md5Algorithm.getInstance().md5Digest(
					sign_src.getBytes("utf-8")))) {
				System.out.println("MD5签名验证通过");
				return true;
			} else {
				System.out.println("MD5签名验证未通过");
				return false;
			}
		} catch (UnsupportedEncodingException e) {
			System.out.println("MD5签名验证异常" + e.getMessage());
			return false;
		}
	}

	/**
	 * 签名
	 * 
	 * @Title: addSignMD5
	 * @Description: MD5加签名
	 * @param @param sign_src
	 * @param @param md5_key
	 * @param @return
	 * @return String
	 * @throws
	 */
	private static String addSignMD5(String sign_src, String md5_key) {
		if (sign_src == null) {
			return "";
		}
		sign_src += "&key=" + md5_key;
		try {
			return Md5Algorithm.getInstance().md5Digest(
					sign_src.getBytes("utf-8"));
		} catch (Exception e) {
			System.out.println("MD5加签名异常" + e.getMessage());
			return "";
		}
	}
}
