package com.iosocketservice.service;

import com.iosocketservice.mapper.StreamDataRepository;
import com.iosocketservice.model.StreamData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StreamDataService {

    private final StreamDataRepository repository;

    @Autowired
    public StreamDataService(StreamDataRepository repository) {
        this.repository = repository;
    }

    public StreamData insertHexData(String hexData, String configuration, String enrollment, String attendance) {
        // Create a new StreamData object and insert it into the collection
        StreamData streamData = new StreamData(hexData, configuration, enrollment, attendance);
        return repository.save(streamData);
    }
}

