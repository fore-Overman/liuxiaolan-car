package com.liuxiaolan.internalcommon.dto;

import com.liuxiaolan.internalcommon.constant.CommonStatusEnum;
import lombok.Data;
import lombok.experimental.Accessors;

@Data  //给每个属性设置 get set方法
@Accessors(chain = true)//链式调用  set之后 get返回
public class ResponseResult<T> {

    private int code;
    private String message;
    private T data;

    public static <T> ResponseResult success(){
        return new ResponseResult().setCode(CommonStatusEnum.SUCCESS.getCode()).setMessage(CommonStatusEnum.SUCCESS.getValue());
    }
    /** 方法描述 成功响应的方法
    * @return
    * @author liuxiaolan
    * @date 2023/4/18
    */
    public static <T> ResponseResult success(T data){
        return new ResponseResult().setCode(CommonStatusEnum.SUCCESS.getCode()).setMessage(CommonStatusEnum.SUCCESS.getValue()).setData(data);
    }


    public static ResponseResult fail(int code,String message){
        return new ResponseResult().setCode(code).setMessage(message);
    }

    
    /** 方法描述 自定义失败方法 错误码  错误信息 具体错误
    * @return 
    * @author liuxiaolan
    * @date 2023/4/18
    */
    public static ResponseResult fail(int code,String message,String data){
        return new ResponseResult().setCode(code).setMessage(message).setData(data);
    }

    /** 方法描述  失败 统一的失败
    * @return 
    * @author liuxiaolan
    * @date 2023/4/18
    */
    public static <T> ResponseResult fail(T data){
        return new ResponseResult().setData(data);
    }






















}
