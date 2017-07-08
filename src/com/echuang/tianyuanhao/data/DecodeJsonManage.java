package com.echuang.tianyuanhao.data;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.echuang.tianyuanhao.model.DynamicModel;
import com.echuang.tianyuanhao.model.EarningModel;
import com.echuang.tianyuanhao.model.ErrorMsgModel;
import com.echuang.tianyuanhao.model.MainAssetsModel;
import com.echuang.tianyuanhao.utils.StrRes;
import com.google.gson.Gson;

/**
 * 数据处理类
 * 
 * @ClassName: DecodeJsonManage
 * @Description: TODO
 * @author 蒲江
 * @date 2016-7-12 下午2:29:36
 */
public class DecodeJsonManage {

	/**
	 * 首页数据解析
	 * 
	 * @Title: getMainAssetsList
	 * @Description: TODO
	 * @param @param msg
	 * @param @param datas
	 * @param @return
	 * @return List<MainAssetsModel>
	 * @throws
	 */
	public static List<MainAssetsModel> getMainAssetsList(String msg,
			List<MainAssetsModel> datas) {
		if (null == datas) {
			datas = new ArrayList<MainAssetsModel>();
		}
		try {
			JSONObject object = new JSONObject(msg);
			JSONObject object2 = object.optJSONObject(StrRes.map);
			JSONObject object3 = object2.optJSONObject(StrRes.pageInfo);
			JSONArray array = object3.optJSONArray(StrRes.rows);
			Gson gson = new Gson();
			if (null != array) {
				for (int i = 0; i < array.length(); i++) {
					JSONObject jsonObject = array.optJSONObject(i);
					MainAssetsModel data = gson.fromJson(jsonObject.toString(),
							MainAssetsModel.class);
					datas.add(data);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return datas;
	}

	/**
	 * 收益数据解析
	 * 
	 * @Title: getNewsList
	 * @Description: TODO
	 * @param @param msg
	 * @param @param datas
	 * @param @return
	 * @return List<EarningModel>
	 * @throws
	 */
	public static List<EarningModel> getNewsList(String msg,
			List<EarningModel> datas) {
		if (null == datas) {
			datas = new ArrayList<EarningModel>();
		}
		try {
			JSONObject object = new JSONObject(msg);
			JSONObject object2 = object.optJSONObject(StrRes.map);
			JSONObject object3 = object2.optJSONObject(StrRes.pageInfo);
			JSONArray array = object3.optJSONArray(StrRes.rows);
			Gson gson = new Gson();
			if (null != array) {
				for (int i = 0; i < array.length(); i++) {
					JSONObject jsonObject = array.optJSONObject(i);
					EarningModel data = gson.fromJson(jsonObject.toString(),
							EarningModel.class);
					datas.add(data);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return datas;
	}
	
	/**
	 * 动态数据解析
	 * 
	 * @Title: getNewsList
	 * @Description: TODO
	 * @param @param msg
	 * @param @param datas
	 * @param @return
	 * @return List<EarningModel>
	 * @throws
	 */
	public static List<DynamicModel> getDynamicModelList(String msg,
			List<DynamicModel> datas) {
		if (null == datas) {
			datas = new ArrayList<DynamicModel>();
		}
		try {
			JSONObject object = new JSONObject(msg);
			JSONObject object2 = object.optJSONObject(StrRes.map);
			JSONObject object3 = object2.optJSONObject(StrRes.pageInfo);
			JSONArray array = object3.optJSONArray(StrRes.rows);
			Gson gson = new Gson();
			if (null != array) {
				for (int i = 0; i < array.length(); i++) {
					JSONObject jsonObject = array.optJSONObject(i);
					DynamicModel data = gson.fromJson(jsonObject.toString(),
							DynamicModel.class);
					datas.add(data);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return datas;
	}

	/**
	 * 是否成功
	 * 
	 * @Title: isSuccess
	 * @Description: TODO
	 * @param @param msg
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public static boolean isSuccess(String msg) {
		try {
			JSONObject object = new JSONObject(msg);

			return object.optBoolean(StrRes.success, false);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 是否有下一页
	 * 
	 * @Title: isHasNextPage
	 * @Description: TODO
	 * @param @param reponse
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public static boolean isHasNextPage(String reponse) {
		try {
			JSONObject object = new JSONObject(reponse);
			JSONObject object2 = object.optJSONObject(StrRes.map);
			JSONObject object3 = object2.optJSONObject(StrRes.pageInfo);
			int page = object3.optInt(StrRes.pageOn);
			int totalpage = object3.optInt(StrRes.totalPage);
			return page < totalpage;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 是否有下一页
	 * 
	 * @Title: isHasNextPage2
	 * @Description: TODO
	 * @param @param reponse
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public static boolean isHasNextPage2(String reponse) {
		try {
			JSONObject object = new JSONObject(reponse);
			JSONObject object2 = object.optJSONObject(StrRes.map);
			JSONObject object3 = object2.optJSONObject(StrRes.page);
			int page = object3.optInt(StrRes.pageOn);
			int totalpage = object3.optInt(StrRes.totalPage);
			return page < totalpage;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 错误：提示数据
	 * 
	 * @Title: getErrorMsgData
	 * @Description: TODO
	 * @param @param msg
	 * @param @return
	 * @return ErrorMsgModel
	 * @throws
	 */
	public static ErrorMsgModel getErrorMsgData(String msg) {
		ErrorMsgModel errorMsgData = new ErrorMsgModel();
		try {
			JSONObject object = new JSONObject(msg);
			errorMsgData.setErrorCode(object.optString(StrRes.errorCode));
			errorMsgData.setErrorMsg(object.optString(StrRes.errorMsg));
			errorMsgData.setMsg(object.optString(StrRes.msg));
			errorMsgData.setMsg(object.optString(StrRes.msg));
			errorMsgData.setSuccess(object.optBoolean(StrRes.success, false));
			errorMsgData
					.setSuccess(object.optBoolean(StrRes.isPayPwdSet, true));
			return errorMsgData;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return errorMsgData;
	}

	/**
	 * 错误：提示
	 * 
	 * @Title: getErrorMsg
	 * @Description: TODO
	 * @param @param msg
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getErrorMsg(String msg) {
		ErrorMsgModel errorMsgData = new ErrorMsgModel();
		try {
			JSONObject object = new JSONObject(msg);
			errorMsgData.setErrorCode(object.optString(StrRes.errorCode));
			errorMsgData.setErrorMsg(object.optString(StrRes.errorMsg));
			errorMsgData.setMsg(object.optString(StrRes.msg));
			errorMsgData.setSuccess(object.optBoolean(StrRes.success, false));
			return errorMsgData.getErrorMsg();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return errorMsgData.getErrorMsg();
	}

	/**
	 * 提示
	 * 
	 * @Title: getMsg
	 * @Description: TODO
	 * @param @param msg
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getMsg(String msg) {
		ErrorMsgModel errorMsgData = new ErrorMsgModel();
		try {
			JSONObject object = new JSONObject(msg);
			errorMsgData.setMsg(object.optString(StrRes.msg));
			errorMsgData.setSuccess(object.optBoolean(StrRes.success, false));
			return errorMsgData.getMsg();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
