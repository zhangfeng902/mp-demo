package com.itheima.mp.mapper;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.itheima.mp.domain.po.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {

    User queryUserById(@Param("id") Long id);

    List<User> queryUserByIds(@Param("ids") List<Long> ids);

    void updateBalanceByIds(@Param(Constants.WRAPPER) LambdaUpdateWrapper<User> updateWrapper, @Param("amount") int amount);

    @Update("UPDATE user SET balance = balance - #{money} WHERE id = #{id}")
    void deductBalance(@Param("id")Long id, @Param("money")Integer money);
}
