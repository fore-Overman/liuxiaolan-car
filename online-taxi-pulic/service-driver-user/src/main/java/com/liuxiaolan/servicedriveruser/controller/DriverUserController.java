package com.liuxiaolan.servicedriveruser.controller;

import com.liuxiaolan.internalcommon.dto.ResponseResult;
import com.liuxiaolan.servicedriveruser.service.DriverUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DriverUserController {

    @Autowired
    private DriverUserService driverUserService;





    @GetMapping("/test")
    public ResponseResult getDriverUserInfo(){
        return ResponseResult.success(driverUserService.testGetDriverUser());
    }








}
