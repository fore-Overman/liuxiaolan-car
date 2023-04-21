package com.liuxiaolan.apipassenger.remote;

import com.liuxiaolan.internalcommon.dto.ResponseResult;
import com.liuxiaolan.internalcommon.request.VerificationCodeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("service-passenger-user")
public interface ServicePassengerUserClient {


     @RequestMapping(method = RequestMethod.POST,value = "/user")
     ResponseResult loginOrRegister(@RequestBody VerificationCodeDTO verificationCodeDTO);
}
