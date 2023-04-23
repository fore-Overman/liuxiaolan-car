package com.liuxiaolan.internalcommon.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.liuxiaolan.internalcommon.constant.TokenConstants;
import com.liuxiaolan.internalcommon.dto.TokenResult;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {

    //盐
    private static final String SIGN = "DALANZHU";

    //用户手机号
    private static final String JWT_KEY_PHONE = "passengerPhone";

    //身份标识  1:乘客   2：司机
    private static final String JWT_KEY_IDENTITY = "identity";

    //token 类型
    private static final String JWI_TOKEN_TYPE = "tokenType";

    private static final String JWT_TOKEN_TIME = "tokenTime";

    /** 方法描述生成token   token有过期时间
    * @return
    * @author liuxiaolan
    * @date 2023/4/20
    */
    public static String generatorToken(String  passengerPhone,String identity,String tokenType){
        Map<String,String> map = new HashMap<>();
        map.put(JWT_KEY_PHONE,passengerPhone);
        map.put(JWT_KEY_IDENTITY,identity);
        map.put(JWI_TOKEN_TYPE,tokenType);
        //过期时间 的设置
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,1);
        Date date =  calendar.getTime();
        //生成时间
        map.put(JWT_TOKEN_TIME,date.toString());
        JWTCreator.Builder builder = JWT.create();
        //将map中的值 迭代至 builder中
        map.forEach( (k,v) -> {
            builder.withClaim(k,v);
        });
        //整合过期时间
    //    builder.withExpiresAt(date);   存储redis的时候加了时间 所以注释掉

        //生成token
        String sign = builder.sign(Algorithm.HMAC256(SIGN));
        return sign;
    }


    /** 方法描述 解析token
    * @return
    * @author liuxiaolan
    * @date 2023/4/20
    */
    public static TokenResult DecodeJMT(String token){
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);

        String phone = verify.getClaim(JWT_KEY_PHONE).asString();
        String identity = verify.getClaim(JWT_KEY_IDENTITY).asString();

        TokenResult tokenResult = new TokenResult();
        tokenResult.setPhone(phone);
        tokenResult.setIdentity(identity);

        return tokenResult;
    }


    public static void main(String[] args) {
        String passengerPhone = "17823456798";
        String ss = generatorToken(passengerPhone,"1", TokenConstants.ACCESS_TOKEN_TYPE);
        System.out.println("生成的token:"+ss);
        //解析的token的结果
        System.out.println("解析结果是："+DecodeJMT(ss));

    }

    /** 方法描述 校验token 判断token是否异常
    * @return 
    * @author liuxiaolan
    * @date 2023/4/22
    */
    public static TokenResult checkToken(String token){
        TokenResult tokenResult = null;
        try {
            tokenResult = JwtUtils.DecodeJMT(token);
        }catch (Exception e){
            //token 无效

        }
        return tokenResult;
    }




















}
