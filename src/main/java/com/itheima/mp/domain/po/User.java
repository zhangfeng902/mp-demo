package com.itheima.mp.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.itheima.mp.enums.UserStatus;
import lombok.Data;
import org.apache.ibatis.type.TypeHandler;

import java.time.LocalDateTime;


/**
 * mybatis-plus官网
 * https://baomidou.com/
 */

@Data
@TableName(value = "user",autoResultMap = true)
public class User {

    /**
     * 用户id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    //Boolean isLeader 布尔值+is开头 也需要声明(会去掉is后去匹配数据库字段)
    @TableField("username")
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 注册手机号
     */
    private String phone;

    /**
     * 订单
     */
    //order是关键字order by  用反单引号
    @TableField("`order`")
    private String order;

    /**
     * 详细信息
     * JSON类型
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private UserInfo info;

    /**
     * 使用状态（1正常 2冻结）
     */
//    private Integer status;
    //可以用枚举，需要加 @EnumValue 注解 和配置文件 MybatisEnumTypeHandler
    private UserStatus status;

    /**
     * 账户余额
     */
    private Integer balance;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
