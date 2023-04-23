package com.liuxiaolan.servicepassengeruser.service;

import com.liuxiaolan.internalcommon.constant.CommonStatusEnum;
import com.liuxiaolan.internalcommon.dto.PassengerUser;
import com.liuxiaolan.internalcommon.dto.ResponseResult;
import com.liuxiaolan.servicepassengeruser.mapper.PassengerUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private PassengerUserMapper passengerUserMapper;

    public ResponseResult loginOrRegister(String passengerPhone){
        //根据用户手机号查询用户信息
        Map<String,Object> map = new HashMap<>();
        map.put("passenger_phone",passengerPhone);
        List<PassengerUser> passengerUsers = passengerUserMapper.selectByMap(map);
        //用户信息不存在就新增用户信息
        if(passengerUsers.size() == 0){
            PassengerUser passengerUser = new PassengerUser();
            passengerUser.setPassengerName("刘小懒");
            passengerUser.setPassengerGender((byte) 1);//0:女   1：男
            passengerUser.setState((byte) 0);  //0:有效  1：失效
            passengerUser.setPassengerPhone(passengerPhone);
            LocalDateTime localDateTime  = LocalDateTime.now();
            passengerUser.setGmtCreate(localDateTime);//创建时间
            passengerUser.setGmtModified(localDateTime);//修改时间
            passengerUserMapper.insert(passengerUser);
        }
        return ResponseResult.success();
    }



    public ResponseResult getUserByPhone(String passengerPhone) {
        //根据用户手机号查询用户信息
        Map<String,Object> map = new HashMap<>();
        map.put("passenger_phone",passengerPhone);
        List<PassengerUser> passengerUsers = passengerUserMapper.selectByMap(map);
        if(passengerUsers.size() == 0){
           return ResponseResult.fail(CommonStatusEnum.USER_NOT_EXISTS.getCode(),CommonStatusEnum.USER_NOT_EXISTS.getValue());
        }else{
            PassengerUser passengerUser = passengerUsers.get(0);
            return ResponseResult.success(passengerUser);
        }
    }



}
