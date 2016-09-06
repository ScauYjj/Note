package com.chinamobile.notes.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yjj on 2016/8/29.
 */
public class InputUtil {

    //隐藏虚拟键盘
    public static void HideKeyboard(View v)
    {
        InputMethodManager imm = ( InputMethodManager ) v.getContext( ).getSystemService( Context.INPUT_METHOD_SERVICE );
        if ( imm.isActive( ) ) {
            imm.hideSoftInputFromWindow( v.getApplicationWindowToken( ) , 0 );

        }
    }

    //显示虚拟键盘
    public static void ShowKeyboard(View v)
    {
        InputMethodManager imm = ( InputMethodManager ) v.getContext( ).getSystemService( Context.INPUT_METHOD_SERVICE );

        imm.showSoftInput(v,InputMethodManager.SHOW_FORCED);

    }

    //强制显示或者关闭系统键盘
    public static void KeyBoard(final EditText txtSearchKey, final String status)
    {

        Timer timer = new Timer();
        timer.schedule(new TimerTask(){
            @Override
            public void run()
            {
                InputMethodManager m = (InputMethodManager)
                        txtSearchKey.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if(status.equals("open"))
                {
                    m.showSoftInput(txtSearchKey,InputMethodManager.SHOW_FORCED);
                }
                else
                {
                    m.hideSoftInputFromWindow(txtSearchKey.getWindowToken(), 0);
                }
            }
        }, 300);
    }

    //通过定时器强制隐藏虚拟键盘
    public static void TimerHideKeyboard(final View v)
    {
        Timer timer = new Timer();
        timer.schedule(new TimerTask(){
            @Override
            public void run()
            {
                InputMethodManager imm = ( InputMethodManager ) v.getContext( ).getSystemService( Context.INPUT_METHOD_SERVICE );
                if ( imm.isActive( ) )
                {
                    imm.hideSoftInputFromWindow( v.getApplicationWindowToken( ) , 0 );
                }
            }
        }, 10);
    }
    //输入法是否显示着
    public static boolean KeyBoard(EditText edittext)
    {
        boolean bool = false;
        InputMethodManager imm = ( InputMethodManager ) edittext.getContext( ).getSystemService( Context.INPUT_METHOD_SERVICE );
        if ( imm.isActive( ) )
        {
            bool = true;
        }
        return bool;

    }

    /**
     * 动态显示隐藏键盘
     *
     * @param editText
     * @param open
     */
    public static void showSoftInput(final EditText editText, boolean open) {
        if (editText != null) {
            try {
                if (open) {
                    editText.setFocusable(true);
                    editText.setFocusableInTouchMode(true);
                    editText.requestFocus();
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        public void run() {
                            InputMethodManager imm = (InputMethodManager) editText.getContext()
                                    .getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.showSoftInput(editText, 0);
                        }
                    }, 100);
                } else {
                    InputMethodManager imm = (InputMethodManager) (InputMethodManager) editText.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}