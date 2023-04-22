package com.liuxiaolan.apipassenger.controller;

import com.liuxiaolan.apipassenger.service.TokenService;
import com.liuxiaolan.internalcommon.dto.ResponseResult;
import com.liuxiaolan.internalcommon.responese.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    @Autowired
    private TokenService tokenService;

    /** 方法描述 双token的刷新  /token-refresh
     * @return
     * @author liuxiaolan
     * @date 2023/4/22
     */
    @PostMapping("/token-refresh")
    public ResponseResult refreshToken(@RequestBody TokenResponse tokenResponse){
        //接收 refreshtoken
        String refreshToken = tokenResponse.getRefreshToken();
        return tokenService.refreshToken(refreshToken);
    }
}
