package com.memmcol.iostaffrecordservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("memmcol/iosocketwebservice/api/v1/")
public class StreamDataController {

    @GetMapping
    public String get(){
        return "hello";
    }
}
