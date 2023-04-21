package com.liuxiaolan.serviceverificationcode.controller;

import com.liuxiaolan.internalcommon.dto.ResponseResult;
import com.liuxiaolan.internalcommon.responese.NumberCodeResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NumberCodeController {


    @GetMapping("/numberCode/{size}")
    public ResponseResult numberCode(@PathVariable("size") int size){

        System.out.println("size:"+size);
        //生成短信验证码
        double mathRandom = (Math.random()*9 + 1) * (Math.pow(10,size - 1));
        Integer mathCode = (int) mathRandom;

        /*JSONObject result = new JSONObject();
        result.put("code",1);
        result.put("message","success");
        JSONObject data = new JSONObject();
        data.put("numberCode",mathCode.toString());
        result.put("data",data);*/

        NumberCodeResponse response = new NumberCodeResponse();
        response.setNumberCode(mathCode.toString());

        return ResponseResult.success(response);
    }
























}
