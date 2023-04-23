package com.liuxiaolan.apipassenger.service;

import com.liuxiaolan.apipassenger.remote.ServicePassengerUserClient;
import com.liuxiaolan.internalcommon.dto.PassengerUser;
import com.liuxiaolan.internalcommon.dto.ResponseResult;
import com.liuxiaolan.internalcommon.dto.TokenResult;
import com.liuxiaolan.internalcommon.request.VerificationCodeDTO;
import com.liuxiaolan.internalcommon.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private ServicePassengerUserClient servicePassengerUserClient;



    public ResponseResult getUserByAccessToken(String accessToken){
       //获取到手机号
        TokenResult tokenResult = JwtUtils.DecodeJMT(accessToken);
        String phone = tokenResult.getPhone();
        //查询用户信息
        ResponseResult<PassengerUser> user = servicePassengerUserClient.getUser(phone);
        //返回用户信息
        return ResponseResult.success(user.getData());
    }
}
