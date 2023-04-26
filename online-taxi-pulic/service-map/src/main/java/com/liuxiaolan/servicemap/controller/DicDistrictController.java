package com.liuxiaolan.servicemap.controller;

import com.liuxiaolan.internalcommon.dto.ResponseResult;
import com.liuxiaolan.servicemap.service.DicDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DicDistrictController {

    @Autowired
    private DicDistrictService dicDistrictService;


    /** 方法描述 同步地区码到数据库
    * @return
    * @author liuxiaolan
    * @date 2023/4/26
    */
    @GetMapping("/dic_district")
    public ResponseResult initDistrict(String keyWords){
       return dicDistrictService.initDicDistrict(keyWords);
   }








}
