package com.example.zyjulib;

public class EventBusEntity {
    private int mMsgId;
    private int context;
    private int type;//type，1极致单品，2优质评论，3猜你喜欢，4系统通知

    private Object object;

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public EventBusEntity(int msg) {
        mMsgId = msg;
    }

    public EventBusEntity() {
    }


    public void setMsgId(int mMsgId) {
        this.mMsgId = mMsgId;
    }

    public void setContext(int id) {
        this.context = id;
    }

    public int getMsgId() {
        return mMsgId;
    }

    public int getContext() {
        return context;
    }
}
