package com.iosocketservice.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RawData {

    @Insert("INSERT * INTO Stream_data")
    void saveRawData(String hexString);
}