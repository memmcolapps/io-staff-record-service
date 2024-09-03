package com.memmcol.iostaffrecordservice.service;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NettyServer {

    private final StreamDataService streamDataService;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private ChannelFuture channelFuture;
    @Autowired
    public NettyServer(StreamDataService streamDataService) {
        this.streamDataService = streamDataService;
    }
    public void startServer() throws InterruptedException {
         bossGroup = new NioEventLoopGroup(1); // processes connections
         workerGroup = new NioEventLoopGroup(); //processes i/o (read/write) of accepted connection
        final int port = 8883;
        try {
            ServerBootstrap bootstrap = new ServerBootstrap(); //used to set up netty server
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new HexBinaryHandler(streamDataService));
                        }
                    });
            channelFuture = bootstrap.bind(port).sync();
            System.out.println("Netty server started on port 8883");
            channelFuture.channel().closeFuture().sync();
//            bootstrap.bind(8883).sync().channel().closeFuture().sync();
//            Channel channel = bootstrap.bind(port).sync().channel();
//            System.out.println("Netty server started on port 8883");
//            channel.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    @PreDestroy
    public void stopServer() {
        System.out.println("Shutting down Netty server...");
        if (channelFuture != null) {
            channelFuture.channel().close();
        }
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
        System.out.println("Netty server stopped.");
    }
}

