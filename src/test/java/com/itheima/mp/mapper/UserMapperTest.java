package com.itheima.mp.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.po.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class UserMapperTest {

    @Autowired(required = false)
    private UserMapper userMapper;

    @Test
    void testInsert() {
        User user = new User();
//        user.setId(5L);
        user.setUsername("Lucy" + System.currentTimeMillis());
        user.setPassword("123");
        user.setPhone("18688990011");
        user.setOrder("186-8899-0011");
        user.setBalance(200);
        user.setInfo(UserInfo.of(24,"英文老师","female"));
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.insert(user);
    }

    @Test
    void testSelectById() {
        User user = userMapper.queryUserById(5L);
        System.out.println("user = " + user);
    }


    @Test
    void testQueryByIds() {
        List<User> users = userMapper.queryUserByIds(List.of(1L, 2L, 3L, 4L));
        users.forEach(System.out::println);
    }

    @Test
    void testSelectByIds() {
        List<User> users = userMapper.selectBatchIds(List.of(1L, 2L, 3L, 4L));
        users.forEach(System.out::println);
    }

    @Test
    void testSelectListWrapper() {
        QueryWrapper<User> wrapper = new QueryWrapper<User>()
                .select("id", "username", "info", "balance")
                .likeRight("username", "h")
                .ge("balance", 1000);
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
//        System.out.println("----"+wrapper.getSqlSelect());
    }

    @Test
    void testLambdaSelectListWrapper() {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>()
                .select(User::getId,User::getUsername,User::getInfo,User::getBalance)
                .likeRight(User::getUsername, "h")
                .ge(User::getBalance, 1000);
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    @Test
    void testWrapperUpdate() {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<User>()
//                .set("balance",2000)
                .setSql("balance = balance - 200")
                .in("id",List.of(1L,2L));
        userMapper.update(null,updateWrapper);
    }

    @Test
    void testLambdaWrapperUpdate() {
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<User>()
                .set(User::getBalance,2000)
                .setSql("balance = balance - 200")
                .in(User::getId,List.of(1L,2L));
        userMapper.update(null,updateWrapper);
    }

    @Test
    void testCustomSqlUpdate() {
        int amount = 2000;
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<User>()
                .in(User::getId,List.of(3L,6L));
        System.out.println(updateWrapper.getCustomSqlSegment());
        userMapper.updateBalanceByIds(updateWrapper,amount);
    }

}