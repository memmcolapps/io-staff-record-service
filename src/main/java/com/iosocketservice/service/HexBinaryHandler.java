package com.iosocketservice.service;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


public class HexBinaryHandler extends ChannelInboundHandlerAdapter {
    private static final Logger log = LoggerFactory.getLogger(HexBinaryHandler.class);
    // Create a ChannelGroup to manage connected clients
    private static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private final List<Object> data = new ArrayList<>();

    private final StreamDataService streamDataService;
    public HexBinaryHandler(StreamDataService streamDataService) {
        this.streamDataService = streamDataService;
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // Add the new channel to the channel group
        channels.add(ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        HexSplitDecimalHandler hexSplitDecimalHandler = new HexSplitDecimalHandler(streamDataService);
        if (msg instanceof ByteBuf) {
            ByteBuf buf = (ByteBuf) msg;
            try {
                byte[] bytes = new byte[buf.readableBytes()];
                buf.readBytes(bytes);
                // Convert byte array to HEX string
                String hexData = hexSplitDecimalHandler.bytesToHex(bytes);
                // Broadcast the HEX data to all connected clients
                for (Channel channel : channels) {
                    if (channel != ctx.channel()) {
                        //channel.writeAndFlush(hexString);
                        channel.writeAndFlush(Unpooled.copiedBuffer(hexData + "\n", CharsetUtil.UTF_8));
                        log.info("Received HEX data {}", hexData);
                    }else {
//                        streamDataService.insertHexData(hexString);
                    }

                }
            } finally {
                buf.release();
            }
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}

