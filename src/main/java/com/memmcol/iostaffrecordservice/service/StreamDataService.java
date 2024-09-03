package com.memmcol.iostaffrecordservice.service;

import com.memmcol.iostaffrecordservice.mapper.StreamDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StreamDataService {

//    private final StreamDataRepository repository;

    @Autowired
    private StreamDataMapper mapper;

    public void postConfigurationHexRawData(String openingFlag, String deviceId, String packetType, String packetNumber,
                         String packetLength, String packetSize, String deviceStatus, String fingerPrintId,
                         String loginMemory, String configDateTime, String lastLoginDateTime, String nextLoginOffset,
                         String prevRecordCount, String totalEnrolUser, String totalLoginUser, String totalRecordPush,
                         String nextPushOffset, String adminIdTagNumber, String deviceIdNumber, String crc, String closingFlag) {
        mapper.insertConfigurationHexData(
                openingFlag, deviceId, packetType, packetNumber,
                packetLength, packetSize, deviceStatus, fingerPrintId,
                loginMemory, configDateTime, lastLoginDateTime, nextLoginOffset,
                prevRecordCount, totalEnrolUser, totalLoginUser, totalRecordPush,
                nextPushOffset, adminIdTagNumber, deviceIdNumber, crc, closingFlag
        );
    }

    public void postEnrollmentHexRawData(
            String openingFlag, String deviceId, String packetType, String packetNumber,
            String packetLength, String packetSize, String fingerPrintId, String idTagNumber,
            String dateTime, String crc, String closingFlag) {
        mapper.insertEnrollmentHexData(
                openingFlag, deviceId, packetType, packetNumber,
                packetLength, packetSize, fingerPrintId, idTagNumber,
                dateTime, crc, closingFlag
        );
    }

    public void postAttendanceHexRawData(
            String openingFlag, String deviceId, String packetType, String packetNumber,
            String packetLength, String packetSize, String fingerPrintId, String loginType,
            String dateTime, String crc, String closingFlag) {
        mapper.insertAttendanceHexData(
                openingFlag, deviceId, packetType, packetNumber,
                packetLength, packetSize, fingerPrintId, loginType,
                dateTime, crc, closingFlag
        );
    }

    public void postConfigurationDecimalData(
            String openingFlagDec, String deviceIdDec, String packetType,
            String packetNumber, String packetLength, String packetSizeDec,
            String deviceStatusDec, String fingerPrintIdDec, String loginMemoryDec,
            String configDateTimeDec, String lastLoginDateTimeDec, String nextLoginOffsetDec,
            String prevRecordCountDec, String totalEnrolUserDec, String totalLoginUserDec,
            String totalRecordPushDec, String nextPushOffsetDec, String adminIdTagNumberDec,
            String deviceIdNumberDec, String crcDec, String closingFlagDec) {
        mapper.insertConfigurationDecData(
                openingFlagDec, deviceIdDec, packetType,
                packetNumber, packetLength, packetSizeDec,
                deviceStatusDec, fingerPrintIdDec, loginMemoryDec,
                configDateTimeDec, lastLoginDateTimeDec, nextLoginOffsetDec,
                prevRecordCountDec, totalEnrolUserDec, totalLoginUserDec,
                totalRecordPushDec, nextPushOffsetDec, adminIdTagNumberDec,
                deviceIdNumberDec, crcDec, closingFlagDec
        );
    }

    public void postEnrollmentDecimalData(
            String openingFlagDec, String deviceIdDec, String packetType, String packetNumber,
            String packetLength, String packetSizeDec, String fingerPrintIdDec, String idTagNumberDec,
            String dateTimeDec, String crcDec, String closingFlagDec) {
        mapper.insertEnrollmentDecData(
                openingFlagDec, deviceIdDec, packetType, packetNumber,
                packetLength, packetSizeDec, fingerPrintIdDec, idTagNumberDec,
                dateTimeDec, crcDec, closingFlagDec);
    }

    public void postAttendanceDecimalData(
            String openingFlagDec, String deviceIdDec, String packetType, String packetNumber,
            String packetLength, String packetSizeDec, String fingerPrintIdDec, String loginTypeDec,
            String dateTimeDec, String crcDec, String closingFlagDec) {
        if(Integer.parseInt(loginTypeDec) == 1){
            loginTypeDec = "Login";
        } else if(Integer.parseInt(loginTypeDec) == 2){
            loginTypeDec = "Logout";
        } else if(Integer.parseInt(loginTypeDec) == 3){
            loginTypeDec = "Step-in";
        } else {
            loginTypeDec = "Step-out";
        }
        mapper.insertAttendanceDecData(
                openingFlagDec, deviceIdDec, packetType, packetNumber,
                packetLength, packetSizeDec, fingerPrintIdDec, loginTypeDec,
                dateTimeDec, crcDec, closingFlagDec);
    }
}

