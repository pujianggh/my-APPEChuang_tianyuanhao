package com.echuang.tianyuanhao.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wuxi-pc on 2016/9/18.
 */
public class LBMainAssetsModel {
	public String Message;
	public List<Resultdata> Result;
	public String Success;

	public class Resultdata implements Serializable {
		public String rid;
		public String room_acreage;
		public String room_acreages;
		public String room_address;
		public String room_all;
		public String room_bath;
		public String room_bedroom;
		public String room_berth;
		public String room_confirm;
		public String room_country;
		public String room_img;
		public String room_lpush;
		public String room_pin;
		public String room_pins;
		public String room_rent;
		public String room_rents;
		public String room_return;
		public String room_returns;
		public String room_state;
		public String room_value;
		public String room_years;

	}
}
