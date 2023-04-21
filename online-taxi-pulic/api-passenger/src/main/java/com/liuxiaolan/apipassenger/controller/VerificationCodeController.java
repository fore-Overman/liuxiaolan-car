package com.liuxiaolan.apipassenger.controller;

import com.liuxiaolan.apipassenger.service.VerificationCodeService;
import com.liuxiaolan.internalcommon.dto.ResponseResult;
import com.liuxiaolan.internalcommon.request.VerificationCodeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VerificationCodeController {

    @Autowired
    private VerificationCodeService verificationCodeService;

    /** 方法描述* 获取短信验证码
    * @return 短信验证码
    * @author liuxiaolan
    * @date 2023/4/19
    */
    @PostMapping("/verification-code")
    public ResponseResult VerificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO){
      String passengerPhone = verificationCodeDTO.getPassengerPhone();
      return verificationCodeService.generatorCode(passengerPhone);
    }


    /** 方法描述* 检验短信验证码
    * @return  token信息
    * @author liuxiaolan
    * @date 2023/4/19
    */
    @PostMapping("/verification-code-check")
    public ResponseResult checkVerificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO){

        String passengerPhone = verificationCodeDTO.getPassengerPhone();
        String verificationCode = verificationCodeDTO.getVerificationCode();
        //校验短信验证码
        return verificationCodeService.checkCode(passengerPhone,verificationCode);
    }






}
