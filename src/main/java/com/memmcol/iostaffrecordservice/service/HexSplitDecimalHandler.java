package com.memmcol.iostaffrecordservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class HexSplitDecimalHandler {
    private static final Logger log = LoggerFactory.getLogger(HexSplitDecimalHandler.class);
    String openingFlag = null;
    String openingFlagDec = null;
    String deviceId = null;
    String deviceIdDec = null;
    String packetNumber = null;
    String packetLength = null;
    String packetSize = null;
    String packetSizeDec = null;
    String crc = null;
    String crcDec = null;
    String closingFlag = null;
    String closingFlagDec = null;
    String fingerPrintId = null;
    String fingerPrintIdDec = null;
    String loginMemory = null;
    String loginMemoryDec = null;
    String configDateTime = null;
    String configDateTimeDec = null;
    String lastLoginDateTime = null;
    String lastLoginDateTimeDec = null;
    String nextLoginOffset= null;
    String nextLoginOffsetDec= null;
    String prevRecordCount= null;
    String prevRecordCountDec= null;
    String totalEnrolUser = null;
    String totalEnrolUserDec = null;
    String totalLoginUser = null;
    String totalLoginUserDec = null;
    String totalRecordPush = null;
    String totalRecordPushDec = null;
    String nextPushOffset = null;
    String nextPushOffsetDec = null;
    String adminIdTagNumber = null;
    String adminIdTagNumberDec = null;
    String deviceIdNumber = null;
    String deviceIdNumberDec = null;
    String actualData = null;
    String actualDataDec = null;
    String deviceStatus = null;
    String deviceStatusDec = null;
    String packetType = null;
    String idTagNumber = null;
    String idTagNumberDec = null;
    String dateTime = null;
    String dateTimeDec = null;
    String loginType = null;
    String loginTypeDec = null;
    String decimalPacketSize = null;
    String decimalPacketSizeDec = null;
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
//        log.info("Received HEX data length {}", hexData.length());
        handleSplit(hexData.toString());
        return hexData.toString();
    }
    public void handleSplit(String hexData) {
        List<String> frames = splitHexFrames(hexData);

        for (String frame : frames) {
            String[] hexBytes = frame.split(" ");

            // Extract the opening flag bytes
            openingFlag = extractBytes(hexBytes, 0, 2);
            openingFlagDec = convertHexToDecimal(openingFlag);

            // Extract the device Id bytes
            deviceId = extractBytes(hexBytes, 2, 12);
            deviceIdDec = convertHexToDecimal(deviceId);

            // Extract the packet number bytes
            packetType = extractBytes(hexBytes, 14, 1);

            // Extract the packet number bytes
            packetNumber = extractBytes(hexBytes, 15, 1);

            // Extract the packet number bytes
            packetLength = extractBytes(hexBytes, 16, 1);

            // Extract the packet number bytes
            packetSize = extractBytes(hexBytes, 17, 1);
            packetSizeDec = convertHexToDecimal(packetSize);

            decimalPacketSize = convertHexToDecimal(packetSize);
            decimalPacketSizeDec = convertHexToDecimal(decimalPacketSize);

            crc = extractBytes(hexBytes, hexBytes.length - 4, 2); // First 2 bytes
            crcDec = convertHexToDecimal(crc);

            // Extract the opening flag bytes
            closingFlag = extractBytes(hexBytes, hexBytes.length - 2, 2);
            closingFlagDec = convertHexToDecimal(closingFlag);

            // Process configuration frame
            if (packetType.equals("01")) {
                actualData = extractBytes(hexBytes, 18, hexBytes.length - 18); // Exclude first 14 and last 4
                deviceStatus = extractBytes(hexBytes, 18, 3);
                deviceStatusDec = convertHexToDecimal(deviceStatus);
                fingerPrintId = extractBytes(hexBytes, 21, 2);
                fingerPrintIdDec = convertHexToDecimal(fingerPrintId);
                loginMemory = extractBytes(hexBytes, 23, 2);
                loginMemoryDec = convertHexToDecimal(loginMemory);
                configDateTime = extractBytes(hexBytes, 25, 6);
                configDateTimeDec = convertHexToDecimal(configDateTime);
                lastLoginDateTime = extractBytes(hexBytes, 31, 6);
                lastLoginDateTimeDec = convertHexToDecimal(lastLoginDateTime);
                nextLoginOffset = extractBytes(hexBytes, 37, 2);
                nextLoginOffsetDec = convertHexToDecimal(nextLoginOffset);
                prevRecordCount = extractBytes(hexBytes, 39, 2);
                prevRecordCountDec = convertHexToDecimal(prevRecordCount);
                totalEnrolUser = extractBytes(hexBytes, 41, 2);
                totalEnrolUserDec = convertHexToDecimal(totalEnrolUser);
                totalLoginUser = extractBytes(hexBytes, 43, 2);
                totalLoginUserDec = convertHexToDecimal(totalLoginUser);
                totalRecordPush = extractBytes(hexBytes, 45, 2);
                totalRecordPushDec = convertHexToDecimal(totalRecordPush);
                nextPushOffset = extractBytes(hexBytes, 47, 2);
                nextPushOffsetDec = convertHexToDecimal(nextPushOffset);
                adminIdTagNumber = extractBytes(hexBytes, 49, 12);
                adminIdTagNumberDec = convertHexToDecimal(adminIdTagNumber);
                deviceIdNumber = extractBytes(hexBytes, 61, 12);
                deviceIdNumberDec = convertHexToDecimal(deviceIdNumber);

//                System.out.println("openingFlag: " + openingFlag);
//                System.out.println("openingFlagDec: " + openingFlagDec);
//                System.out.println("deviceId: " + deviceId);
//                System.out.println("deviceIdDec: " + deviceIdDec);
//                System.out.println("packetType: " + packetType);
//                System.out.println("packetNumber: " + packetNumber);
//                System.out.println("packetLength: " + packetLength);
//                System.out.println("packetSize: " + packetSize);
//                System.out.println("packetSizeDec: " + packetSizeDec);
//                System.out.println("decimal Packet Size: " + decimalPacketSize);
//                System.out.println("Actual data: " + actualData);
//                System.out.println("Device Status: " + deviceStatus);
//                System.out.println("Device Status Dec: " + deviceStatusDec);
//                System.out.println("finger Print Id: " + fingerPrintId);
//                System.out.println("finger Print Id Dec: " + fingerPrintIdDec);
//                System.out.println("login Memory: " + loginMemory);
//                System.out.println("login Memory Dec: " + loginMemoryDec);
//
//                System.out.println("config DateTime: " + configDateTime);
//                System.out.println("config DateTime Dec: " + configDateTimeDec);
//                System.out.println("last Login DateTime: " + lastLoginDateTime);
//                System.out.println("last Login DateTime Dec: " + lastLoginDateTimeDec);
//                System.out.println("next Login Offset: " + nextLoginOffset);
//                System.out.println("next Login Offset Dec: " + nextLoginOffsetDec);
//                System.out.println("prev Record Count: " + prevRecordCount);
//                System.out.println("prev Record Count Dec: " + prevRecordCountDec);
//                System.out.println("total Enrol User: " + totalEnrolUser);
//                System.out.println("total Enrol User Dec: " + totalEnrolUserDec);
//                System.out.println("total Login User: " + totalLoginUser);
//                System.out.println("total Login User Dec: " + totalLoginUserDec);
//                System.out.println("total Record Push: " + totalRecordPush);
//                System.out.println("total Record Push Dec: " + totalRecordPushDec);
//                System.out.println("next Push Offset: " + nextPushOffset);
//                System.out.println("next Push Offset Dec: " + nextPushOffsetDec);
//                System.out.println("adminId Tag Number: " + adminIdTagNumber);
//                System.out.println("adminId Tag Number Dec: " + adminIdTagNumberDec);
//                System.out.println("deviceId Number: " + deviceIdNumber);
//                System.out.println("deviceId Number Dec: " + deviceIdNumberDec);
//
//                System.out.println("crc: " + crc);
//                System.out.println("crc Dec: " + crcDec);
//                System.out.println("closingFlag: " + closingFlag);
//                System.out.println("closingFlagDec: " + closingFlagDec);
//                System.out.println("==============================================");
                streamDataService.postConfigurationHexRawData(
                        openingFlag, deviceId, packetType, packetNumber,
                        packetLength, packetSize, deviceStatus, fingerPrintId,
                        loginMemory, configDateTime, lastLoginDateTime, nextLoginOffset,
                        prevRecordCount, totalEnrolUser, totalLoginUser, totalRecordPush,
                        nextPushOffset, adminIdTagNumber, deviceIdNumber, crc, closingFlag);
                streamDataService.postConfigurationDecimalData(
                        openingFlag, deviceIdDec, packetType, packetNumber,
                        packetLength, packetSizeDec, deviceStatus, fingerPrintId,
                        loginMemoryDec, configDateTimeDec, lastLoginDateTimeDec, nextLoginOffsetDec,
                        prevRecordCountDec, totalEnrolUserDec, totalLoginUserDec, totalRecordPushDec,
                        nextPushOffsetDec, adminIdTagNumber, deviceIdNumber, crc, closingFlag
                );

            } else if (packetType.equals("02")) {
                // Process enrollment frame
                actualData = extractBytes(hexBytes, 18, hexBytes.length - 18); // Exclude first 14 and last 4
//                System.out.println("openingFlag: " + openingFlag);
//                System.out.println("deviceId: " + deviceId);
//                System.out.println("packetType: " + packetType);
//                System.out.println("packetNumber: " + packetNumber);
//                System.out.println("packetLength: " + packetLength);
//                System.out.println("packetSize: " + packetSize);
//                System.out.println("decimal Packet Size: " + decimalPacketSize);
//                System.out.println("Actual data: " + actualData);

                int actualDataLength = Integer.valueOf(decimalPacketSize) / 21;
                System.out.println("---------------- actualDataLength:: " + actualDataLength);
                for (int i = 0; i < actualDataLength; i++) {
                    int pos = i * 21;
                    fingerPrintId = extractBytes(hexBytes, 18 + pos, 2);
                    fingerPrintIdDec = convertHexToDecimal(fingerPrintId);
                    idTagNumber = extractBytes(hexBytes, 20 + pos, 12);
                    idTagNumberDec = convertHexToDecimal(idTagNumber);
                    dateTime = extractBytes(hexBytes, 32 + pos, 7);
                    dateTimeDec = convertHexToDecimal(dateTime);
//                    System.out.println("finger Print Id: " + fingerPrintId);
//                    System.out.println("id Tag Number: " + idTagNumber);
//                    System.out.println("DateTime: " + dateTime);
//                    System.out.println("---------------------------------------------------");
                    streamDataService.postEnrollmentHexRawData(
                            openingFlag, deviceId, packetType, packetNumber,
                            packetLength, packetSize, fingerPrintId,
                            idTagNumber, dateTime, crc, closingFlag);
                    streamDataService.postEnrollmentDecimalData(
                            openingFlag, deviceIdDec, packetType, packetNumber,
                            packetLength, packetSizeDec, fingerPrintId,
                            idTagNumber, dateTimeDec, crc, closingFlag
                    );
                }
//                System.out.println("crc: " + crc);
//                System.out.println("closingFlag: " + closingFlag);
//                System.out.println("====================----==========================");
            } else {
                // Process attendance frame
                actualData = extractBytes(hexBytes, 18, hexBytes.length - 18); // Exclude first 14 and last 4
//                System.out.println("openingFlag: " + openingFlag);
//                System.out.println("deviceId: " + deviceId);
//                System.out.println("packetType: " + packetType);
//                System.out.println("packetNumber: " + packetNumber);
//                System.out.println("packetLength: " + packetLength);
//                System.out.println("packetSize: " + packetSize);
//                System.out.println("decimal Packet Size: " + decimalPacketSize);
//                System.out.println("Actual data: " + actualData);
                int actualDataLength = Integer.valueOf(decimalPacketSize) / 10;
                System.out.println("----------------attendance actualDataLength:: " + actualDataLength);
                for (int i = 0; i < actualDataLength; i++) {
                    int pos = i * 10;
                    fingerPrintId = extractBytes(hexBytes, 18 + pos, 2);
                    fingerPrintIdDec = convertHexToDecimal(fingerPrintId);
                    loginType = extractBytes(hexBytes, 20 + pos, 1);
                    loginTypeDec = convertHexToDecimal(loginType);
                    dateTime = extractBytes(hexBytes, 21 + pos, 7);
                    dateTimeDec = convertHexToDecimal(dateTime);
//                    System.out.println("openingFlag:: " + openingFlag);
//                    System.out.println("deviceId:: " + deviceId);
//                    System.out.println("packetType:: " + packetType);
//                    System.out.println("packetNumber:: " + packetNumber);
//                    System.out.println("packetLength:: " + packetLength);
//                    System.out.println("packetSize:: " + packetSize);
//                    System.out.println("decimal Packet Size:: " + decimalPacketSize);
//                    System.out.println("finger PrintId Dec:: " + fingerPrintIdDec);
////                    System.out.println("Actual data:: " + actualData);
//                    System.out.println("finger Print Id: " + fingerPrintId);
//                    System.out.println("login Type: " + loginType);
//                    System.out.println("DateTime: " + dateTime);
//                    System.out.println("---------------------------------------------------");
                    streamDataService.postAttendanceHexRawData(
                            openingFlag, deviceId, packetType, packetNumber,
                            packetLength, packetSize, fingerPrintId,
                            loginType, dateTime, crc, closingFlag);
                    streamDataService.postAttendanceDecimalData(
                            openingFlag, deviceIdDec, packetType, packetNumber,
                            packetLength, packetSizeDec, fingerPrintId,
                            loginTypeDec, dateTimeDec, crc, closingFlag
                    );
                }
                System.out.println("crc:: " + crc);
                System.out.println("closingFlag:: " + closingFlag);
            }

            assert openingFlag != null;

        }
    }

    public static String convertHexToDecimal(String packetSize) {
        // Split the hex string by spaces to process each hex segment individually
        String[] hexSegments = packetSize.split("\\s+");

        StringBuilder decimalResult = new StringBuilder();

        if (hexSegments.length == 7 || hexSegments.length == 6) {
            int currentYear = Year.now().getValue();
            int currentCentury = currentYear / 100; // Determine the current century dynamically
            String year = Objects.equals(hexSegments[0], "00") ? "00"+hexSegments[0] : currentCentury + hexSegments[0]; // Prefix with the current century

//          String year = "20" + hexSegments[0]; // Prefix with 20 for 2000s century
            String month = hexSegments[1];

            String day = hexSegments[2];
            String dayOfWeek = hexSegments[3];
            String hour = hexSegments[4];
            String minute = hexSegments[5];
            String second = hexSegments.length == 6 ? "00" : hexSegments[6];
            System.out.println("hexSegments: "+ hexSegments.length);
            System.out.println("second: "+ second);
            String formattedDateTime = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
            System.out.println("formattedDateTime: "+ formattedDateTime);
            return formattedDateTime;
        }

        for (String hexSegment : hexSegments) {
            try {
                BigInteger decimalValue = new BigInteger(hexSegment, 16);
                // Append the decimal value to the result string
                decimalResult.append(decimalValue.toString());
            } catch (NumberFormatException e) {
                return "Invalid hexadecimal number";
            }
        }

        return decimalResult.toString();
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



//Configuration
//3F F3 00 00 00 00 00 00 00 00 00 01 18 18 01 01 01 37 AA EE EE 00 04 29 F8 24 07 25 05 15 42 00 00 00 00 00 00 16 E4 00 16 00 03 00 16 00 00 15 2C 33 38 30 30 34 32 32 38 45 45 42 43 FF FF FF FF FF FF FF FF FF FF FF FF BB AA 21 12
//
//Enrollment
//3F F3 00 00 00 00 00 00 00 00 00 01 18 18 02 01 01 3F 00 01 33 38 30 30 34 32 32 38 45 45 42 43 24 07 25 05 15 41 17 00 02 30 32 30 30 31 36 33 33 30 46 32 38 24 09 09 02 12 59 18 00 03 30 37 30 30 33 46 44 41 44 39 33 42 24 12 16 02 10 22 39 EE 0A 21 12
//
//Attendance
//3F F3 00 00 00 00 00 00 00 00 00 01 18 18 03 01 01 D2 00 01 01 24 07 25 05 15 42 17 00 01 02 24 07 25 05 15 42 40 00 01 01 24 08 13 03 10 39 35 00 01 01 24 08 23 06 10 38 41 00 01 03 24 08 23 06 10 39 09 00 01 01 24 08 23 06 10 41 25 00 01 04 24 08 23 06 10 44 45 00 01 03 24 08 23 06 10 58 02 00 01 02 24 08 23 06 11 03 57 00 01 03 24 08 23 06 11 05 16 00 01 03 24 08 23 06 11 06 47 00 01 03 24 08 23 06 11 10 19 00 01 03 24 08 23 06 11 25 37 00 02 01 24 09 09 02 13 00 09 00 01 01 24 11 05 03 12 25 57 00 01 02 24 11 05 03 12 26 27 00 01 03 24 11 05 03 12 27 14 00 01 04 24 11 05 03 12 27 56 00 03 01 24 12 16 02 10 32 53 00 03 04 24 12 16 02 10 34 06 00 03 03 24 12 16 02 10 34 37 62 A7 21 12 3F F3 00 00 00 00 00 00 00 00 00 01 18 18 03 02 01 DC 00 03 02 24 12 16 02 10 35 00 FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF DD 39 21 12
