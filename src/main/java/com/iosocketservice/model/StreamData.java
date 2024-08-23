//package com.iosocketservice.model;
//
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;
//
//@Getter
//@Document(collection = "Stream_data")
//public class StreamData {
//
//    // Getters and Setters
//    @Id
//    private String id;  // The unique document identifier
//    @Setter
//    private String configuration;
//    @Setter
//    private String enrollment;
//    @Setter
//    private String attendance;
//    @Setter
//    private String hexData;
//
//    // Constructor
//    public StreamData(String hexData, String configuration, String enrollment, String attendance) {
//        this.hexData = hexData;
//        this.configuration = configuration;
//        this.enrollment = enrollment;
//        this.attendance = attendance;
//    }
//}
//
