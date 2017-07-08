package com.echuang.tianyuanhao.fragment;

import java.io.File;
import java.io.IOException;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.echuang.tianyuanhao.R;
import com.echuang.tianyuanhao.activity.AssetsRankActivity;
import com.echuang.tianyuanhao.activity.MyStoreUpActivity;
import com.echuang.tianyuanhao.activity.SystemSetActivity;
import com.echuang.tianyuanhao.activity.my.MyDealDetailsActivity;
import com.echuang.tianyuanhao.activity.my.MyDealStateActivity;
import com.echuang.tianyuanhao.activity.my.MyWithdrawActivity;
import com.echuang.tianyuanhao.activity.my.PerfectDataActivity;
import com.echuang.tianyuanhao.base.BaseFragment;
import com.echuang.tianyuanhao.dialog.SelectDialog;
import com.echuang.tianyuanhao.dialog.TipsDialog;
import com.echuang.tianyuanhao.instance.LBInstance;
import com.echuang.tianyuanhao.instance.LBUrls;
import com.echuang.tianyuanhao.model.LBMainAssetsModel;
import com.echuang.tianyuanhao.model.LBMyFragmentModel;
import com.echuang.tianyuanhao.model.LBRecycleViewPagerImg;
import com.echuang.tianyuanhao.utils.ImageLoaderHelper;
import com.echuang.tianyuanhao.utils.SPUtils;
import com.echuang.tianyuanhao.utils.StrRes;
import com.echuang.tianyuanhao.view.CircleTransform;
import com.google.gson.Gson;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
/**
 * 我的账户
 * 
 * @ClassName: MyFragment
 * @Description: TODO
 * @author 蒲江
 * @date 2016-7-12 下午5:27:17
 */
public class MyFragment extends BaseFragment implements OnClickListener {
	private View parentView;
	private LinearLayout mHouseDeal, mIdentityAuth, mFileData, mMystoreUp,
			mInviteFriend, mVipLine, mSystemSet, layout_deal_state,
			layout_perfect_data;
	private ImageView mAssetsRanking, mMyAvatar;
	private TextView mMyName, mMyAssets;
	private Button btn_tx, btn_mx;

	private TipsDialog mTipsDialog;// 提示对话框
	private SelectDialog mSelectDialog;// 选择框

	// 头像更新参数
	private final static int PICK_FROM_CAMERA = 2;
	private final static int AVATAR_WIDTH = 512; // 头像截图的长
	private final static int AVATAR_HEIGHT = 512; // 头像截图的高
	protected static final int MY_FRAGMENT = 0;
	private Uri mImageCaptureUri; // 照相照片临时存储的地方

	// 网络请求处理
	//头像的地址
	protected static final String HEAD_IMG_URL = "http://tianyuanhao.oss-cn-shanghai.aliyuncs.com/img/";//还要加上用户信息里的head参数
	private Handler mhandler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case MY_FRAGMENT:
					LBMyFragmentModel myFragmentModel = (LBMyFragmentModel) msg.obj;
					System.out.print("handleMessage==============:"+HEAD_IMG_URL+SPUtils.getString(getActivity(), "user_head"));
					if (!SPUtils.getString(getActivity(), "user_head").isEmpty()) {
						ImageLoaderHelper.displayImage(HEAD_IMG_URL+SPUtils.getString(getActivity(), "user_head")
								, mMyAvatar);
//						Picasso.with(getActivity()).load(HEAD_IMG_URL+SPUtils.getString(getActivity(), "user_head")).transform(new CircleTransform())
//								.into(mMyAvatar);
						
					}
					//Picasso.with(getActivity()).
					
					mMyAssets.setText(myFragmentModel.Result.personal);
					
				break;
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		parentView = inflater.inflate(R.layout.fr_my, container, false);
		initView();
		//加载网络数据 
		loadDataFromNet();
		return parentView;

	}

	/**
	 * 组件初始化
	 * <p>
	 * Title: initView
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @see com.echuang.tianyuanhao.base.BaseFragment#initView()
	 */
	@Override
	protected void initView() {

		mHouseDeal = (LinearLayout) parentView
				.findViewById(R.id.layout_house_deal);
		mIdentityAuth = (LinearLayout) parentView
				.findViewById(R.id.layout_identity_auth);
		mFileData = (LinearLayout) parentView
				.findViewById(R.id.layout_file_data);
		mMystoreUp = (LinearLayout) parentView
				.findViewById(R.id.layout_mystore_up);
		mInviteFriend = (LinearLayout) parentView
				.findViewById(R.id.layout_invite_friend);
		mVipLine = (LinearLayout) parentView.findViewById(R.id.layout_vip_line);
		mSystemSet = (LinearLayout) parentView
				.findViewById(R.id.layout_system_set);
		layout_deal_state = (LinearLayout) parentView
				.findViewById(R.id.layout_deal_state);
		layout_perfect_data = (LinearLayout) parentView
				.findViewById(R.id.layout_perfect_data);

		mAssetsRanking = (ImageView) parentView
				.findViewById(R.id.igv_assets_ranking);
		//头像
		mMyAvatar = (ImageView) parentView.findViewById(R.id.igv_myAvatar);
		// 用户名 在登陆界面存储的
		mMyName = (TextView) parentView.findViewById(R.id.tv_myName);
		mMyName.setText(SPUtils.getString(getActivity(), "user_name"));
		// 账户余额
		mMyAssets = (TextView) parentView.findViewById(R.id.tv_myAssets);


		btn_tx = (Button) parentView.findViewById(R.id.btn_tx);
		btn_mx = (Button) parentView.findViewById(R.id.btn_mx);

		mHouseDeal.setOnClickListener(this);
		mIdentityAuth.setOnClickListener(this);
		mFileData.setOnClickListener(this);
		mMystoreUp.setOnClickListener(this);
		mInviteFriend.setOnClickListener(this);
		mVipLine.setOnClickListener(this);
		mSystemSet.setOnClickListener(this);
		mAssetsRanking.setOnClickListener(this);
		mMyName.setOnClickListener(this);
		mMyAssets.setOnClickListener(this);
		mMyAvatar.setOnClickListener(this);
		btn_tx.setOnClickListener(this);
		btn_mx.setOnClickListener(this);
		layout_deal_state.setOnClickListener(this);
		layout_perfect_data.setOnClickListener(this);
	}

	/**
	 * 事件处理
	 * <p>
	 * Title: onClick
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param v
	 * @see com.echuang.tianyuanhao.base.BaseFragment#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_tx:// 提现
			startActivity(new Intent(getActivity(), MyWithdrawActivity.class));
			break;
		case R.id.btn_mx:// 明细
			startActivity(new Intent(getActivity(), MyDealDetailsActivity.class));
			break;
		case R.id.layout_deal_state:// 交易状态
			startActivity(new Intent(getActivity(), MyDealStateActivity.class));
			break;
		case R.id.layout_perfect_data:// 完善资料-身份认证
			startActivity(new Intent(getActivity(), PerfectDataActivity.class));
			break;
		case R.id.layout_house_deal:// 房屋交易
			break;
		case R.id.layout_mystore_up:// 我的收藏
			startActivity(new Intent(getActivity(), MyStoreUpActivity.class)
					.putExtra(StrRes.title, "我的收藏").putExtra(StrRes.content,
							"我的收藏"));
			break;
		case R.id.layout_vip_line:// vip专线
			mTipsDialog = new TipsDialog(getActivity(), "贵宾专线", "400-888-8888",
					"取消", "拨打", new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							mTipsDialog.dismiss();
							Uri uri = Uri.parse("tel:400-888-8888");
							Intent intent = new Intent(Intent.ACTION_DIAL, uri);
							// intent.setAction("android.intent.action.CALL");//
							// 添加拨号不提示系统界面
							startActivity(intent);
						}
					});
			mTipsDialog.show();
			break;
		case R.id.layout_system_set:// 系统设置
			startActivity(new Intent(getActivity(), SystemSetActivity.class));
			break;
		case R.id.igv_myAvatar:// 我的头像
			mSelectDialog = new SelectDialog(getActivity());
			mSelectDialog.show();
			mSelectDialog
					.setCancelGallaryOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							mSelectDialog.dismiss();
						}
					});

			mSelectDialog
					.setOpenCameraOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							takeCamera();
							mSelectDialog.dismiss();
						}
					});

			mSelectDialog
					.setEditorGallaryOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent intent = new Intent(
									Intent.ACTION_PICK,
									android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
							getActivity().startActivityForResult(intent, 1);
							mSelectDialog.dismiss();
						}
					});
			break;
		case R.id.igv_assets_ranking:// 收益排行榜
			startActivity(new Intent(getActivity(), AssetsRankActivity.class));
			break;
		case R.id.layout_identity_auth:// 身份认证
			break;
		case R.id.layout_file_data:// 文件资料
			break;
		case R.id.layout_invite_friend:// 邀请好友
			break;
		case R.id.tv_myAssets:// 我的账户
			break;
		case R.id.tv_myName:// 账号名字
			break;
		default:
			break;
		}
	}

	protected void initEvents() {
	}

	@Override
	protected void back() {
	}

	@Override
	protected void next() {
	}

	/*** 从照相机拍摄照片 **/
	private void takeCamera() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		mImageCaptureUri = Uri.fromFile(new File(Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
				"tmp_camera_crop_" + String.valueOf(System.currentTimeMillis())
						+ ".jpg"));
		intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
				mImageCaptureUri);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, PICK_FROM_CAMERA);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void loadDataFromNet() {
		String myFragmentUrl = LBUrls.MY_FRAGMENT;
		String url = myFragmentUrl
				+ SPUtils.getString(getActivity(), "user_id");
		System.out.print("我的界面的网络地址："+url);
		//----------------------------分割线------------------------------
		
		
		
		
		//----------------------------分割线------------------------------
		// 创建okHttpClient对象
		OkHttpClient mOkHttpClient = new OkHttpClient();
		// 创建一个Request
		final Request request = new Request.Builder().url(url).build();
		// new call
		Call call = mOkHttpClient.newCall(request);
		// 请求加入调度
		call.enqueue(new Callback() {

		

			@Override
			public void onFailure(Request arg0, IOException arg1) {
				System.out.print("onFailure我的界面："+arg1);
				
			}

			@Override
			public void onResponse(Response response) throws IOException {
				String roomMessage = response.body().string();
				// final LBMainAssetsModel goodonedata1 =
				// JSON.parseObject(roomMessage, LBMainAssetsModel.class);
				System.out.print("onResponse我的界面："+roomMessage);
				LBMyFragmentModel myFragmentModel = new Gson().fromJson(
						roomMessage, LBMyFragmentModel.class);
				System.out.print("onResponse我的界面："+myFragmentModel.Result.personal);
				Message msg = mhandler.obtainMessage();
				msg.what = MY_FRAGMENT;
				msg.obj = myFragmentModel;
				mhandler.sendMessage(msg);
				
			}

		
		});

	}

}
