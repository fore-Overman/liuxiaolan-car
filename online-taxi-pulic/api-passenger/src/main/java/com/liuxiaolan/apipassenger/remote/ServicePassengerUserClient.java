package com.liuxiaolan.apipassenger.remote;

import com.liuxiaolan.internalcommon.dto.ResponseResult;
import com.liuxiaolan.internalcommon.request.VerificationCodeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("service-passenger-user")
public interface ServicePassengerUserClient {


     /** 方法描述 保存用户信息
     * @return
     * @author liuxiaolan
     * @date 2023/4/23
     */
     @RequestMapping(method = RequestMethod.POST,value = "/user")
     ResponseResult loginOrRegister(@RequestBody VerificationCodeDTO verificationCodeDTO);


     /** 方法描述 根据手机号查询用户信息
     * @return
     * @author liuxiaolan
     * @date 2023/4/23
     */
     @RequestMapping(method = RequestMethod.GET,value = "/user/{passengerPhnoe}")
     ResponseResult getUser(@PathVariable("passengerPhnoe") String passengerPhnoe);






}
