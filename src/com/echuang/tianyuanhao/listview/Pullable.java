package com.echuang.tianyuanhao.listview;

/**
 * 下拉动作接口
 * 
 * @ClassName: Pullable
 * @Description: TODO
 * @author 蒲江
 * @date 2016-7-11 上午11:29:58
 */
public interface Pullable {
	
	/**
	 * 判断是否可以下拉，如果不需要下拉功能可以直接return false
	 * 
	 * @Title: canPullDown
	 * @Description: TODO true如果可以下拉否则返回false
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	boolean canPullDown();
}
