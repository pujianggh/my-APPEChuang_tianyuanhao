package com.echuang.tianyuanhao.model;

import java.io.Serializable;
import java.util.List;

public class LBRecycleViewPagerImg {
	public String Message;
	public List<RecycleImgUrl> Result;
	public int Success;

	public class RecycleImgUrl implements Serializable{
		public String c_states;
		public String img;
		public String url;
		

	}

}
