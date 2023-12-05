package com.itheima.mp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.itheima.mp.domain.dto.PageDTO;
import com.itheima.mp.domain.po.Address;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.query.UserQuery;
import com.itheima.mp.domain.vo.AddressVO;
import com.itheima.mp.domain.vo.UserVO;
import com.itheima.mp.enums.UserStatus;
import com.itheima.mp.mapper.UserMapper;
import com.itheima.mp.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public void deductBalance(Long id, Integer money) {
        //1.查询用户
        User user = getById(id);
        //2.校验用户状态
        if(user == null || user.getStatus() == UserStatus.FROZEM){
            throw new RuntimeException("用户状态异常!");
        }
        //3.校验金额是否充足
        if(user.getBalance() < money){
            throw new RuntimeException("用户余额不足!");
        }
        //4.扣减金额
//        baseMapper.deductBalance(id,money);
        //4.金额扣减为0时，状态改为2
        int remainBalance = user.getBalance() - money;
        lambdaUpdate()
                .set(User::getBalance,remainBalance)
                .set(remainBalance == 0 , User::getStatus , 2)
                .eq(User::getId,id)
                .update();
    }

    @Override
    public List<User> queryList(String name, Integer status, Integer minBalance, Integer maxBalance) {
        return lambdaQuery()
                .like(name != null , User::getUsername,name)
                .eq(status != null , User::getStatus,status)
                .ge(minBalance != null , User::getBalance,minBalance)
                .le(maxBalance != null , User::getBalance,maxBalance).list();
    }

    @Override
    public UserVO queryUserAndAddress(Long id) {
        //1.查询用户
        User user = getById(id);
        //2.校验用户状态
        if(user == null || user.getStatus() == UserStatus.FROZEM){
            throw new RuntimeException("用户状态异常!");
        }
        List<Address> addresses = Db.lambdaQuery(Address.class).eq(Address::getUserId, id).list();
        UserVO userVO = BeanUtil.copyProperties(user,UserVO.class);
        if(CollUtil.isNotEmpty(addresses)){
            userVO.setAddressVOS(BeanUtil.copyToList(addresses, AddressVO.class));
        }
        return userVO;
    }

    @Override
    public PageDTO<UserVO> queryUsersPage(UserQuery query) {
        String name = query.getName();
        Integer status = query.getStatus();
//        //1.构建分页条件
//        Page<User> page = Page.of(query.getPageNo(), query.getPageSize());
//
//        if(StrUtil.isNotBlank(query.getSortBy())){
//            page.addOrder(new OrderItem(query.getSortBy(),query.getIsAsc()));
//        }else{
//            page.addOrder(new OrderItem("update_time",false));
//        }
//        //2.分页查询
//        lambdaQuery()
//                .like(name != null,User::getUsername,name)
//                .eq(status != null,User::getStatus,status)
//                .page(page);
//        //3.封装VO结果
//        PageDTO<UserVO> dto = new PageDTO<>();
//        dto.setTotal(page.getTotal());
//        dto.setPages(page.getPages());
//        List<User> records = page.getRecords();
//        if(CollUtil.isEmpty(records)){
//            dto.setList(Collections.emptyList());
//            return dto;
//        }
//        List<UserVO> userVOS = BeanUtil.copyToList(records, UserVO.class);
//        dto.setList(userVOS);

        //1.构建分页条件
        Page<User> page = query.toMpPageDefaultSortByUpdateTimeDesc();
        //2.分页查询
        lambdaQuery()
                .like(name != null,User::getUsername,name)
                .eq(status != null,User::getStatus,status)
                .page(page);
        //3.封装VO结果
        return PageDTO.of(page,user -> BeanUtil.copyProperties(user,UserVO.class));
    }
}
