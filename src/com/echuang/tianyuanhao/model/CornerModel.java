package com.echuang.tianyuanhao.model;

/**
 * 数据配置
 * 
 * @ClassName: TopCornerModel
 * @Description: TODO
 * @author 蒲江
 * @date 2016-9-1 上午11:35:58
 */
public class CornerModel {

	private String content = "";
	private String id = "";
	private int flag = 0;// 标记升序或者降序 默认0:降序 1：升序

	public CornerModel(String content, String id) {
		super();
		this.content = content;
		this.id = id;
	}

	public CornerModel(String content, String id, int flag) {
		super();
		this.content = content;
		this.id = id;
		this.flag = flag;
	}

	public CornerModel(String content) {
		super();
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public CornerModel setContent(String content) {
		this.content = content;
		return this;
	}

	public String getId() {
		return id;
	}

	public CornerModel setId(String id) {
		this.id = id;
		return this;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
}
