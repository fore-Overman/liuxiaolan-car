package com.liuxiaolan.serviceprice.service;

import com.liuxiaolan.internalcommon.dto.ResponseResult;
import com.liuxiaolan.internalcommon.responese.ForecastPriceResponse;
import org.springframework.stereotype.Service;

@Service
public class ForecastPriceService {


    public ResponseResult forecastPrice(String depLongitude,String depLatitude,
                                        String destLongitude,String destLatitude){


        //调用地图服务 查询距离和时长

        //读取计价规则


        //根据距离时长  及  计价规则 计算价格



        ForecastPriceResponse forecastPriceResponse = new ForecastPriceResponse();
        forecastPriceResponse.setPrice("23.44");
        return ResponseResult.success(forecastPriceResponse);
    }







}
