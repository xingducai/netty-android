package com.xinxindong.client.util;

/**
 * Created by xingdu on 2016/9/24.
 */
public class Codes {
    /**
     * 通用
     */
    public static final int success = 200; //成功
    public static final int error = 500; //出错
    public static final int unWrite = 501; //channel不可写

    /**
     * 客户端登陆失败
     */
    public static final int nobind = 10000; //别名未绑定
    public static final int login_fail_max_limit = 10001; //超过服务端限制最大连接数


    public static final int offline = 1002; //用户app退入后台运行

}
