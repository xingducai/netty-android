package com.xinxindong.client;

import java.io.Serializable;

/**
 * 用户消息
 *
 * @author xingdu.cai
 */
public class NettyMsg implements Serializable {

    public class Reply {
        public static final byte YES = 1; //需要应答
        public static final byte NO = 0; //不需要应答

    }

    private static final long serialVersionUID = 1L;
    private int type; //消息类型
    private byte reply = Reply.NO; //0 不需要应答，2需要应答
    private String id;
    private String clientId;
    private String alias;
    private Integer code;


    private String title;


    private long time;

    public NettyMsg() {

    }


    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte getReply() {
        return reply;
    }

    public void setReply(byte reply) {
        this.reply = reply;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }


    public NettyMsg type(int type) {
        this.type = type;
        return this;
    }

    public NettyMsg code(int code) {
        this.code = code;
        return this;
    }

    public NettyMsg title(String title) {
        this.title = title;
        return this;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }


    /**
     * @param alias 消息消费者
     * @return
     */
    public static NettyMsg create(String alias) {
        NettyMsg msg = new NettyMsg();
        msg.setAlias(alias);

        return msg;
    }


}
