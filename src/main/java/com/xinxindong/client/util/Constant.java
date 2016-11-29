package com.xinxindong.client.util;

/**
 * 常用消息类型
 * Created by xingdu on 2016/9/24.
 */
public class Constant {


    public static final int PING = 0; //ping
    public static final int ACK = 1;
    public static final int REPLY = 2; //响应消息
    public static final int ASK = 3; //请求消息
    public static final int LOGIN = 4; //登陆
    public static final int PUSH = 5; //推送
    public static final int OFFLINE = 6; //app退入后台运行
    /**
     * 服务客户端码
     */
    public static final int LOGOUT = -4; //移动客户端离线


    public static final int CLIENT_CLOSE_CHANNEL = -10; //移动客服的关闭长连接
    /**
     * 移动客户端码
     */
    public static final int port = 10910; //端口
    public static final String daily = "120.24.248.171"; //登陆

}
