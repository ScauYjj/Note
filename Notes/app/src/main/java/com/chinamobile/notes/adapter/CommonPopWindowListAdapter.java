package com.chinamobile.notes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chinamobile.notes.R;
import java.util.List;

/**
 * 弹窗菜单列表适配器
 * @author junbin
 * @date 2016/03/23
 */
public class CommonPopWindowListAdapter extends BaseAdapter {
    private List<String> items;
    private LayoutInflater mLayoutInflater;
    private Context context;

    public CommonPopWindowListAdapter(Context context, List<String> items) {
        // TODO Auto-generated constructor stub
        this.items = items;
        this.context = context;

        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return items.size();
    }

    @Override
    public Object getItem(int postion) {
        // TODO Auto-generated method stub
        return items.get(postion);
    }

    @Override
    public long getItemId(int postion) {
        // TODO Auto-generated method stub
        return postion;
    }

    @Override
    public View getView(int postion, View convertView, ViewGroup parentView) {
        // TODO Auto-generated method stub
        ViewHolder vh = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.common_popwindow_item,null);
            vh = new ViewHolder();
            vh.tvItem = (TextView) convertView.findViewById(R.id.tv_item);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        String item=items.get(postion);
        vh.tvItem.setText(item);
        return convertView;
    }

    class ViewHolder {
        TextView tvItem;
    }
}
