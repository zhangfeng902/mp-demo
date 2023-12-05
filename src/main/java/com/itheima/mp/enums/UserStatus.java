package com.itheima.mp.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum UserStatus {

    NORMAL(1,"正常"),
    FROZEM(2,"冻结"),
    ;

    @EnumValue //sql取值
    private final int value;
    @JsonValue //返回前端 "正常"或"冻结"
    private final String desc;

    UserStatus(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
