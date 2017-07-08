package com.echuang.tianyuanhao.activity.earning;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.echuang.tianyuanhao.R;

/**
 * @Date 2016.9.10
 * @author Libao 租客管理页面
 */
public class RenterManagerActivity extends Activity implements OnClickListener {

	private ImageButton ivBack;
	private TextView tvTitle;
	private MapView mMapView = null;
	BaiduMap mBaiduMap;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//在使用SDK各组件之前初始化context信息，传入ApplicationContext  
        //注意该方法要再setContentView方法之前实现  
        SDKInitializer.initialize(getApplicationContext()); 
		setContentView(R.layout.activity_renter_manager);

		initView();
		init();
	}
	private void init() {
        //1.单例   2.工厂  3.构造器
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.overlook(-45);
        builder.rotate(-30);
        builder.zoom(18);
        builder.target(new LatLng(31.2428680000,121.4905840000));
        //31.2428680000,121.4905840000
        MapStatus mapStatus = builder.build();
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);
        mBaiduMap.setMapStatus(mapStatusUpdate);
    }

	private void initView() {
		mMapView = (MapView) findViewById(R.id.bmapView1);
		mBaiduMap = mMapView.getMap();
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		hideZoomView(mMapView);
		//百度地图的点击事件
		mBaiduMap.setOnMapClickListener(new OnMapClickListener() {
			
			@Override
			public boolean onMapPoiClick(MapPoi arg0) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void onMapClick(LatLng arg0) {
				Toast.makeText(RenterManagerActivity.this, "我点击了百度地图", Toast.LENGTH_SHORT).show();
				
			}
		});
		
		
		
		ivBack = (ImageButton) findViewById(R.id.imageButton_left);
		ivBack.setOnClickListener(this);
		tvTitle = (TextView) findViewById(R.id.textView_title);
		tvTitle.setText("租客管理");
	}
	//百度地图生命周期
	@Override  
    protected void onDestroy() {  
        super.onDestroy();  
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理  
        mMapView.onDestroy();  
    }  
    @Override  
    protected void onResume() {  
        super.onResume();  
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理  
        mMapView.onResume();  
        }  
    @Override  
    protected void onPause() {  
        super.onPause();  
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理  
        mMapView.onPause();  
        } 

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.imageButton_left:
			back();
			break;

		case R.id.imageButton_right:
		case R.id.button_right:
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			back();
		}
		return super.onKeyDown(keyCode, event);
	}

	protected void back() {
		finish();
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}
	
	//---------------------分隔线----------------------
	/**	 
	 * 
	 * 隐藏缩放控件	 
	 * @param mapView	 
	 */	
	private void hideZoomView(MapView mapView) {
		//mBaiduMap = mapView.getMap();		
		// 隐藏缩放控件	
		int childCount = mapView.getChildCount();
		View zoom = null;		
		for (int i = 0; i < childCount; i++) {			
			View child = mapView.getChildAt(i);			
			if (child instanceof ZoomControls) {				
				zoom = child;				
				break;			
				}		
			}		
		zoom.setVisibility(View.GONE);
		}
	}


