package com.xinxindong.client;

import com.alibaba.fastjson.JSONObject;
import com.xinxindong.client.util.Codes;
import com.xinxindong.client.util.Constant;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.util.concurrent.ScheduledFuture;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 *
 * 登陆成功后，定时发送心跳
 * Created by xingdu on 2016/9/24.
 */
public class HeartbeatReqHandler extends ChannelHandlerAdapter {


    ScheduledFuture heartbeat;
    Consumer callback;

    public HeartbeatReqHandler() {

    }

    public HeartbeatReqHandler(Consumer callback) {

        this.callback = callback;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {


    }

    /**
     * 登陆成功后处理心跳检查
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {


        JSONObject nettyMsg = (JSONObject) msg;
        if (nettyMsg != null) {

            int type = nettyMsg.getIntValue("type");
            int code = nettyMsg.getIntValue("code");
            if (type == Constant.LOGIN && code == Codes.success) {
                System.out.println("-----alias bind success----------");
                if (callback != null) {
                    callback.accept(msg);
                }

                //登陆成功后，每隔10s发送心跳数据
                heartbeat = ctx.executor().scheduleAtFixedRate(() -> {
                    NettyMsg heart = new NettyMsg();
                    heart.setType(Constant.PING);
                    ctx.writeAndFlush(heart);
                }, 0, 10, TimeUnit.SECONDS);
            } else
                ctx.fireChannelRead(msg);
        } else
            ctx.fireChannelRead(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        if (heartbeat != null) {
            heartbeat.cancel(true);
            heartbeat = null;
        }

        super.exceptionCaught(ctx, cause);
        ctx.close();
    }

    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        super.close(ctx, promise);
    }


}
