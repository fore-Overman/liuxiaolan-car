package com.liuxiaolan.servicepassengeruser.controller;

import com.liuxiaolan.internalcommon.dto.ResponseResult;
import com.liuxiaolan.internalcommon.request.VerificationCodeDTO;
import com.liuxiaolan.servicepassengeruser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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









}
