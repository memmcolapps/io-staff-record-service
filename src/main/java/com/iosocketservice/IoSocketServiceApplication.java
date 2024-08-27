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
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class IoSocketServiceApplication implements CommandLineRunner, ApplicationListener<ContextClosedEvent> {

    @Autowired
    private NettyServer nettyServer;

    public static void main(String[] args) {
        SpringApplication.run(IoSocketServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws InterruptedException {
        nettyServer.startServer();
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        nettyServer.stopServer();
    }
}


//public class IoSocketServiceApplication {
//    @Autowired
//    private NettyServer nettyServer; // Autowire your Netty server class
//
//    public static void main(String[] args) {
//
//        SpringApplication.run(IoSocketServiceApplication.class, args);
//
////        startServer(); // This will start the Netty server
//
//    }
//    // CommandLineRunner allows you to execute code after the application starts
//    @Bean
//    CommandLineRunner startNettyServer() {
//        return args -> {
//            System.out.println("Starting Netty Server...");
//                // Start your Netty server after the Spring context is ready
//                nettyServer.startServer();
//        };
//    }
//
//    @EventListener(ContextClosedEvent.class)
//    public void stopNettyServer() {
//        nettyServer.stopServer();
//        System.out.println("Netty server stopped.");
//    }
//
//}

///--------------------------------------------------------------
// 3F F3 00 00 00 00 00 00 00 00 00 01 18 18 01 01 01 37 AA EE EE 00 02 2A B6 24 07 25 05 15 42 00 00 00 00 00 00 16 26 00 03 00 01 00 03 00 00 15 2C 33 38 30 30 34 32 32 38 45 45 42 43 FF FF FF FF FF FF FF FF FF FF FF FF A6 4D 21 12
//3F F3 00 00 00 00 00 00 00 00 00 01 18 18 02 01 01 15 00 01 33 38 30 30 34 32 32 38 45 45 42 43 24 07 25 05 15 41 17 E7 31 21 12
//3F F3 00 00 00 00 00 00 00 00 00 01 18 18 03 01 01 1E 00 01 01 24 07 25 05 15 42 17 00 01 02 24 07 25 05 15 42 40 00 01 01 24 08 13 03 10 39 35 DB 0B 21 12
