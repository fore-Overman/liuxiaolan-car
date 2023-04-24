package com.liuxiaolan.servicemap.service;

import com.liuxiaolan.internalcommon.dto.ResponseResult;
import com.liuxiaolan.internalcommon.responese.DirectionResponse;
import com.liuxiaolan.servicemap.remote.MapDirectionClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DirectionService {
    
        @Autowired
        private MapDirectionClient mapDirectionClient;


    /** 方法描述 通过调用高德API 获取距离（米）和时长（分钟）
    * @return
    * @author liuxiaolan
    * @date 2023/4/24
    */
    public ResponseResult driving(String depLongitude,String depLatitude,String destLongitude,String destLatitude){
        //通过调用高德的API 来获取 距离和时长
        DirectionResponse direction = mapDirectionClient.direction(depLongitude,depLatitude,destLongitude,destLatitude);
        return  ResponseResult.success(direction);
    }








}
