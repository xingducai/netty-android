package com.xinxindong.client;

import com.xinxindong.client.codec.Decord;
import com.xinxindong.client.codec.Encoder;
import com.xinxindong.client.util.Constant;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Created by xingdu on 2016/9/24.
 */
public class NettyClient {
    private int port; //服务端端口
    private String host; //服务端ip或者域名不需要http://
    private static SocketChannel socketChannel; //留给客户端操作的写通道
    private String alias; //登陆绑定别名
    private static final EventExecutorGroup group = new DefaultEventExecutorGroup(20);

    public static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    private Consumer callback;

    private NettyClient() {
    }

    /**
     * 客户端想服务端写数据接口
     *
     * @param msg
     */
    public static void writeAndFlush(Object msg) {
        socketChannel.writeAndFlush(msg);
    }


    /**
     * 构建netty client
     * 服务端在处理完成逻辑后，如果回调函数不为空，则会客户端提供的回调函数
     *
     * @param callback 回调函数
     * @return
     * @throws InterruptedException
     */
    public static NettyClient build(String alias, Consumer callback) throws InterruptedException {
        NettyClient client = new NettyClient();
        client.callback = callback;
        client.alias = alias;

        return client;
    }

    public NettyClient bind(String host, int port) {
        this.host = host;
        this.port = port;
        return this;
    }

    public NettyClient start() throws InterruptedException {
        start_();
        return this;
    }

    private void start_() throws InterruptedException {
        try {


            EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, false);
            bootstrap.group(eventLoopGroup);
            bootstrap.remoteAddress(host, Constant.port);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new IdleStateHandler(20, 10, 0));
                    socketChannel.pipeline().addLast(new Encoder());
                    socketChannel.pipeline().addLast(new Decord());
                    socketChannel.pipeline().addLast(new LoginAuthReqHandler(alias, callback));
                    socketChannel.pipeline().addLast(new HeartbeatReqHandler(callback));
                    socketChannel.pipeline().addLast(new ClientHandler(callback));

                }
            });

            ChannelFuture future = bootstrap.connect(host, port).sync();
            if (future.isSuccess()) {
                socketChannel = (SocketChannel) future.channel();
                System.out.println("-----connect server  success---------");
            }
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            reconnect(alias, callback);
        }
    }


    public static void reconnect(String alias, Consumer callback) {

        scheduledExecutorService.execute(() -> {
            try {
                System.out.println("sleep 5 s,try reconnect server");
                TimeUnit.SECONDS.sleep(5);
                NettyClientFactory.build(alias, callback);
            } catch (Exception e1) {
                reconnect(alias, callback);
            }
        });
    }

}