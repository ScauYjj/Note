package com.chinamobile.notes.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.chinamobile.notes.R;

public class NewItemActivity extends BaseActivity implements View.OnClickListener {


    private ImageView ivBack;
    private ImageView ivDone;
    private EditText etTitle;
    private ImageView ivColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);
        initView();
    }

    private void initView() {
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivDone = (ImageView) findViewById(R.id.ivDone);
        etTitle = (EditText) findViewById(R.id.etTitle);
        ivColor = (ImageView) findViewById(R.id.iv_color);

        ivBack.setOnClickListener(this);
        ivDone.setOnClickListener(this);
        ivColor.setOnClickListener(this);
    }

    private void submit() {
        // validate
        String etTitleString = etTitle.getText().toString().trim();
        if (TextUtils.isEmpty(etTitleString)) {
            Toast.makeText(this, "请输入清单名称", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ivBack:
                finish();
                break;
            case R.id.ivDone:
                submit();
                break;
            case R.id.iv_color:

                break;
        }
    }
}
