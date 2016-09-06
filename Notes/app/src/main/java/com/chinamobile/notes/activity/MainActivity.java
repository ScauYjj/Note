package com.chinamobile.notes.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chinamobile.notes.R;
import com.chinamobile.notes.adapter.MainListAdapter;
import com.chinamobile.notes.adapter.RecyclerAdapter;
import com.chinamobile.notes.entity.Note;
import com.chinamobile.notes.utils.DateUtil;
import com.github.jdsjlzx.interfaces.Closeable;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnSwipeMenuItemClickListener;
import com.github.jdsjlzx.interfaces.SwipeMenuCreator;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.swipe.SwipeMenu;
import com.github.jdsjlzx.swipe.SwipeMenuItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private Toolbar toolbar;
    private LRecyclerView mRecyclerView;
    private MainListAdapter adapter;
    private ArrayList<Note> notes;
   // private ImageView iv_add;
    private com.melnykov.fab.FloatingActionButton fab;
    private LinearLayout llHome;
    private LinearLayout llMail;
    private LinearLayout contentView;
    private LinearLayout llMsg;
    private LinearLayout llAdd;
    private LinearLayout llWeChat;
    private LinearLayout llEmail;
    private PopupWindow popupWindow;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initToolBar();
        initDrawerLayout();
        initData();
        initRecyclerView();
    }

    private void initView() {
        fab = (com.melnykov.fab.FloatingActionButton) findViewById(R.id.fab);
        llHome = (LinearLayout) findViewById(R.id.ll_home);
        llMail = (LinearLayout) findViewById(R.id.ll_email);
        llAdd = (LinearLayout) findViewById(R.id.ll_add);

        fab.setOnClickListener(this);
        llHome.setOnClickListener(this);
        llMail.setOnClickListener(this);
        llAdd.setOnClickListener(this);
    }

    /**
     * 初始化ToolBar
     */
    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //设置导航栏的图标
        toolbar.setNavigationIcon(R.mipmap.ic_drawer_home);
        toolbar.setTitle("收集箱");//设置主标题
        toolbar.setTitleTextAppearance(this, R.style.Theme_ToolBar_Base_Title);
        toolbar.inflateMenu(R.menu.base_toolbar_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuItemId = item.getItemId();
                switch (menuItemId){
                    case R.id.action_item1:
                        Toast.makeText(MainActivity.this,R.string.item_01,Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_item2:
                        Toast.makeText(MainActivity.this,R.string.item_02,Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_item3:
                        showPopupWindow();
                        break;
                }
                return true;
            }
        });
    }

    private void showPopupWindow(){
        if (popupWindow == null){
            contentView = (LinearLayout) LayoutInflater.from(this).
                    inflate(R.layout.share_popupwindow_layout,null);
            popupWindow = new PopupWindow(contentView,
                    RelativeLayout.LayoutParams.MATCH_PARENT, 400, true);
            popupWindow.setContentView(contentView);
            llMsg = (LinearLayout) contentView.findViewById(R.id.ll_msg);
            llWeChat = (LinearLayout) contentView.findViewById(R.id.ll_wechat);
            llEmail = (LinearLayout) contentView.findViewById(R.id.ll_mail);

            llMsg.setOnClickListener(this);
            llEmail.setOnClickListener(this);
            llWeChat.setOnClickListener(this);
        }
        View rootview = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_main, null);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setAnimationStyle(R.style.PopupAnimation);
        popupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 初始化DrawerLayout
     */
    private void initDrawerLayout() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close);
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
    }

    /**
     * 测试数据
     */
    private void initData() {
        notes = new ArrayList<>();
        Note note1 = new Note();
        note1.setCurrentTime("2016-08-20");
        note1.setTitle("易建联去了湖人");
        Date date1 = DateUtil.string2Date(note1.getCurrentTime(),"yyyy-MM-dd");
        note1.setTag(DateUtil.FriendlyDate(date1));
        notes.add(note1);

       /* Note note2 = new Note("冰冻三尺，非一日之寒","2016-08-24",null);
        Date date2 = DateUtil.string2Date(note2.getDate(),"yyyy-MM-dd");
        note2.setTag(DateUtil.FriendlyDate(date2));
        notes.add(note2);

        Note note3 = new Note("为山九仞,岂一日之功","2016-08-23",null);
        Date date3 = DateUtil.string2Date(note3.getDate(),"yyyy-MM-dd");
        note3.setTag(DateUtil.FriendlyDate(date3));
        notes.add(note3);

        Note note4 = new Note("正德厚生，臻于至善","2016-08-29",null);
        Date date4 = DateUtil.string2Date(note4.getDate(),"yyyy-MM-dd");
        note4.setTag(DateUtil.FriendlyDate(date4));
        notes.add(note4);*/
        sort(notes);
    }

    /**
     * 菜单创建器。在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {

            if(viewType == MainListAdapter.TYPE_NORMAL_ITEM) {
                int height = getResources().getDimensionPixelSize(R.dimen.item_height);
                int width = getResources().getDimensionPixelSize(R.dimen.item_width);
                int textSize = getResources().getDimensionPixelSize(R.dimen.item_text_size);
                SwipeMenuItem deleteItem = new SwipeMenuItem(MainActivity.this)
                        .setBackgroundDrawable(R.drawable.selector_red)
                        .setText("删除") // 文字，还可以设置文字颜色，大小等。。
                        .setTextSize(textSize)
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。
            }
        }
    };

    /**
     * 右滑菜单点击事件监听。
     */
    private OnSwipeMenuItemClickListener menuItemClickListener = new OnSwipeMenuItemClickListener() {

        @Override
        public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {
            closeable.smoothCloseMenu();// 关闭被点击的菜单。

            if (direction == LRecyclerView.RIGHT_DIRECTION) {
                Toast.makeText(MainActivity.this, "list第" + adapterPosition + "; 右侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            }
        }
    };


    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        mRecyclerView = (LRecyclerView) findViewById(R.id.main_list);
        fab.attachToRecyclerView(mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MainListAdapter(this,notes);
        mRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        mRecyclerView.setSwipeMenuItemClickListener(menuItemClickListener);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));// 布局管理器。
        //mRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加也行。
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(this, adapter);
        mRecyclerView.setAdapter(mLRecyclerViewAdapter);
        mRecyclerView.setPullRefreshEnabled(false);
        mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            private HashMap<Integer, Integer> map = adapter.getItemPositionMap();
            @Override
            public void onItemClick(View view, int position) {
                if(map.get(position) != null) {
                    String text = adapter.getDataList().get(map.get(position)).getContent();
                    Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {
                List<MainListAdapter.NormalItemHolder> list = adapter.getItemHolderList();
                for(int i = 0; i < list.size(); i++)
                    list.get(i).getCheckBox().setBackgroundResource(R.drawable.checkbox_round);
            }
        });
    }

    /**
     * 集合排序
     * @param notes
     */
    private void sort(ArrayList<Note> notes) {
        Collections.sort(notes, new Comparator<Note>() {
            @Override
            public int compare(Note o1, Note o2) {
                Note note1 = (Note) o1;
                Note note2 = (Note) o2;
                Date date1 = DateUtil.string2Date(note1.getCurrentTime(),"yyyy-MM-dd");
                Date date2 = DateUtil.string2Date(note2.getCurrentTime(),"yyyy-MM-dd");
                if(note1.getTag().equals("已完成") && !note2.getTag().equals("已完成"))
                    return 1;
                if(!note1.getTag().equals("已完成") && note2.getTag().equals("已完成"))
                    return -1;
                if(note1.getTag().equals("已完成") && note2.getTag().equals("已完成"))
                    if (date1.getTime() - date2.getTime() < 0){
                        return -1;
                    }else if(date1.getTime() - date2.getTime() > 0){
                        return 1;
                    } else
                        return 0;
                if (date1.getTime() - date2.getTime() < 0){
                    return -1;
                }else if(date1.getTime() - date2.getTime() > 0){
                    return 1;
                }
                return 0;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab:
                Intent intent = new Intent(this,AddNoteActivity.class);
                startActivityForResult(intent,100);
                break;
            case R.id.ll_msg:
                Toast.makeText(MainActivity.this,"MSG",Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
                break;
            case R.id.ll_wechat:
                Toast.makeText(MainActivity.this,"WeChat",Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
                break;
            case R.id.ll_mail:
                Toast.makeText(MainActivity.this,"Email",Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
                break;
            case R.id.ll_home:
                Toast.makeText(MainActivity.this,"home",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_email:
                Toast.makeText(MainActivity.this,"eamil",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_add:
                startActivity(new Intent(MainActivity.this,NewItemActivity.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==100 && resultCode==RESULT_OK){
            Note note = (Note) data.getSerializableExtra("note");
            notes.add(note);
            sort(notes);
        }
    }
}
