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
/////-----------------------------------------------------------------------------------

//    public void handleSplit(String hexData) {
//        // Split into 3 major component which are Configuration, enrolment & attendance
//        List<String> frames = splitHexFrames(hexData);
//
//        System.out.println("Frame>>>> : " + frames);
//        System.out.println("Frame Size>>>> : " + frames.size());
//        String openingFlag = null;
//        String deviceId = null;
//        String packetNumber = null;
//        String packetLength = null;
//        String packetSize = null;
//        String crc = null;
//        String closingFlag = null;
//        String fingerPrintId = null;
//        String loginMemory = null;
//        String configDateTime = null;
//        String lastLoginDateTime= null;
//        String nextLoginOffset= null;
//        String prevRecordCount= null;
//        String totalEnrolUser = null;
//        String totalLoginUser= null;
//        String totalRecordPush= null;
//        String nextPushOffset= null;
//        String adminIdTagNumber= null;
//        String deviceIdNumber = null;
//        String actualData = null;
//        String deviceStatus = null;
//        String packetType = null;
//        String idTagNumber = null;
//        String dataTime = null;
//        String loginType = null;
//        for(int inc = 0; inc < frames.size(); inc++){
//            String[] hexBytes = frames.get(inc).split(" ");
//
//            if(frames.size() == 3){
//                // Extract the first 14 bytes
////            first14Bytes = extractBytes(hexBytes, 0, 14);
//
//                // Extract the opening flag bytes
//                openingFlag = extractBytes(hexBytes, 0, 2);
//
//                // Extract the device Id bytes
//                deviceId = extractBytes(hexBytes, 2, 12);
//
//                // Extract the packet number bytes
//                packetType = extractBytes(hexBytes, 14, 1);
//
//                // Extract the packet number bytes
//                packetNumber = extractBytes(hexBytes, 15, 1);
//
//                // Extract the packet number bytes
//                packetLength = extractBytes(hexBytes, 16, 1);
//
//                // Extract the packet number bytes
//                packetSize = extractBytes(hexBytes, 17, 1);
//
//                crc = extractBytes(hexBytes, hexBytes.length - 4, 2); // First 2 bytes
//
//                // Extract the opening flag bytes
//                closingFlag = extractBytes(hexBytes, hexBytes.length - 2, 2);
//
////            String second2Bytes = extractBytes(hexBytes, hexBytes.length - 2, 2); // Second 2 bytes
//
//                // Extract the last 4 bytes
////            last4Bytes = extractBytes(hexBytes, hexBytes.length - 4, 4);
//
////            crc = extractBytes(hexBytes, Integer.parseInt(last4Bytes), 2);
//                // Extract the remaining bytes
//                if(inc == 0){
//                    actualData = extractBytes(hexBytes, 18, hexBytes.length - 18); // Exclude first 14 and last 4
//                    deviceStatus = extractBytes(hexBytes, 18, 3);
//                    fingerPrintId = extractBytes(hexBytes, 21, 2);
//                    loginMemory = extractBytes(hexBytes, 23, 2);
//                    configDateTime = extractBytes(hexBytes, 25, 6);
//                    lastLoginDateTime = extractBytes(hexBytes, 31, 6);
//                    nextLoginOffset = extractBytes(hexBytes, 37, 2);
//                    prevRecordCount = extractBytes(hexBytes, 39, 2);
//                    totalEnrolUser = extractBytes(hexBytes, 41, 2);
//                    totalLoginUser = extractBytes(hexBytes, 43, 2);
//                    totalRecordPush = extractBytes(hexBytes, 45, 2);
//                    nextPushOffset = extractBytes(hexBytes, 47, 2);
//                    adminIdTagNumber = extractBytes(hexBytes, 49, 12);
//                    deviceIdNumber = extractBytes(hexBytes, 61, 12);
//
//                    System.out.println("openingFlag: " + openingFlag);
//                    System.out.println("deviceId: " + deviceId);
//                    System.out.println("packetType: " + packetType);
//                    System.out.println("packetNumber: " + packetNumber);
//                    System.out.println("packetLength: " + packetLength);
//                    System.out.println("packetSize: " + packetSize);
//                    System.out.println("Actual data: " + actualData);
//                    System.out.println("Device Status: " + deviceStatus);
//                    System.out.println("finger Print Id: " + fingerPrintId);
//                    System.out.println("login Memory: " + loginMemory);
//
//                    System.out.println("config DateTime: " + configDateTime);
//                    System.out.println("last Login DateTime: " + lastLoginDateTime);
//                    System.out.println("next Login Offset: " + nextLoginOffset);
//                    System.out.println("prev Record Count: " + prevRecordCount);
//                    System.out.println("total Enrol User: " + totalEnrolUser);
//                    System.out.println("total Login User: " + totalLoginUser);
//                    System.out.println("total Record Push: " + totalRecordPush);
//                    System.out.println("next Push Offset: " + nextPushOffset);
//                    System.out.println("adminId Tag Number: " + adminIdTagNumber);
//                    System.out.println("deviceId Number: " + deviceIdNumber);
//
//                    System.out.println("crc: " + crc);
//                    System.out.println("closingFlag: " + closingFlag);
//                    System.out.println("==============================================");
//                } else if(inc == 1){
//                    actualData = extractBytes(hexBytes, 18, hexBytes.length - 18); // Exclude first 14 and last 4
//                    fingerPrintId = extractBytes(hexBytes, 18, 2);
//                    idTagNumber = extractBytes(hexBytes, 20, 12);
//                    dataTime = extractBytes(hexBytes, 32, 7);
//                    System.out.println("openingFlag: " + openingFlag);
//                    System.out.println("deviceId: " + deviceId);
//                    System.out.println("packetType: " + packetType);
//                    System.out.println("packetNumber: " + packetNumber);
//                    System.out.println("packetLength: " + packetLength);
//                    System.out.println("packetSize: " + packetSize);
//                    System.out.println("Actual data: " + actualData);
//                    System.out.println("finger Print Id: " + fingerPrintId);
//                    System.out.println("id Tag Number: " + idTagNumber);
//                    System.out.println("DateTime: " + dataTime);
//
//                    System.out.println("crc: " + crc);
//                    System.out.println("closingFlag: " + closingFlag);
//                    System.out.println("==============================================");
//                } else {
//                    actualData = extractBytes(hexBytes, 18, hexBytes.length - 18); // Exclude first 14 and last 4
//                    fingerPrintId = extractBytes(hexBytes, 18, 2);
//                    loginType = extractBytes(hexBytes, 20, 2);
//                    dataTime = extractBytes(hexBytes, 22, 7);
//                    System.out.println("openingFlag: " + openingFlag);
//                    System.out.println("deviceId: " + deviceId);
//                    System.out.println("packetType: " + packetType);
//                    System.out.println("packetNumber: " + packetNumber);
//                    System.out.println("packetLength: " + packetLength);
//                    System.out.println("packetSize: " + packetSize);
//                    System.out.println("Actual data: " + actualData);
//                    System.out.println("finger Print Id: " + fingerPrintId);
//                    System.out.println("login Type: " + loginType);
//                    System.out.println("DateTime: " + dataTime);
//
//                    System.out.println("crc: " + crc);
//                    System.out.println("closingFlag: " + closingFlag);
//                }
//
//            }
//            if(frames.size() == 2){
//                // Extract the first 14 bytes
////            first14Bytes = extractBytes(hexBytes, 0, 14);
//
//                // Extract the opening flag bytes
//                openingFlag = extractBytes(hexBytes, 0, 2);
//
//                // Extract the device Id bytes
//                deviceId = extractBytes(hexBytes, 2, 8);
//
//                // Extract the packet number bytes
//                packetType = extractBytes(hexBytes, 10, 1);
//
//                // Extract the packet number bytes
//                packetLength = extractBytes(hexBytes, 11, 1);
//
//                // Extract the packet number bytes
//                packetSize = extractBytes(hexBytes, 12, 1);
//
//                // Extract the opening flag bytes
//                closingFlag = extractBytes(hexBytes, hexBytes.length - 2, 2);
//
//                crc = extractBytes(hexBytes, hexBytes.length - 4, 2); // First 2 bytes
////            String second2Bytes = extractBytes(hexBytes, hexBytes.length - 2, 2); // Second 2 bytes
//
//                // Extract the last 4 bytes
////            last4Bytes = extractBytes(hexBytes, hexBytes.length - 4, 4);
//
////            crc = extractBytes(hexBytes, Integer.parseInt(last4Bytes), 2);
//                // Extract the remaining bytes
//                actualData = extractBytes(hexBytes, 14, hexBytes.length - 18); // Exclude first 14 and last 4
//
//            }
//
//            assert openingFlag != null;
//            streamDataService.postData(openingFlag, deviceId, packetType, packetLength, packetSize, actualData, crc, closingFlag);
//            // Print the results
////            System.out.println("openingFlag: " + openingFlag);
////            System.out.println("deviceId: " + deviceId);
////            System.out.println("packetType: " + packetType);
////            System.out.println("packetNumber: " + packetNumber);
////            System.out.println("packetLength: " + packetLength);
////            System.out.println("packetSize: " + packetSize);
////            System.out.println("Actual data: " + actualData);
////            System.out.println("Device Status: " + deviceStatus);
////            System.out.println("finger Print Id: " + fingerPrintId);
////            System.out.println("login Memory: " + loginMemory);
////
////            System.out.println("config DateTime: " + configDateTime);
////            System.out.println("last Login DateTime: " + lastLoginDateTime);
////            System.out.println("next Login Offset: " + nextLoginOffset);
////            System.out.println("prev Record Count: " + prevRecordCount);
////            System.out.println("total Enrol User: " + totalEnrolUser);
////            System.out.println("total Login User: " + totalLoginUser);
////            System.out.println("total Record Push: " + totalRecordPush);
////            System.out.println("next Push Offset: " + nextPushOffset);
////            System.out.println("adminId Tag Number: " + adminIdTagNumber);
////            System.out.println("deviceId Number: " + deviceIdNumber);
//
////            System.out.println("crc: " + crc);
////            System.out.println("closingFlag: " + closingFlag);
////            System.out.println("==============================================");
//
//        }
////        System.out.println("==============================================");
////        System.out.println("openingFlag: " + openingFlag);
////        System.out.println("deviceId: " + deviceId);
////        System.out.println("packetNoCount: " + packetNoCount);
////        System.out.println("packetNo: " + packetNo);
//////        System.out.println("crc: " + crc);
////        System.out.println("closingFlag: " + closingFlag);
//
//
//
////        for (String frame : frames) {
////
////        String[] hexBytes = frame.split(" ");
////
////        // Extract the first 14 bytes
////         first14Bytes = extractBytes(hexBytes, 0, 14);
////
////            // Extract the opening flag bytes
////             openingFlag = extractBytes(hexBytes, 0, 2);
////
////            // Extract the device Id bytes
////             deviceId = extractBytes(hexBytes, 0, 8);
////
////            // Extract the packet number bytes
////             packetNoCount = extractBytes(hexBytes, 0, 1);
////
////            // Extract the packet number bytes
////             packetNo = extractBytes(hexBytes, 0, 1);
////
////            // Extract the CRC bytes
////             crc = extractBytes(hexBytes, 0, 1);
////
////            // Extract the opening flag bytes
////             closingFlag = extractBytes(hexBytes, 0, 2);
////
////
////
////        // Extract the last 4 bytes
////         last4Bytes = extractBytes(hexBytes, hexBytes.length - 4, 4);
////
////        // Extract the remaining bytes
////         remainingBytes = extractBytes(hexBytes, 14, hexBytes.length - 18); // Exclude first 14 and last 4
////
////        // Print the results
////        System.out.println("First 14 Bytes: " + first14Bytes);
////        System.out.println("Last 4 Bytes: " + last4Bytes);
////        System.out.println("Remaining Bytes: " + remainingBytes);
////        }
////
////        streamDataService.insertHexData(hexData, frames.get(0), frames.get(1), frames.get(2));
//    }

}

