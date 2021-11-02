package com.zs.itking.eventbusputvaluedemo.base;

/**
 * created by on 2021/11/2
 * 描述：
 *
 * @author ZSAndroid
 * @create 2021-11-02-13:20
 */
public class AnyEventType {

    private String mMsg;

    public AnyEventType() {
    }

    public AnyEventType(String msg) {
        mMsg = msg;
    }

    public String getMsg() {
        return mMsg;
    }
}
