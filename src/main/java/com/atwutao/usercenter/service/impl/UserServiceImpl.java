package com.atwutao.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atwutao.usercenter.model.User;
import com.atwutao.usercenter.service.UserService;
import com.atwutao.usercenter.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author 86185
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2024-03-10 16:44:02
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

}




