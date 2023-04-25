package com.liuxiaolan.serviceprice.remote;

import com.liuxiaolan.internalcommon.dto.ResponseResult;
import com.liuxiaolan.internalcommon.request.ForecastPriceDTO;
import com.liuxiaolan.internalcommon.responese.DirectionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("service-map")
public interface ServiceMapClient {


     @RequestMapping(method = RequestMethod.GET,value = "/direction/driving")
     ResponseResult<DirectionResponse> driving(@RequestBody ForecastPriceDTO forecastPriceDTO);






    }
