package com.liuxiaolan.servicepassengeruser.controller;

import com.liuxiaolan.internalcommon.dto.ResponseResult;
import com.liuxiaolan.internalcommon.request.VerificationCodeDTO;
import com.liuxiaolan.servicepassengeruser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /** 方法描述 根据手机号插入用户
    * @return 
    * @author liuxiaolan
    * @date 2023/4/19
    */
    @PostMapping("/user")
    public ResponseResult loginOrRegister(@RequestBody VerificationCodeDTO verificationCodeDTO){
        String passengerPhone = verificationCodeDTO.getPassengerPhone();
        return userService.loginOrRegister(passengerPhone);
    }

    /** 方法描述 根据用户手机号查询用户信息
    * @return
    * @author liuxiaolan
    * @date 2023/4/23
    */
    @GetMapping("/user/{passengerPhnoe}")
    public ResponseResult getUser(@PathVariable("passengerPhnoe") String passengerPhone){
        return userService.getUserByPhone(passengerPhone);
    }






}
