package com.echuang.tianyuanhao.model;

import java.util.List;


public class LBDealStateModel {

	public String Message;
	public List<HouseMessage> Result;
	public int Success;

	public class HouseMessage {
		public String assets_tempo;
		public String rid;
		public String room_address;
		public String room_img;

	}

}
