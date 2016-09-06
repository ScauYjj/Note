package com.chinamobile.notes.activity;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.chinamobile.notes.R;
import com.chinamobile.notes.utils.ScreenUtil;

/**
 * Created by yjj on 2016/8/22.
 */
public class BaseActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    /**
     * 开启沉浸式状态栏
     * @param view
     */
    public void setImmerseLayout(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            int statusBarHeight = ScreenUtil.getStatusBarHeight(this
                    .getBaseContext());
            view.setPadding(0, statusBarHeight, 0, 0);
        }
    }
}
