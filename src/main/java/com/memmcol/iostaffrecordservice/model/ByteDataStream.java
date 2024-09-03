package com.memmcol.iostaffrecordservice.model;

import lombok.Getter;
import lombok.Setter;
//import org.springframework.data.annotation.Id;

@Getter
public class ByteDataStream {
//    @Id
    private int id;
    @Setter
    private String openingFlag;
    @Setter
    private String packetType;
    @Setter
    private  String packetLength;
    @Setter
    private String packetNumber;
    @Setter
    private String packetSize;
    @Setter
    private String ActualSize;
    @Setter
    private String crc;
    @Setter
    private String closingFlag;
}
