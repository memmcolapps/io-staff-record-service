package com.iosocketservice.service;

import com.iosocketservice.mapper.RawData;
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
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

//@Service
public class HexBinaryHandler extends ChannelInboundHandlerAdapter {
    private static final Logger log = LoggerFactory.getLogger(HexBinaryHandler.class);
    // Create a ChannelGroup to manage connected clients
    private static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    //private static final List<Channel> channels = new ArrayList<>();

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
        if (msg instanceof ByteBuf) {
            ByteBuf buf = (ByteBuf) msg;
            try {
                byte[] bytes = new byte[buf.readableBytes()];
                buf.readBytes(bytes);
                // Convert byte array to HEX string
                String hexString = bytesToHex(bytes);
                // Broadcast the HEX data to all connected clients
                for (Channel channel : channels) {
                    if (channel != ctx.channel()) {
                        //channel.writeAndFlush(hexString);
                        channel.writeAndFlush(Unpooled.copiedBuffer(hexString + "\n", CharsetUtil.UTF_8));
                        log.info("Received HEX data {}", hexString);
                        //streamDataService.insertHexData(hexString);
                    }else {
                        streamDataService.insertHexData(hexString);
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

    // Utility method to convert byte array to HEX string
    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            if (i > 0) {
                hexString.append(" ");
            }
            hexString.append(String.format("%02X", bytes[i]));
        }
        return hexString.toString();
    }
}

