package com.xinxindong.client;

import java.util.function.Consumer;

/**
 *
 * 构建nettyClient factory
 * Created by xingdu on 2016/9/24.
 */
public class NettyClientFactory {

    /**
     * 构建netty client
     * 服务端在处理完成逻辑后，如果回调函数不为空，则会客户端提供的回调函数
     *
     * @param alias    别名
     * @param callback 回调函数
     * @return
     * @throws InterruptedException
     */
    public static NettyClient build(String alias, Consumer callback) throws InterruptedException {
        NettyClient client = NettyClient.build(alias, callback);
        return client;
    }


    /**
     * 构建不带回调函数的netty客户端
     *
     * @param alias 别名
     * @return
     * @throws InterruptedException
     */
    public static NettyClient build(String alias) throws InterruptedException {
        NettyClient client = NettyClient.build(alias, null);
        return client;
    }


}
