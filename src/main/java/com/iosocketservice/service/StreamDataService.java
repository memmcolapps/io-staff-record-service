package com.iosocketservice.service;

import com.iosocketservice.mapper.StreamDataMapper;
//import com.iosocketservice.mapper.StreamDataRepository;
//import com.iosocketservice.model.StreamData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StreamDataService {

//    private final StreamDataRepository repository;

    @Autowired
    private StreamDataMapper mapper;

//    @Autowired
//    public StreamDataService(StreamDataRepository repository) {
//        this.repository = repository;
//    }
//
//    public StreamData insertHexData(String hexData, String configuration, String enrollment, String attendance) {
//        // Create a new StreamData object and insert it into the collection
//        StreamData streamData = new StreamData(hexData, configuration, enrollment, attendance);
//        return repository.save(streamData);
//    }

    public void postData(String openingFlag, String deviceId, String packetType, String packetLength, String packetSize, String actualData, String crc, String closingFlag) {

        // Remove spaces from the hex string
        String hexNumber = openingFlag.replaceAll("\\s", "");

        try {
            // Convert hex to decimal
            int decimalValue = Integer.parseInt(hexNumber, 16);
            System.out.println("Hexadecimal " + hexNumber + " in decimal is " + decimalValue);
        } catch (NumberFormatException e) {
            System.out.println("Invalid hexadecimal number");
        }


//        mapper.postStreamData();
    }
}

