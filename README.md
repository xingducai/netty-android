###  netty android 客户端程序


### 快速开始

```

 public static void main(String[] args) throws InterruptedException {
        String alias = "1234";
        String host = Constant.daily; //netty server host
        int port = Constant.port; //netty server port
        //初始化nettyclient 
        NettyClient netClient = NettyClientFactory.build(alias, (msg) -> {
            //所有捕获的事件都会回调，客户端在此处理相应的逻辑
            System.out.println("message from server... " + msg);
        }).bind(host, port).start();

        //android客户端写入数据到服务端
        NettyClient.writeAndFlush(null);
    }
```
 