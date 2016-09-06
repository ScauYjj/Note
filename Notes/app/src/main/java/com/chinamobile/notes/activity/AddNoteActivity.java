package com.chinamobile.notes.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chinamobile.notes.R;
import com.chinamobile.notes.adapter.CommonPopWindowListAdapter;
import com.chinamobile.notes.entity.Note;
import com.chinamobile.notes.utils.DateUtil;
import com.chinamobile.notes.utils.InputUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AddNoteActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivBack;
    private EditText etTitle;
    private TextView tvAdd;
    private EditText etContent;
    private LinearLayout llRemind;
    private LinearLayout llFavor;
    private LinearLayout llList;
    private LinearLayout llVoice;
    private ImageView ivRemind;
    private ImageView ivFavor;
    private ImageView ivList;
    private ImageView ivVoice;
    private RelativeLayout rlVoice;
    private ImageView btnVoicePress;
    private TextView tv_press_tip;
    private FrameLayout contentView;
    private PopupWindow popupWindow;
    // 获取手机屏幕分辨率的类
    private DisplayMetrics dm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        initView();
    }

    private void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        etTitle = (EditText) findViewById(R.id.et_title);
        tvAdd = (TextView) findViewById(R.id.tv_add);
        etContent = (EditText) findViewById(R.id.et_content);
        llRemind = (LinearLayout) findViewById(R.id.ll_remind);
        llFavor = (LinearLayout) findViewById(R.id.ll_favor);
        llList = (LinearLayout) findViewById(R.id.ll_list);
        llVoice = (LinearLayout) findViewById(R.id.ll_voice);
        ivRemind = (ImageView) findViewById(R.id.iv_remind);
        ivFavor = (ImageView) findViewById(R.id.iv_favor);
        ivList = (ImageView) findViewById(R.id.iv_list);
        ivVoice = (ImageView) findViewById(R.id.iv_voice);
        rlVoice = (RelativeLayout) findViewById(R.id.rl_voice);
        btnVoicePress = (ImageView) findViewById(R.id.btn_voice_press);
        tv_press_tip = (TextView) findViewById(R.id.tv_press_tip);

        ivBack.setOnClickListener(this);
        tvAdd.setOnClickListener(this);
        etContent.setOnClickListener(this);
        llRemind.setOnClickListener(this);
        llFavor.setOnClickListener(this);
        llList.setOnClickListener(this);
        llVoice.setOnClickListener(this);
        btnVoicePress.setOnClickListener(this);

        btnVoicePress.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        //btnVoicePress.setBackgroundResource(R.drawable.voice_press2);
                        btnVoicePress.setImageResource(R.drawable.voice_press2);
                        tv_press_tip.setText("松开结束");
                        break;
                    case MotionEvent.ACTION_UP:
                        //btnVoicePress.setBackgroundResource(R.drawable.voicepress1);
                        btnVoicePress.setImageResource(R.drawable.voicepress1);
                        tv_press_tip.setText("按住说话");
                        break;
                }
                return true;
            }
        });

    }

    private boolean tag1 = false;
    private boolean tag2 = false;
    private boolean tag3 = false;
    private boolean tag4 = false;

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
            break;
            case R.id.tv_add:
                add();
                break;
            case R.id.ll_remind:
                if(!tag1){
                    ivRemind.setImageResource(R.mipmap.remind1);
                    tag1 = !tag1;
                }else{
                    ivRemind.setImageResource(R.mipmap.remind);
                    tag1 = !tag1;
                }
                break;
            case R.id.ll_favor:
                if(!tag2){
                    ivFavor.setImageResource(R.mipmap.favor_fill);
                    tag2 = !tag2;
                }else{
                    ivFavor.setImageResource(R.mipmap.favor);
                    tag2 = !tag2;
                }
                break;
            case R.id.ll_list:
               /* if(!tag3){
                    ivList.setImageResource(R.mipmap.list1);
                    tag3 = !tag3;
                }else{
                    ivList.setImageResource(R.mipmap.list);
                    tag3 = !tag3;
                }
                break;*/
                InputUtil.HideKeyboard(llList);
                getPopupWindow();
                break;
            case R.id.ll_voice:
                if(!tag4){
                    InputUtil.HideKeyboard(rlVoice);
                    ivVoice.setImageResource(R.mipmap.keyboard);
                    rlVoice.setVisibility(View.VISIBLE);
                    tag4 = !tag4;
                }else{
                    ivVoice.setImageResource(R.mipmap.voice);
                    rlVoice.setVisibility(View.GONE);
                    InputUtil.showSoftInput(etContent,tag4);
                    tag4 = !tag4;
                }
                break;
            case R.id.ll_back:
                if (popupWindow.isShowing()){
                    popupWindow.dismiss();
                }
                break;

        }
    }

    /**
     *判断内容并提交
     */
    private void add() {
        String title = etTitle.getText().toString();
        if (title.equals("")){
            Toast.makeText(AddNoteActivity.this,"请输入标题",Toast.LENGTH_SHORT).show();
            return;
        }
        String currentTime = DateUtil.getCurrentTime();
        Note note = new Note();
        note.setTitle(title);
        note.setCurrentTime(currentTime);
        //note.setTag("已完成");
        Date date = DateUtil.string2Date(note.getCurrentTime(),"yyyy-MM-dd");
        note.setTag(DateUtil.FriendlyDate(date));
        //数据是使用Intent返回
        Intent intent = new Intent();
        //把返回数据存入Intent
        intent.putExtra("note",note);
        setResult(RESULT_OK,intent);
        finish();
    }


    private void getPopupWindow(){
        if (contentView == null){
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            contentView = (FrameLayout) LayoutInflater.from(this).
                    inflate(R.layout.list_popupwindow_layout,null);
            dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            // 创建一个PopuWidow对象
            popupWindow = new PopupWindow(contentView, dm.widthPixels,
                    LinearLayout.LayoutParams.MATCH_PARENT);
        }
        View rootview = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        // 使其聚集 ，要想监听菜单里控件的事件就必须要调用此方法
        popupWindow.setFocusable(true);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);
        // 设置背景，这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        // PopupWindow的显示及位置设置
        popupWindow.showAtLocation(rootview, Gravity.FILL, 0, 0);

        LinearLayout backll=(LinearLayout)contentView.findViewById(R.id.ll_back);
        ListView listView=(ListView)contentView.findViewById(R.id.family_list);

        backll.setOnClickListener(this);

        final List<String> listems = new ArrayList<String>();
        listems.add("收集箱");
        listems.add("学习");
        listems.add("工作");
        listems.add("娱乐");
        listems.add("其他");
        listView.setAdapter(new CommonPopWindowListAdapter(AddNoteActivity.this, listems));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent0, View view, int position, long id) {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
            }
        });
    }
}
