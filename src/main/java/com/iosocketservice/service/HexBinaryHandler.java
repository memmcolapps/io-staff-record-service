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
        if (msg instanceof ByteBuf) {
            ByteBuf buf = (ByteBuf) msg;
            try {
                byte[] bytes = new byte[buf.readableBytes()];
                buf.readBytes(bytes);
                // Convert byte array to HEX string
                String hexData = bytesToHex(bytes);
//                handleBytesSplittingByOffset(bytes, 1);
                // Broadcast the HEX data to all connected clients
                for (Channel channel : channels) {
                    if (channel != ctx.channel()) {
                        //channel.writeAndFlush(hexString);
                        channel.writeAndFlush(Unpooled.copiedBuffer(hexData + "\n", CharsetUtil.UTF_8));
                        log.info("Received HEX data {}", hexData);
                        //streamDataService.insertHexData(hexString);
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

    // Utility method to convert byte array to HEX string
    private String bytesToHex(byte[] bytes) {
        StringBuilder hexData = new StringBuilder();
        System.out.println("length of byte: "+bytes.length);
//        printTwoBytesFromByteArray(bytes);
        for (int i = 0; i < bytes.length; i++) {
            if (i > 0) {
                hexData.append(" ");
            }
//            printTwoBytesFromByteArray(bytes);
            hexData.append(String.format("%02X", bytes[i]));
        }
        log.info("Received HEX data length {}", hexData.length());
        handleSplit(hexData.toString());
        return hexData.toString();
    }

    public void handleSplit(String hexData) {
        // Split into 3 major component which are Configuration, enrolment & attendance
        List<String> frames = splitHexFrames(hexData);

        System.out.println("Frame>?>>> : " + frames);
        String openingFlag = null;
        String deviceId = null;
        String packetLength = null;
        String packetNo = null;
        String crc = null;
        String closingFlag = null;
        String last4Bytes = null;
        String remainingBytes = null;
        String first14Bytes = null;
        String packetType = null;
        for(int inc = 0; inc < frames.size(); inc++){
            String[] hexBytes = frames.get(inc).split(" ");
            // Extract the first 14 bytes
//            first14Bytes = extractBytes(hexBytes, 0, 14);

                // Extract the opening flag bytes
                openingFlag = extractBytes(hexBytes, 0, 2);

                // Extract the device Id bytes
                deviceId = extractBytes(hexBytes, 2, 8);

                // Extract the packet number bytes
                packetType = extractBytes(hexBytes, 10, 1);

                // Extract the packet number bytes
                packetLength = extractBytes(hexBytes, 11, 1);

                // Extract the packet number bytes
                packetNo = extractBytes(hexBytes, 12, 1);


                // Extract the opening flag bytes
                closingFlag = extractBytes(hexBytes, hexBytes.length - 2, 2);

            String first2Bytes = extractBytes(hexBytes, hexBytes.length - 4, 2); // First 2 bytes
//            String second2Bytes = extractBytes(hexBytes, hexBytes.length - 2, 2); // Second 2 bytes

            // Extract the last 4 bytes
//            last4Bytes = extractBytes(hexBytes, hexBytes.length - 4, 4);

//            crc = extractBytes(hexBytes, Integer.parseInt(last4Bytes), 2);
            // Extract the remaining bytes
            remainingBytes = extractBytes(hexBytes, 14, hexBytes.length - 18); // Exclude first 14 and last 4

//            streamDataService.postData();
            // Print the results
            System.out.println("openingFlag: " + openingFlag);
            System.out.println("deviceId: " + deviceId);
            System.out.println("packetType: " + packetType);
            System.out.println("packetLength: " + packetLength);
            System.out.println("packetNo: " + packetNo);
            System.out.println("Actual data: " + remainingBytes);
            System.out.println("crc: " + first2Bytes);
            System.out.println("closingFlag: " + closingFlag);
            System.out.println("==============================================");

        }
//        System.out.println("==============================================");
//        System.out.println("openingFlag: " + openingFlag);
//        System.out.println("deviceId: " + deviceId);
//        System.out.println("packetNoCount: " + packetNoCount);
//        System.out.println("packetNo: " + packetNo);
////        System.out.println("crc: " + crc);
//        System.out.println("closingFlag: " + closingFlag);



//        for (String frame : frames) {
//
//        String[] hexBytes = frame.split(" ");
//
//        // Extract the first 14 bytes
//         first14Bytes = extractBytes(hexBytes, 0, 14);
//
//            // Extract the opening flag bytes
//             openingFlag = extractBytes(hexBytes, 0, 2);
//
//            // Extract the device Id bytes
//             deviceId = extractBytes(hexBytes, 0, 8);
//
//            // Extract the packet number bytes
//             packetNoCount = extractBytes(hexBytes, 0, 1);
//
//            // Extract the packet number bytes
//             packetNo = extractBytes(hexBytes, 0, 1);
//
//            // Extract the CRC bytes
//             crc = extractBytes(hexBytes, 0, 1);
//
//            // Extract the opening flag bytes
//             closingFlag = extractBytes(hexBytes, 0, 2);
//
//
//
//        // Extract the last 4 bytes
//         last4Bytes = extractBytes(hexBytes, hexBytes.length - 4, 4);
//
//        // Extract the remaining bytes
//         remainingBytes = extractBytes(hexBytes, 14, hexBytes.length - 18); // Exclude first 14 and last 4
//
//        // Print the results
//        System.out.println("First 14 Bytes: " + first14Bytes);
//        System.out.println("Last 4 Bytes: " + last4Bytes);
//        System.out.println("Remaining Bytes: " + remainingBytes);
//        }
//
//        streamDataService.insertHexData(hexData, frames.get(0), frames.get(1), frames.get(2));
    }

    // Function to extract bytes from a hex string array
    public static String extractBytes(String[] hexBytes, int start, int count) {
        StringBuilder extractedBytes = new StringBuilder();

        for (int i = start; i < start + count; i++) {
            extractedBytes.append(hexBytes[i]).append(" ");
        }

        return extractedBytes.toString().trim();
    }

    public static List<String> splitHexFrames(String hexData) {
        List<String> frames = new ArrayList<>();

        // Split the data on the delimiter "3F F3" while preserving the delimiter at the start of each frame
        String[] parts = hexData.split("(?=3F F3)");

        for (String part : parts) {
            // Clean up any leading or trailing spaces
            frames.add(part.trim());
        }

        return frames;
    }


    public void handleBytesSplittingByOffset(ByteBuf byteBuf, int offset) {
        // Ensure there are at least 2 bytes available from the given offset
        if (byteBuf.readableBytes() >= offset + 2) {
            // Extract the byte at the specified offset and the next byte
            byte byte1 = byteBuf.getByte(offset);
            byte byte2 = byteBuf.getByte(offset + 1);

            // Print the bytes in hex format
            System.out.printf("Byte at offset %d: 0x%02X, Byte at offset %d: 0x%02X%n", offset, byte1, offset + 1, byte2);
        } else {
            System.out.println("Not enough bytes available from the specified offset");
        }
    }

    public void printTwoBytesFromByteArray(byte[] byteArray) {
        if (byteArray.length >= 2) {
            byte byte1 = byteArray[0];
            byte byte2 = byteArray[1];

            // Print them out in hex format
            System.out.printf("First byte: %02X, Second byte: %02X%n", byte1, byte2);
        } else {
            System.out.println("Not enough bytes in the array");
        }
    }

//    public void printTwoBytesFromByteArray(byte[] byteArray) {
//        if (byteArray.length >= 2) {
//            byte byte1 = byteArray[0];
//            byte byte2 = byteArray[1];
//
//            // Print them out in hex format
//            System.out.printf("First byte: %02X, Second byte: %02X%n", byte1, byte2);
//        } else {
//            System.out.println("Not enough bytes in the array");
//        }
//    }


}

