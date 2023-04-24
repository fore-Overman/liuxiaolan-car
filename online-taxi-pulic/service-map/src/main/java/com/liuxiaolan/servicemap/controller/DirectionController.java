package com.liuxiaolan.servicemap.controller;

import com.liuxiaolan.internalcommon.dto.ResponseResult;
import com.liuxiaolan.internalcommon.request.ForecastPriceDTO;
import com.liuxiaolan.servicemap.service.DirectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DirectionController {

    @Autowired
    private DirectionService directionService;


    /** 方法描述 根据起点终点的经纬度 计算距离及时长 调用高德API
    * @return 
    * @author liuxiaolan
    * @date 2023/4/24
    */
    @GetMapping("/direction/driving")
    public ResponseResult driving(@RequestBody ForecastPriceDTO forecastPriceDTO){

        String depLongitude = forecastPriceDTO.getDepLongitude();
        //出发 纬度
        String depLatitude = forecastPriceDTO.getDepLatitude();
        //目的 经度
        String destLongitude = forecastPriceDTO.getDestLongitude();
        //目的  纬度
        String destLatitude = forecastPriceDTO.getDestLatitude();

        return directionService.driving(depLongitude,depLatitude,destLongitude,destLatitude);
    }




}
