package com.iosocketservice.mapper;

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
}
