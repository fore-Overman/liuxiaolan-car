package com.liuxiaolan.internalcommon.util;

public class RedisPrefixUtils {

    public static String verificationCodePrefix = "passenger-verification-code-";

    public static String tokenPrefix = "token-";

    /** 方法描述 验证码 redis 中key 的生成
     * @return
     * @author liuxiaolan
     * @date 2023/4/19
     */
    public static String generatorKeyByPhone(String passengerPhone){
        return verificationCodePrefix+passengerPhone;
    }

    /** 方法描述 token的key生成  根据手机号和身份标识
     * @return
     * @author liuxiaolan
     * @date 2023/4/22
     */
    public static String generatorTokenKey(String phone,String identity,String tokenType){
        return tokenPrefix + phone + "-" + identity + "-" + tokenType;

    }






}
