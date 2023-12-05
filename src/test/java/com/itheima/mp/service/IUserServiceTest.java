package com.itheima.mp.service;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.mp.domain.po.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
class IUserServiceTest {

    @Autowired
    private IUserService userService;

    @Test
    public void selectTest(){
        List<User> users = userService.listByIds(List.of(1L, 2L));
        users.forEach(System.out::println);
    }

    @Test
    public void testPageQuery(){
        int pageNo = 1,pageSize = 3;
        Page<User> page = Page.of(pageNo, pageSize);
        page.addOrder(new OrderItem("balance",true));
        page.addOrder(new OrderItem("id",true));

        Page<User> p = userService.page(page);

        System.out.println(p.getTotal());
        System.out.println(p.getPages());

        List<User> users = p.getRecords();
        users.forEach(System.out::println);
    }

}