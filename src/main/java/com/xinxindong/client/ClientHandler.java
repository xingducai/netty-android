package com.xinxindong.client;

import com.alibaba.fastjson.JSONObject;
import com.xinxindong.client.util.Constant;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.function.Consumer;

/**
 * 捕获client 事件
 * Created by xingdu on 2016/9/24.
 */
public class ClientHandler extends ChannelHandlerAdapter {


    Consumer callback;


    public ClientHandler(Consumer callback) {

        this.callback = callback;
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        JSONObject nettyMsg = (JSONObject) msg;
        if (nettyMsg != null) {
            int type = nettyMsg.getIntValue("type");
            boolean call = true;
            switch (type) {

                case Constant.PING:
                    call = false;  //不需要回调客户端
                    break;
                case Constant.ACK:
                    break;
                case Constant.REPLY:
                    break;
                case Constant.ASK:
                    break;
                case Constant.LOGOUT:
                    LogoutHandler.handle(ctx, nettyMsg, callback);
                    break;

            }
            if (call && callback != null)
                callback.accept(msg);
        } else
            ctx.fireChannelRead(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }
}
