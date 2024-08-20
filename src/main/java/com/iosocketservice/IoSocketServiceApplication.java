package com.iosocketservice;

import com.iosocketservice.service.HexBinaryHandler;
import com.iosocketservice.service.NettyServer;
import io.netty.bootstrap.ServerBootstrap;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class IoSocketServiceApplication {
    @Autowired
    private NettyServer nettyServer; // Autowire your Netty server class

    public static void main(String[] args) {

        SpringApplication.run(IoSocketServiceApplication.class, args);

//        startServer(); // This will start the Netty server

    }
    // CommandLineRunner allows you to execute code after the application starts
    @Bean
    CommandLineRunner startNettyServer() {
        return args -> {
            // Start your Netty server after the Spring context is ready
            nettyServer.startServer();
        };
    }
//    public static void startServer() {
//        final int port = 8883;
//        EventLoopGroup bossGroup = new NioEventLoopGroup(3);
//        EventLoopGroup workerGroup = new NioEventLoopGroup();
//
//        try {
//            ServerBootstrap bootstrap = new ServerBootstrap();
//            bootstrap.group(bossGroup, workerGroup)
//                    .channel(NioServerSocketChannel.class)
//                    .childHandler(new ChannelInitializer<SocketChannel>() {
//                        @Override
//                        protected void initChannel(SocketChannel ch) {
//                            ChannelPipeline pipeline = ch.pipeline();
//                            pipeline.addLast(new HexBinaryHandler()); // Custom handler to process incoming HEX binary data
//                        }
//                    });
//            //b.bind(port).sync().channel().closeFuture().sync();
//            Channel channel = bootstrap.bind(port).sync().channel();
//            channel.closeFuture().sync();
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        } finally {
//            bossGroup.shutdownGracefully();
//            workerGroup.shutdownGracefully();
//        }
//    }
}


