package com.echuang.tianyuanhao.adapter;

import java.util.List;

import javax.crypto.spec.IvParameterSpec;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.echuang.tianyuanhao.R;
import com.echuang.tianyuanhao.activity.MyStoreUpActivity;

public class NewMainAssetsListAdapter extends BaseExpandableListAdapter{
	
	private Context mContext;
	List<String> group_list;
	List<String> item_lt;
	

	public NewMainAssetsListAdapter(MyStoreUpActivity activity) {
		mContext = activity;
	}
	
	 /**
    *
    * 获取组的个数
    *
    * @return
    * @see android.widget.ExpandableListAdapter#getGroupCount()
    */
   @Override
   public int getGroupCount()
   {
       return 12;
   }

   /**
    *
    * 获取指定组中的子元素个数
    *
    * @param groupPosition
    * @return
    * @see android.widget.ExpandableListAdapter#getChildrenCount(int)
    */
   @Override
   public int getChildrenCount(int groupPosition)
   {
       return 3;
   }

   /**
    *
    * 获取指定组中的数据
    *
    * @param groupPosition
    * @return
    * @see android.widget.ExpandableListAdapter#getGroup(int)
    */
   /*@Override
   public Object getGroup(int groupPosition)
   {
       return group_list.get(groupPosition);
   }
*/
   /**
    *
    * 获取指定组中的指定子元素数据。
    *
    * @param groupPosition
    * @param childPosition
    * @return
    * @see android.widget.ExpandableListAdapter#getChild(int, int)
    */
   /*@Override
   public Object getChild(int groupPosition, int childPosition)
   {
       return item_lt.get(childPosition);
   }*/

   /**
    *
    * 获取指定组的ID，这个组ID必须是唯一的
    *
    * @param groupPosition
    * @return
    * @see android.widget.ExpandableListAdapter#getGroupId(int)
    */
   @Override
   public long getGroupId(int groupPosition)
   {
       return groupPosition;
   }

   /**
    *
    * 获取指定组中的指定子元素ID
    *
    * @param groupPosition
    * @param childPosition
    * @return
    * @see android.widget.ExpandableListAdapter#getChildId(int, int)
    */
   @Override
   public long getChildId(int groupPosition, int childPosition)
   {
       return childPosition;
   }

   /**
    *
    * 组和子元素是否持有稳定的ID,也就是底层数据的改变不会影响到它们。
    *
    * @return
    * @see android.widget.ExpandableListAdapter#hasStableIds()
    */
   @Override
   public boolean hasStableIds()
   {
       return true;
   }

   /**
    *
    * 获取显示指定组的视图对象
    *
    * @param groupPosition 组位置
    * @param isExpanded 该组是展开状态还是伸缩状态
    * @param convertView 重用已有的视图对象
    * @param parent 返回的视图对象始终依附于的视图组
    * @return
    * @see android.widget.ExpandableListAdapter#getGroupView(int, boolean, android.view.View,
    *      android.view.ViewGroup)
    */
   @Override
   public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
   {
       GroupHolder groupHolder = null;
       if (convertView == null)
       {
           convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_assets_group, null);
           groupHolder = new GroupHolder();
           groupHolder.txt = (TextView)convertView.findViewById(R.id.tv_money);
           groupHolder.img = (ImageView)convertView.findViewById(R.id.iv_arrow_down);
           convertView.setTag(groupHolder);
       }
       else
       {
           groupHolder = (GroupHolder)convertView.getTag();
       }
  
       
       
       
       
       if (isExpanded) {
    	   groupHolder.img.setImageResource(R.drawable.group);
		}else {
			groupHolder.img.setImageResource(R.drawable.ic_arrow_to_below);
		}
       

       return convertView;
   }

   /**
    *
    * 获取一个视图对象，显示指定组中的指定子元素数据。
    *
    * @param groupPosition 组位置
    * @param childPosition 子元素位置
    * @param isLastChild 子元素是否处于组中的最后一个
    * @param convertView 重用已有的视图(View)对象
    * @param parent 返回的视图(View)对象始终依附于的视图组
    * @return
    * @see android.widget.ExpandableListAdapter#getChildView(int, int, boolean, android.view.View,
    *      android.view.ViewGroup)
    */
   @Override
   public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
   {
       ItemHolder itemHolder = null;
       if (convertView == null)
       {
           convertView = LayoutInflater.from(mContext).inflate(R.layout.expendlist_item, null);
           itemHolder = new ItemHolder();
           itemHolder.txt = (TextView)convertView.findViewById(R.id.tv_money_two);
           convertView.setTag(itemHolder);
       }
       else
       {
           itemHolder = (ItemHolder)convertView.getTag();
       }

       return convertView;
   }

   /**
    *
    * 是否选中指定位置上的子元素。
    *
    * @param groupPosition
    * @param childPosition
    * @return
    * @see android.widget.ExpandableListAdapter#isChildSelectable(int, int)
    */
   @Override
   public boolean isChildSelectable(int groupPosition, int childPosition)
   {
       return true;
   }

@Override
public Object getGroup(int groupPosition) {
	// TODO Auto-generated method stub
	return groupPosition;
}

@Override
public Object getChild(int groupPosition, int childPosition) {
	// TODO Auto-generated method stub
	return childPosition;
}



}

class GroupHolder
{
   public TextView txt;
   public ImageView img;
}

class ItemHolder
{  
   public TextView txt;
}

	
	
	//R.layout.adapter_assets_group



