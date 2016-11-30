package com.xinxindong.client;

import com.xinxindong.client.util.Constant;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.function.Consumer;

/**
 * Created by xingdu on 2016/9/24.
 */
public class LoginAuthReqHandler extends ChannelHandlerAdapter {


    private String alias;
    private Consumer callback;

    public LoginAuthReqHandler(String alias, Consumer callback) {
        this.alias = alias;
        this.callback = callback;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        if (alias == null || alias.equals("")) {
            throw new RuntimeException("别名不能为空");
        }
        System.out.println("---------------alias ready to server---" + alias);
        NettyMsg nettyMsg = NettyMsg.create(alias);
        nettyMsg.setType(Constant.LOGIN);
        ctx.writeAndFlush(nettyMsg);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ctx.fireChannelRead(msg);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        super.exceptionCaught(ctx, cause);
    }

    /**
     * 移动客户端可以选择在此处建立重连接
     * 如果是异地登陆，禁止重新连接
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        if (!LogoutHandler.isLogout()) //异地登陆禁止重新连接
            NettyClient.reconnect();
    }


}
