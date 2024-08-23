package com.iosocketservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


public class HexSplitDecimalHandler {
    private static final Logger log = LoggerFactory.getLogger(HexSplitDecimalHandler.class);

    private final StreamDataService streamDataService;

    public HexSplitDecimalHandler(StreamDataService streamDataService) {
        this.streamDataService = streamDataService;
    }

    // Utility method to convert byte array to HEX string
    public String bytesToHex(byte[] bytes) {
        StringBuilder hexData = new StringBuilder();
        System.out.println("length of byte: "+bytes.length);
        for (int i = 0; i < bytes.length; i++) {
            if (i > 0) {
                hexData.append(" ");
            }
            hexData.append(String.format("%02X", bytes[i]));
        }
        log.info("Received HEX data length {}", hexData.length());
        handleSplit(hexData.toString());
        return hexData.toString();
    }
    public void handleSplit(String hexData) {
        List<String> frames = splitHexFrames(hexData);
        String openingFlag = null;
        String deviceId = null;
        String packetNumber = null;
        String packetLength = null;
        String packetSize = null;
        String crc = null;
        String closingFlag = null;
        String fingerPrintId = null;
        String loginMemory = null;
        String configDateTime = null;
        String lastLoginDateTime= null;
        String nextLoginOffset= null;
        String prevRecordCount= null;
        String totalEnrolUser = null;
        String totalLoginUser= null;
        String totalRecordPush= null;
        String nextPushOffset= null;
        String adminIdTagNumber= null;
        String deviceIdNumber = null;
        String actualData = null;
        String deviceStatus = null;
        String packetType = null;
        String idTagNumber = null;
        String dataTime = null;
        String loginType = null;
        int decimalPacketSize = 0;
        System.out.println("Frame>>>> : " + frames);
        System.out.println("Frame Size>>>> : " + frames.size());
        for(int inc = 0; inc < frames.size(); inc++){
            String[] hexBytes = frames.get(inc).split(" ");

            // Extract the opening flag bytes
            openingFlag = extractBytes(hexBytes, 0, 2);

            // Extract the device Id bytes
            deviceId = extractBytes(hexBytes, 2, 12);

            // Extract the packet number bytes
            packetType = extractBytes(hexBytes, 14, 1);

            // Extract the packet number bytes
            packetNumber = extractBytes(hexBytes, 15, 1);

            // Extract the packet number bytes
            packetLength = extractBytes(hexBytes, 16, 1);

            // Extract the packet number bytes
            packetSize = extractBytes(hexBytes, 17, 1);

            decimalPacketSize = convertHexToDecimal(packetSize);

            crc = extractBytes(hexBytes, hexBytes.length - 4, 2); // First 2 bytes

            // Extract the opening flag bytes
            closingFlag = extractBytes(hexBytes, hexBytes.length - 2, 2);

            // Process configuration bytes
            if(packetType.equals("01")){
                actualData = extractBytes(hexBytes, 18, hexBytes.length - 18); // Exclude first 14 and last 4
                deviceStatus = extractBytes(hexBytes, 18, 3);
                fingerPrintId = extractBytes(hexBytes, 21, 2);
                loginMemory = extractBytes(hexBytes, 23, 2);
                configDateTime = extractBytes(hexBytes, 25, 6);
                lastLoginDateTime = extractBytes(hexBytes, 31, 6);
                nextLoginOffset = extractBytes(hexBytes, 37, 2);
                prevRecordCount = extractBytes(hexBytes, 39, 2);
                totalEnrolUser = extractBytes(hexBytes, 41, 2);
                totalLoginUser = extractBytes(hexBytes, 43, 2);
                totalRecordPush = extractBytes(hexBytes, 45, 2);
                nextPushOffset = extractBytes(hexBytes, 47, 2);
                adminIdTagNumber = extractBytes(hexBytes, 49, 12);
                deviceIdNumber = extractBytes(hexBytes, 61, 12);

                System.out.println("openingFlag: " + openingFlag);
                System.out.println("deviceId: " + deviceId);
                System.out.println("packetType: " + packetType);
                System.out.println("packetNumber: " + packetNumber);
                System.out.println("packetLength: " + packetLength);
                System.out.println("packetSize: " + packetSize);
                System.out.println("decimal Packet Size: " + decimalPacketSize);
                System.out.println("Actual data: " + actualData);
                System.out.println("Device Status: " + deviceStatus);
                System.out.println("finger Print Id: " + fingerPrintId);
                System.out.println("login Memory: " + loginMemory);

                System.out.println("config DateTime: " + configDateTime);
                System.out.println("last Login DateTime: " + lastLoginDateTime);
                System.out.println("next Login Offset: " + nextLoginOffset);
                System.out.println("prev Record Count: " + prevRecordCount);
                System.out.println("total Enrol User: " + totalEnrolUser);
                System.out.println("total Login User: " + totalLoginUser);
                System.out.println("total Record Push: " + totalRecordPush);
                System.out.println("next Push Offset: " + nextPushOffset);
                System.out.println("adminId Tag Number: " + adminIdTagNumber);
                System.out.println("deviceId Number: " + deviceIdNumber);

                System.out.println("crc: " + crc);
                System.out.println("closingFlag: " + closingFlag);
                System.out.println("==============================================");
            } else if(packetType.equals("02")){
                // Process enrollment bytes
                actualData = extractBytes(hexBytes, 18, hexBytes.length - 18); // Exclude first 14 and last 4
                System.out.println("openingFlag: " + openingFlag);
                System.out.println("deviceId: " + deviceId);
                System.out.println("packetType: " + packetType);
                System.out.println("packetNumber: " + packetNumber);
                System.out.println("packetLength: " + packetLength);
                System.out.println("packetSize: " + packetSize);
                System.out.println("decimal Packet Size: " + decimalPacketSize);
                System.out.println("Actual data: " + actualData);

                int actualDataLength = decimalPacketSize/20;
                System.out.println("----------------:: "+actualDataLength);
                for(int i = 0; i < actualDataLength; i++){
                    int pos = i*20;
                    fingerPrintId = extractBytes(hexBytes, 18+pos, 2);
                    idTagNumber = extractBytes(hexBytes, 20+pos, 12);
                    dataTime = extractBytes(hexBytes, 32+pos, 7);
                    System.out.println("finger Print Id: " + fingerPrintId);
                    System.out.println("id Tag Number: " + idTagNumber);
                    System.out.println("DateTime: " + dataTime);
                    System.out.println("---------------------------------------------------");
                }
                System.out.println("crc: " + crc);
                System.out.println("closingFlag: " + closingFlag);
                System.out.println("==============================================");
            } else {
                // Process attendance bytes
                actualData = extractBytes(hexBytes, 18, hexBytes.length - 18); // Exclude first 14 and last 4
                System.out.println("openingFlag: " + openingFlag);
                System.out.println("deviceId: " + deviceId);
                System.out.println("packetType: " + packetType);
                System.out.println("packetNumber: " + packetNumber);
                System.out.println("packetLength: " + packetLength);
                System.out.println("packetSize: " + packetSize);
                System.out.println("decimal Packet Size: " + decimalPacketSize);
                System.out.println("Actual data: " + actualData);
                int actualDataLength = decimalPacketSize/10;
                System.out.println("----------------:: "+actualDataLength);
                for(int i = 0; i < actualDataLength; i++){
                    int pos = i*10;
                    fingerPrintId = extractBytes(hexBytes, 18+pos, 2);
                    loginType = extractBytes(hexBytes, 20+pos, 1);
                    dataTime = extractBytes(hexBytes, 21+pos, 7);
                    System.out.println("finger Print Id: " + fingerPrintId);
                    System.out.println("login Type: " + loginType);
                    System.out.println("DateTime: " + dataTime);
                    System.out.println("---------------------------------------------------");
                }
                System.out.println("crc: " + crc);
                System.out.println("closingFlag: " + closingFlag);
            }

            assert openingFlag != null;
            streamDataService.postData(openingFlag, deviceId, packetType, packetLength, packetSize, actualData, crc, closingFlag);

        }
    }

    public static int convertHexToDecimal(String packetSize){
        String hexNumber = packetSize.replaceAll("\\s", "");
        int decimalValue = 0;
        try {
            // Convert hex to decimal
            decimalValue = Integer.parseInt(hexNumber, 16);
            System.out.println("Hexadecimal " + hexNumber + " in decimal is " + decimalValue);
        } catch (NumberFormatException e) {
            System.out.println("Invalid hexadecimal number");
        }
        return decimalValue;
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
}
