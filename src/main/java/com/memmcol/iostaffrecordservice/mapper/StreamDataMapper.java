package com.memmcol.iostaffrecordservice.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StreamDataMapper {

    @Insert("INSERT * INTO Stream_data")
    void saveRawData(String hexString);

    void postStreamData();

    @Insert("INSERT INTO ConfigurationRawFrame(openingFlag, deviceId, packetType, packetNumber, " +
            "packetLength, packetSize, deviceStatus, fingerPrintId, loginMemory, configDateTime, " +
            "lastLoginDateTime, nextLoginOffset, prevRecordCount, totalEnrolUser, totalLoginUser," +
            "totalRecordPush, nextPushOffset, adminIdTagNumber, deviceIdNumber, crc, closingFlag)" +
            "VALUES(#{openingFlag}, #{deviceId}, #{packetType}, #{packetNumber}, #{packetLength}," +
            "#{packetSize}, #{deviceStatus}, #{fingerPrintId}, #{loginMemory}, #{configDateTime}," +
            "#{lastLoginDateTime}, #{nextLoginOffset}, #{prevRecordCount}, #{totalEnrolUser}," +
            "#{totalLoginUser}, #{totalRecordPush}, #{nextPushOffset}, #{adminIdTagNumber}," +
            "#{deviceIdNumber}, #{crc}, #{closingFlag})")
    void insertConfigurationHexData(
            String openingFlag, String deviceId, String packetType, String packetNumber,
            String packetLength, String packetSize, String deviceStatus, String fingerPrintId,
            String loginMemory, String configDateTime, String lastLoginDateTime, String nextLoginOffset,
            String prevRecordCount, String totalEnrolUser, String totalLoginUser, String totalRecordPush,
            String nextPushOffset, String adminIdTagNumber, String deviceIdNumber, String crc, String closingFlag);

    @Insert("INSERT INTO EnrollmentRawFrame(openingFlag, deviceId, packetType, packetNumber, " +
            "packetLength, packetSize, fingerPrintId, idTagNumber, dateTime, crc, closingFlag)" +
            "VALUES(#{openingFlag}, #{deviceId}, #{packetType}, #{packetNumber}, #{packetLength}," +
            "#{packetSize}, #{fingerPrintId}, #{idTagNumber}, #{dateTime}, #{crc}, #{closingFlag})")
    void insertEnrollmentHexData(
            String openingFlag, String deviceId, String packetType,
            String packetNumber, String packetLength, String packetSize,
            String fingerPrintId, String idTagNumber, String dateTime,
            String crc, String closingFlag);

    @Insert("INSERT INTO AttendanceRawFrame(openingFlag, deviceId, packetType, packetNumber, " +
            "packetLength, packetSize, fingerPrintId, loginType, dateTime, crc, closingFlag)" +
            "VALUES(#{openingFlag}, #{deviceId}, #{packetType}, #{packetNumber}, #{packetLength}," +
            "#{packetSize}, #{fingerPrintId}, #{loginType}, #{dateTime}, #{crc}, #{closingFlag})")
    void insertAttendanceHexData(
            String openingFlag, String deviceId, String packetType, String packetNumber,
            String packetLength, String packetSize, String fingerPrintId, String loginType,
            String dateTime, String crc, String closingFlag);

    @Insert("INSERT INTO ConfigurationDecFrame(openingFlag, deviceId, packetType, packetNumber," +
            "packetLength, packetSize, deviceStatus, fingerPrintId, loginMemory, configDateTime," +
            "lastLoginDateTime, nextLoginOffset, prevRecordCount, totalEnrolUser, totalLoginUser," +
            "totalRecordPush, nextPushOffset, adminIdTagNumber, deviceIdNumber, crc, closingFlag)" +
            "VALUES(#{openingFlagDec}, #{deviceIdDec}, #{packetType}, #{packetNumber}, #{packetLength}," +
            "#{packetSizeDec}, #{deviceStatusDec}, #{fingerPrintIdDec}, #{loginMemoryDec}, #{configDateTimeDec}," +
            "#{lastLoginDateTimeDec}, #{nextLoginOffsetDec}, #{prevRecordCountDec}, #{totalEnrolUserDec}," +
            "#{totalLoginUserDec}, #{totalRecordPushDec}, #{nextPushOffsetDec}, #{adminIdTagNumberDec}," +
            "#{deviceIdNumberDec}, #{crcDec}, #{closingFlagDec})")
    void insertConfigurationDecData(
            String openingFlagDec, String deviceIdDec, String packetType,
            String packetNumber, String packetLength, String packetSizeDec,
            String deviceStatusDec, String fingerPrintIdDec, String loginMemoryDec,
            String configDateTimeDec, String lastLoginDateTimeDec, String nextLoginOffsetDec,
            String prevRecordCountDec, String totalEnrolUserDec, String totalLoginUserDec,
            String totalRecordPushDec, String nextPushOffsetDec, String adminIdTagNumberDec,
            String deviceIdNumberDec, String crcDec, String closingFlagDec);

    @Insert("INSERT INTO EnrollmentDecFrame(openingFlag, deviceId, packetType, packetNumber," +
            "packetLength, packetSize, fingerPrintId, idTagNumber, dateTime, crc, closingFlag)" +
            "VALUES(#{openingFlagDec}, #{deviceIdDec}, #{packetType}, #{packetNumber}, #{packetLength}," +
            "#{packetSizeDec}, #{fingerPrintIdDec}, #{idTagNumberDec}, #{dateTimeDec}, #{crcDec}, #{closingFlagDec})")
    void insertEnrollmentDecData(
            String openingFlagDec, String deviceIdDec, String packetType, String packetNumber,
            String packetLength, String packetSizeDec, String fingerPrintIdDec, String idTagNumberDec,
            String dateTimeDec, String crcDec, String closingFlagDec);

    @Insert("INSERT INTO AttendanceDecFrame(openingFlag, deviceId, packetType, packetNumber," +
            "packetLength, packetSize, fingerPrintId, loginType, dateTime, crc, closingFlag)" +
            "VALUES(#{openingFlagDec}, #{deviceIdDec}, #{packetType}, #{packetNumber}, #{packetLength}," +
            "#{packetSizeDec}, #{fingerPrintIdDec}, #{loginTypeDec}, #{dateTimeDec}, #{crcDec}, #{closingFlagDec})")
    void insertAttendanceDecData(
            String openingFlagDec, String deviceIdDec, String packetType, String packetNumber,
            String packetLength, String packetSizeDec, String fingerPrintIdDec, String loginTypeDec,
            String dateTimeDec, String crcDec, String closingFlagDec);
}
