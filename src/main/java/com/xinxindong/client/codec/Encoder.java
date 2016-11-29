package com.xinxindong.client.codec;

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by xingdu on 2016/9/24.
 */
public class Encoder extends MessageToByteEncoder<Object> {


    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        try {
            byte[] body = JSONObject.toJSONBytes(msg);  //将对象转换为byte，伪代码，具体用什么进行序列化，你们自行选择。可以使用我上面说的一些
            int dataLength = body.length;  //读取消息的长度
            out.writeInt(dataLength);  //先将消息长度写入，也就是消息头
            out.writeBytes(body);  //消息体中包含我们要发送的数据
        } catch (Exception e) {
            System.out.println(e + "");
        }
    }

    int count = 0;


}
