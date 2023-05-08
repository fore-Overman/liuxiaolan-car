package com.liuxiaolan.servicedriveruser.service;

import com.liuxiaolan.internalcommon.dto.DriverUser;
import com.liuxiaolan.internalcommon.dto.ResponseResult;
import com.liuxiaolan.servicedriveruser.mapper.DriverUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverUserService {
    @Autowired
    private DriverUserMapper driverUserMapper;


    public ResponseResult testGetDriverUser(){

        DriverUser driverUser = driverUserMapper.selectById("1584359006294835202");

        return ResponseResult.success(driverUser);
    }












}
