package com.liuxiaolan.apipassenger.interceptor;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.liuxiaolan.internalcommon.constant.TokenConstants;
import com.liuxiaolan.internalcommon.dto.ResponseResult;
import com.liuxiaolan.internalcommon.dto.TokenResult;
import com.liuxiaolan.internalcommon.util.JwtUtils;
import com.liuxiaolan.internalcommon.util.RedisPrefixUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/** 方法描述 token 的拦截器  即所有请求先判断token的使用控制
* @return 
* @author liuxiaolan
* @date 2023/4/20
*/
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;




    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean result = true;
        String resultString = "";
        //从请求头中获取token
        String token = request.getHeader("Authorization");
        //解析token
        TokenResult tokenResult = JwtUtils.checkToken(token);
        //生成token在redis中的key
        if(tokenResult == null){
            resultString = "token invalid";
            result = false;
        }else{
            String phone = tokenResult.getPhone();
            String identity = tokenResult.getIdentity();
            String tokenKey = RedisPrefixUtils.generatorTokenKey(phone,identity, TokenConstants.ACCESS_TOKEN_TYPE);
            String tokenS = stringRedisTemplate.opsForValue().get(tokenKey);
            if((StringUtils.isBlank(tokenS)) || (!tokenS.trim().equals(token.trim()))){//是空
                resultString = "token time out";
                result = false;
            }
        }
        //如果token 异常 就给前端抛出错误码及信息
        if(!result){
            PrintWriter out = response.getWriter();
            out.print(JSONObject.fromObject(ResponseResult.fail(resultString)).toString());
        }

        return result;
    }
}
