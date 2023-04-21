package com.liuxiaolan.apipassenger.service;


import com.liuxiaolan.apipassenger.remote.ServicePassengerUserClient;
import com.liuxiaolan.apipassenger.remote.ServiceVeficationCodeClient;
import com.liuxiaolan.internalcommon.constant.CommonStatusEnum;
import com.liuxiaolan.internalcommon.constant.IndentityConstant;
import com.liuxiaolan.internalcommon.dto.ResponseResult;
import com.liuxiaolan.internalcommon.request.VerificationCodeDTO;
import com.liuxiaolan.internalcommon.responese.NumberCodeResponse;
import com.liuxiaolan.internalcommon.responese.TokenResponse;
import com.liuxiaolan.internalcommon.util.JwtUtils;
import io.netty.util.internal.StringUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class VerificationCodeService {

    @Autowired
    private ServiceVeficationCodeClient serviceVeficationCodeClient;

    private String verificationCodePrefix = "passenger-verification-code-";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ServicePassengerUserClient servicePassengerUserClient;



    /** 方法描述 获取短信短信验证码
    * @return 短信验证码
    * @author liuxiaolan
    * @date 2023/4/19
    */
  public ResponseResult generatorCode(String passengerPhone){

      //调用短信验证码服务
      ResponseResult<NumberCodeResponse> numberCode = serviceVeficationCodeClient.getNumberCode(6);
      String code = numberCode.getData().getNumberCode();
      //存入Redis  key   value  过期时间
      String key = generatorKeyByPhone(passengerPhone);
      //存入redis
      stringRedisTemplate.opsForValue().set(key,code, 2,TimeUnit.MINUTES);
      //调用第三方服务 将短信验证码发送给客户  短信平台

      return ResponseResult.success(code);
  }

/** 方法描述 验证码 redis 中key 的生成
* @return 
* @author liuxiaolan
* @date 2023/4/19
*/
  private String generatorKeyByPhone(String passengerPhone){
      return verificationCodePrefix+passengerPhone;
  }

  /** 方法描述校验短信验证码
  * @return
  * @author liuxiaolan
  * @date 2023/4/19
  */
  public ResponseResult checkCode(String passengerPhone,String verificationCode){
      //根据手机号 去redis中获取短信验证码
      String key = generatorKeyByPhone(passengerPhone);
      String codeRedis = stringRedisTemplate.opsForValue().get(key);
      //校验短信验证码
        //1:验证码是否国企
        if(StringUtils.isBlank(codeRedis)){
           return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(),CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }
        //2：判断验证码是否正确
        if(!verificationCode.trim().equals(codeRedis.trim())){
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_FAIL.getCode(),CommonStatusEnum.VERIFICATION_CODE_FAIL.getValue());
        }
       //判断是否有原用户 进行相应处理
        VerificationCodeDTO verificationCodeDTO = new VerificationCodeDTO();
        verificationCodeDTO.setPassengerPhone(passengerPhone);
        ResponseResult result = servicePassengerUserClient.loginOrRegister(verificationCodeDTO);
      //颁发令牌  JWT  通过json的方式   标识应该用枚举/常量
      String generatorToken = JwtUtils.generatorToken(passengerPhone, IndentityConstant.PASSENGER_INDENTITY);

      TokenResponse token = new TokenResponse();
      token.setToken(generatorToken);

      return ResponseResult.success(token);
  }




}
