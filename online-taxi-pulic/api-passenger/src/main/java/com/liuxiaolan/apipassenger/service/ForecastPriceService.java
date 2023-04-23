package com.liuxiaolan.apipassenger.service;

import com.liuxiaolan.internalcommon.dto.ResponseResult;
import com.liuxiaolan.internalcommon.request.ForecastPriceDTO;
import com.liuxiaolan.internalcommon.responese.ForecastPriceResponse;
import org.springframework.stereotype.Service;

@Service
public class ForecastPriceService {


    /** 方法描述 根据出发地目的地的经纬度计算预估价格
    * @return
    * @author liuxiaolan
    * @date 2023/4/23
    */
    public ResponseResult forecastPrice(String depLongitude,String depLatitude,
                                        String destLongitude,String destLatitude){

        //计算价格   计价服务

        ForecastPriceResponse forecastPriceResponse = new ForecastPriceResponse();
        forecastPriceResponse.setPrice("23.44");
        return ResponseResult.success(forecastPriceResponse);
    }
}
