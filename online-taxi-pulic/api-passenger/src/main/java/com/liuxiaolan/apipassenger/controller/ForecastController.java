package com.liuxiaolan.apipassenger.controller;

import com.liuxiaolan.apipassenger.service.ForecastPriceService;
import com.liuxiaolan.internalcommon.dto.ResponseResult;
import com.liuxiaolan.internalcommon.request.ForecastPriceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
public class ForecastController{

        @Autowired
        private ForecastPriceService forecastPriceService;
        /** 方法描述 根据出发地与目的地的经纬度预估价格
        * @return 
        * @author liuxiaolan
        * @date 2023/4/23
        */
        @PostMapping("/forecast-price")
        public ResponseResult forecastPrice(@RequestBody ForecastPriceDTO forecastPriceDTO){
                log.info("出发地经纬度"+forecastPriceDTO.toString());
                //出发 经度
                String depLongitude = forecastPriceDTO.getDepLongitude();
                //出发 纬度
                String depLatitude = forecastPriceDTO.getDepLatitude();
                //目的 经度
                String destLongitude = forecastPriceDTO.getDestLongitude();
                //目的  纬度
                String destLatitude = forecastPriceDTO.getDestLatitude();
                return forecastPriceService.forecastPrice(depLongitude,depLatitude,destLongitude,destLatitude);
        }








}
