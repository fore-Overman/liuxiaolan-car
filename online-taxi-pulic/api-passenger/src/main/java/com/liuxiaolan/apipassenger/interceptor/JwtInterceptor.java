package com.liuxiaolan.apipassenger.interceptor;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.liuxiaolan.internalcommon.dto.ResponseResult;
import com.liuxiaolan.internalcommon.util.JwtUtils;
import net.sf.json.JSONObject;
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
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean result = true;
        String resultString = "";
        //从请求头中获取token
        String token = request.getHeader("Authorization");
        //解析token
        try {
            JwtUtils.DecodeJMT(token);
        }catch (SignatureVerificationException e){
            //签名错误
            resultString = "token sign error";
            result = false;
        }catch (TokenExpiredException e){
            //过期
            resultString = "token time out";
            result = false;
        }catch (Exception e){
            //token 无效
            resultString = "token invalid";
            result = false;
        }

        //如果token 异常 就给前端抛出错误码及信息
        if(!result){
            PrintWriter out = response.getWriter();
            out.print(JSONObject.fromObject(ResponseResult.fail(resultString)).toString());
        }

        return result;
    }
}
