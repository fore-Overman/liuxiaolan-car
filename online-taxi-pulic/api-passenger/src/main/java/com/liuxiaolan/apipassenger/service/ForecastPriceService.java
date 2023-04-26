package com.liuxiaolan.apipassenger.service;

import com.liuxiaolan.apipassenger.remote.ServicePriceClient;
import com.liuxiaolan.internalcommon.dto.ResponseResult;
import com.liuxiaolan.internalcommon.request.ForecastPriceDTO;
import com.liuxiaolan.internalcommon.responese.ForecastPriceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ForecastPriceService {

    @Autowired
    private ServicePriceClient servicePriceClient;


    /** 方法描述 根据出发地目的地的经纬度计算预估价格
    * @return
    * @author liuxiaolan
    * @date 2023/4/23
    */
    public ResponseResult forecastPrice(String depLongitude,String depLatitude,
                                        String destLongitude,String destLatitude){
        ForecastPriceDTO forecastPriceDTO = new ForecastPriceDTO();
        forecastPriceDTO.setDepLongitude(depLongitude);
        forecastPriceDTO.setDepLatitude(depLatitude);
        forecastPriceDTO.setDestLongitude(destLongitude);
        forecastPriceDTO.setDestLatitude(destLatitude);
        //计算价格   计价服务
        ResponseResult<ForecastPriceResponse> result = servicePriceClient.forecastPrice(forecastPriceDTO);
        double price = result.getData().getPrice();
        ForecastPriceResponse forecastPriceResponse = new ForecastPriceResponse();
        forecastPriceResponse.setPrice(price);
        return ResponseResult.success(forecastPriceResponse);
    }
}
