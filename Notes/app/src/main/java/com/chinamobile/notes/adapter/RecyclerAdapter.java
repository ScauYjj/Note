package com.chinamobile.notes.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chinamobile.notes.R;
import com.chinamobile.notes.entity.Note;

import java.util.ArrayList;

/**
 * Created by yjj on 2016/8/24.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private ArrayList<Note> notes = new ArrayList<>();
    private LayoutInflater mInflater;
    private int GROUP_ITEM = 0;
    private int NORMAL_ITEM = 1;

    public RecyclerAdapter(Context context, ArrayList<Note> notes){
        mContext = context;
        this.notes = notes;
        mInflater = LayoutInflater.from(context);
    }

    /**
     * 渲染具体的ViewHolder
     * @param parent ViewHolder的容器
     * @param viewType 一个标志，我们根据该标志可以实现渲染不同类型的ViewHolder
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == NORMAL_ITEM) {
            return new NormalItemHolder(mInflater.inflate(R.layout.item_layout, parent, false));
        } else {
            return new GroupItemHolder(mInflater.inflate(R.layout.item_with_tag_layout, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Note note = notes.get(position);
        if (note == null) return;
        if (holder instanceof GroupItemHolder){
            bindGroupItem(note,(GroupItemHolder) holder);
        }else {
            NormalItemHolder normalItemHolder = (NormalItemHolder) holder;
            bindNormalItem(note, normalItemHolder.content, normalItemHolder.date);
        }
    }

    private void bindGroupItem(Note note, GroupItemHolder holder) {
        bindNormalItem(note,holder.content,holder.date);
        holder.tag.setText(note.getTag());
    }

    private void bindNormalItem(Note note, TextView content, TextView date) {
        content.setText(note.getContent());
        date.setText(note.getCurrentTime());
    }

    @Override
    public int getItemCount(){
        return notes.size();
    }

    /**
     * 决定元素的布局使用哪种类型
     * @param position 数据源List的下标
     * @return 一个int型标志，传递给onCreateViewHolder的第二个参数
     */

    @Override
    public int getItemViewType(int position) {
        if (position==0) return GROUP_ITEM;
            String currentTag = notes.get(position).getTag();
            int prevIndex = position - 1;
            boolean isDifferent = !notes.get(prevIndex).getTag().equals(currentTag);
            return isDifferent ? GROUP_ITEM : NORMAL_ITEM;
    }

    /**
     * 无标题
     */
    class NormalItemHolder extends RecyclerView.ViewHolder {
        TextView content;
        TextView date;

        public NormalItemHolder(View itemView) {
            super(itemView);
            content = (TextView) itemView.findViewById(R.id.item_tv_text);
            date = (TextView) itemView.findViewById(R.id.tv_date);
        }
    }

    /**
     * 带标题
     */
    class GroupItemHolder extends NormalItemHolder {
        TextView tag;

        public GroupItemHolder(View itemView) {
            super(itemView);
            tag = (TextView) itemView.findViewById(R.id.tv_tag);
        }
    }

}
