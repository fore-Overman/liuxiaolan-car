package com.liuxiaolan.apipassenger.remote;

import com.liuxiaolan.internalcommon.dto.ResponseResult;
import com.liuxiaolan.internalcommon.responese.NumberCodeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("service-verificationcode")
public interface ServiceVeficationCodeClient {

    @RequestMapping(method = RequestMethod.GET,value = "/numberCode/{size}")
    ResponseResult<NumberCodeResponse> getNumberCode(@PathVariable int size);






}
