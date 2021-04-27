package com.chris.jpwrapper.bean;


import java.io.Serializable;

public class HandlerResult implements Serializable {

    private int code;

    private String msg;

    private String content;

    public static HandlerResult createInvalidResult() {
        HandlerResult handlerResult = new HandlerResult();
        handlerResult.setCode(400);
        handlerResult.setMsg("param error");
        return handlerResult;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
