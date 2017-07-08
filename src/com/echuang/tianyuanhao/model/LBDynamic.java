package com.echuang.tianyuanhao.model;

import java.util.List;

public class LBDynamic {

	public String Message;
	public List<DynamicItem> Result;
	public String Success;

	public class DynamicItem {

		public String d_id;
		public String d_img;
		public String d_text;
		public String d_time;
		public String d_title;
	}

}
