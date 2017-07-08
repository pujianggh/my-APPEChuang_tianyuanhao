package com.echuang.tianyuanhao.model;

/**
 * 动态信息
 * 
 * @ClassName: DynamicModel
 * @Description: TODO
 * @author 蒲江
 * @date 2016-8-26 上午11:32:33
 */
public class DynamicModel {
	private String id = "";//
	private String title = "";//
	private String time = "";//
	private String url = "";//

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
