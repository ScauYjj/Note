package com.chinamobile.notes.entity;

import java.io.Serializable;

/**
 * Created by yjj on 2016/8/24.
 */
public class Note implements Serializable{

    private int id;
    private String title;
    private String content;
    private String currentTime;
    private boolean priority;
    private boolean isCompleted;
    private String alarmTime;
    private String category;
    private String tag;

    public Note() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public boolean isPriority() {
        return priority;
    }

    public void setPriority(boolean priority) {
        this.priority = priority;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", currentTime='" + currentTime + '\'' +
                ", priority=" + priority +
                ", isCompleted=" + isCompleted +
                ", alarmTime='" + alarmTime + '\'' +
                ", category='" + category + '\'' +
                ", tag='" + tag + '\'' +
                '}';
    }
}
