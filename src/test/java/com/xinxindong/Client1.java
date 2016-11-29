package com.xinxindong;

import com.xinxindong.client.NettyClient;
import com.xinxindong.client.NettyClientFactory;
import com.xinxindong.client.util.Constant;

/**
 * Created by xingdu on 2016/9/24.
 */
public class Client1 {


    public static void main(String[] args) throws InterruptedException {
        String alias = "1234";
        NettyClient netClient = NettyClientFactory.build(alias, (msg) -> {
            //所有捕获的事件都会回调，客户端在此处理相应的逻辑
            System.out.println("message from server... " + msg);
        }).bind(Constant.daily, Constant.port).start();

        NettyClient.writeAndFlush(null);
    }
}
