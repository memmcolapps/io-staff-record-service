package com.iosocketservice.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "Stream_data")
public class StreamData {

    // Getters and Setters
    @Id
    private String id;  // The unique document identifier
    @Setter
    private String hexString;

    // Constructor
    public StreamData(String hexString) {
        this.hexString = hexString;
    }

}

