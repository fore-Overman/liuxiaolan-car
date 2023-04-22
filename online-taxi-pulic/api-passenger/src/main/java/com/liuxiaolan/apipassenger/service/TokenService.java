package com.liuxiaolan.apipassenger.service;

import com.liuxiaolan.internalcommon.constant.CommonStatusEnum;
import com.liuxiaolan.internalcommon.constant.TokenConstants;
import com.liuxiaolan.internalcommon.dto.ResponseResult;
import com.liuxiaolan.internalcommon.dto.TokenResult;
import com.liuxiaolan.internalcommon.responese.TokenResponse;
import com.liuxiaolan.internalcommon.util.JwtUtils;
import com.liuxiaolan.internalcommon.util.RedisPrefixUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TokenService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public ResponseResult refreshToken(String refreshToken){
        //解析上送的refreshToken
        TokenResult tokenResult = JwtUtils.checkToken(refreshToken);
        if(tokenResult == null){
            return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(),CommonStatusEnum.TOKEN_ERROR.getValue());
        }
        //读取Redis的refreshToken
        String phone = tokenResult.getPhone();
        String identity = tokenResult.getIdentity();
        String refreshTokenKey = RedisPrefixUtils.generatorTokenKey(phone,identity, TokenConstants.REFRESH_TOKEN_TYPE);
        String refreshRedisToken = stringRedisTemplate.opsForValue().get(refreshTokenKey);
        //校验Redis的与上送的是否一致  不一致时
        if((StringUtils.isBlank(refreshRedisToken)) || (!refreshRedisToken.trim().equals(refreshToken.trim()))){
            return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(),CommonStatusEnum.TOKEN_ERROR.getValue());
        }
        //重新生成新的双token
        String acToken = JwtUtils.generatorToken(phone, identity, TokenConstants.ACCESS_TOKEN_TYPE);
        String reToken = JwtUtils.generatorToken(phone, identity, TokenConstants.REFRESH_TOKEN_TYPE);
        String acTokenKey = RedisPrefixUtils.generatorTokenKey(phone,identity,TokenConstants.ACCESS_TOKEN_TYPE);
        stringRedisTemplate.opsForValue().set(acTokenKey,acToken,30, TimeUnit.DAYS);
        stringRedisTemplate.opsForValue().set(refreshTokenKey,reToken,31,TimeUnit.DAYS);
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(acToken);
        tokenResponse.setRefreshToken(refreshToken);
        //返回 双token
        return ResponseResult.success(tokenResponse);
    }







}
