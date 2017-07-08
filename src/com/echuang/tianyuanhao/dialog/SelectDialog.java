package com.echuang.tianyuanhao.dialog;

import com.echuang.tianyuanhao.R;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

/**
 * 选择对话框
 * @ClassName: AvataEditorDialog
 * @Description: TODO
 * @author 蒲江
 * @date 2016-8-30 下午4:14:40
 */
public class SelectDialog extends AlertDialog {
    private Context context;
    private TextView openCamera;//打开摄像头
    private TextView editorGallary;//打开相册
    private TextView cancel;//取消

    public SelectDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_select);
        setCanceledOnTouchOutside(true);
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setWindowAnimations(R.style.avatar_editor_dialog);

        openCamera = (TextView) findViewById(R.id.popup_avatar_editor_gallary);
        editorGallary = (TextView) findViewById(R.id.popup_avatar_editor_camera);
        cancel = (TextView) findViewById(R.id.popup_avatar_editor_cancel);

    }

    public void setOpenCameraOnClickListener(View.OnClickListener okOnClickListener) {
        if (null != okOnClickListener){
            openCamera.setOnClickListener(okOnClickListener);
        }
    }

    public void setEditorGallaryOnClickListener(View.OnClickListener closeOnClickListener){
        if (null != closeOnClickListener){
            editorGallary.setOnClickListener(closeOnClickListener);
        }
    }

    public void setCancelGallaryOnClickListener(View.OnClickListener closeOnClickListener){
        if (null != closeOnClickListener){
            cancel.setOnClickListener(closeOnClickListener);
        }
    }


}
