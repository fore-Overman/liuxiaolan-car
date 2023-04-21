package com.liuxiaolan.internalcommon.constant;

import lombok.Getter;

public enum CommonStatusEnum {

    /**
     *  1000：验证码过期  1099验证码不正确
     * */
    VERIFICATION_CODE_ERROR(1000,"验证码已过期！"),

    VERIFICATION_CODE_FAIL(1099,"验证码不正确！"),
    /**
     *  1:成功   0：失败
     * */
    SUCCESS(1,"success"),
    FAIL(0,"fail")
    ;

    @Getter
    private int code;

    @Getter
    private String value;

    CommonStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

}
