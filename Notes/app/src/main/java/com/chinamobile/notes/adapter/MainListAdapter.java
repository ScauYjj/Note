package com.chinamobile.notes.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chinamobile.notes.R;
import com.chinamobile.notes.entity.Note;
import com.github.jdsjlzx.swipe.SwipeMenuAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Adapter for main list to show notes with RecyclerView
 */
public class MainListAdapter extends SwipeMenuAdapter<MainListAdapter.BaseItemHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<Note> notes = new ArrayList<Note>();
    private HashMap<String, List<Note>> tagContentMap = new HashMap<String, List<Note>>();
    private HashMap<String, Integer> tagStartPosition = new HashMap<String,Integer>();
    private List<Integer> tagOrder;
    private HashMap<Integer, Integer> itemPositionMap = new HashMap<Integer, Integer>(); //显示列表中的位置对应数据列表中的位置
    private List<NormalItemHolder> itemHolderList = new ArrayList<NormalItemHolder>();
    private int ignoreItemNum = 0; //忽略内容为空的item个数
    private int listIterater = 0;  //list的遍历标志
    public static final int TYPE_GROUP_ITEM = 0;
    public static final int TYPE_NORMAL_ITEM = 1;

    public MainListAdapter(Context context, ArrayList<Note> notes){
        mContext = context;
        this.notes = notes;
        mInflater = LayoutInflater.from(context);
        collectTagContent();
        sortTags();
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        if (viewType == TYPE_NORMAL_ITEM) {
            return LayoutInflater.from(mContext).inflate(R.layout.item_layout, parent, false);
        } else {
            return LayoutInflater.from(mContext).inflate(R.layout.item_with_tag_layout, parent, false);
        }
    }

    @Override
    public BaseItemHolder onCompatCreateViewHolder(View view, int viewType) {
        if (viewType == TYPE_NORMAL_ITEM) {
            NormalItemHolder normalItemHolder = new NormalItemHolder(view);
            itemHolderList.add(normalItemHolder);
            return normalItemHolder;
        } else {
            return new GroupItemHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(BaseItemHolder holder, int position) {
        /*collectTagContent();
        sortTags();*/
        if (holder instanceof GroupItemHolder){
            Note note = null;
            if(listIterater >= notes.size())
                return;
            note = notes.get(listIterater);

            if(note != null) {
                if(note.getTitle() == null || note.getTitle().equals(""))
                    listIterater++;
                GroupItemHolder groupItemHolder = (GroupItemHolder)holder;
                final String tag = note.getTag();
                groupItemHolder.tag.setText(tag);
                Log.i("tag",tagStartPosition.get(tag).toString());
                String content = notes.get(tagStartPosition.get(tag)).getTitle();
                if(content != null && !content.equals("")) {
                    groupItemHolder.foldButton.setBackgroundResource(R.mipmap.fold);
                } else {
                    groupItemHolder.foldButton.setBackgroundResource(R.mipmap.unfold);
                }

                groupItemHolder.foldButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String content = notes.get(tagStartPosition.get(tag)).getTitle();
                        if(content != null && !content.equals("")){
                            notes.removeAll(tagContentMap.get(tag));
                            Note note = new Note();
                            note.setTag(tag);
                            notes.add(tagStartPosition.get(tag), note);
                            dataChange();
                            view.setBackgroundResource(R.mipmap.unfold);
                        } else {
                            notes.remove((int)tagStartPosition.get(tag));

                            notes.addAll(tagStartPosition.get(tag), tagContentMap.get(tag));
                            dataChange();
                            view.setBackgroundResource(R.mipmap.fold);
                        }
                    }
                });
            }
        } else if(holder instanceof NormalItemHolder){
            NormalItemHolder normalItemHolder = (NormalItemHolder) holder;

            if(listIterater >= notes.size())
                return;
            Note note = notes.get(listIterater);
            if(note.getTitle() == null || note.getTitle().equals("") && ++listIterater < notes.size())
                note = notes.get(listIterater);
            itemPositionMap.put(position, listIterater);
            listIterater++;


            normalItemHolder.content.setText(note.getTitle());
            normalItemHolder.date.setText(note.getCurrentTime());
        }
    }

    @Override
    public int getItemCount(){
        return notes.size()+tagOrder.size()-ignoreItemNum;
    }

    private void collectTagContent(){
        for(int i = 0; i < notes.size(); i++){
            Note note = notes.get(i);
            List<Note> tempNotes = tagContentMap.get(note.getTag());
            if(tempNotes == null) {
                tagStartPosition.put(note.getTag(), i);
                tempNotes = new ArrayList<Note>();
                tempNotes.add(note);
                tagContentMap.put(note.getTag(), tempNotes);
            } else
                tempNotes.add(note);
        }
    }

    /**
     * 整理各个tag的item在数据list的结束位置
     */
    private void sortTags(){
        ignoreItemNum = 0;
        listIterater = 0;
        int preTagNum = 0;
        String preTag = notes.get(0).getTag();
        tagOrder = new ArrayList<Integer>();
        tagOrder.add(0);
        preTagNum++;
        for(int i = 0; i < notes.size(); i++){
            Note note = notes.get(i);
            if(!note.getTag().equals(preTag)) {
                tagOrder.add(i - ignoreItemNum + preTagNum);
                preTagNum++;
                Log.i("tagOrder", i+"");
            }
            if(note.getTitle() == null || note.getTitle().equals(""))
                ignoreItemNum++;
            preTag = note.getTag();
        }
    }

    /**
     * 决定元素的布局使用哪种类型
     * @param position 显示列表中的位置
     * @return 一个int型标志，传递给onCreateViewHolder的第二个参数
     */
    @Override
    public int getItemViewType(int position) {
        for(int i = 0; i < tagOrder.size(); i++)
            if(position == tagOrder.get(i)) {
                Log.i("type+position", position+"GROUP");
                return TYPE_GROUP_ITEM;
            }
        Log.i("type+position", position+"NORMAL");
        return TYPE_NORMAL_ITEM;
    }

    public List<Note> getDataList(){
        return this.notes;
    }

    public HashMap<Integer, Integer> getItemPositionMap() {
        return this.itemPositionMap;
    }

    public List<NormalItemHolder> getItemHolderList(){
        return this.itemHolderList;
    }



    public void dataChange(){
        sortTags();
        notifyDataSetChanged();
    }

    class BaseItemHolder extends RecyclerView.ViewHolder{
        public BaseItemHolder(View itemView){
            super(itemView);
        }
    }
    /**
     * 无标题
     */
    public class NormalItemHolder extends BaseItemHolder {
        CheckBox checkBox;
        TextView content;
        TextView date;

        public NormalItemHolder(View itemView) {
            super(itemView);
            checkBox = (CheckBox) itemView.findViewById(R.id.item_checkbox);
            content = (TextView) itemView.findViewById(R.id.item_tv_text);
            date = (TextView) itemView.findViewById(R.id.tv_date);
        }

        public CheckBox getCheckBox(){
            return this.checkBox;
        }
    }

    /**
     * 带标题
     */
    class GroupItemHolder extends BaseItemHolder {
        TextView tag;
        ImageButton foldButton;
        public GroupItemHolder(View itemView) {
            super(itemView);
            tag = (TextView) itemView.findViewById(R.id.tv_tag);
            foldButton = (ImageButton)itemView.findViewById(R.id.fold_button);
        }
    }

}



