package com.atwutao.usercenter.service;

import com.atwutao.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author 86185
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2024-03-10 16:44:02
*/
public interface UserService extends IService<User> {

    /*
     * @description: 验证用户登录
     * @param: [userAccount 用户名, password 密码, checkPassword 确认密码]
     * @return: long
     **/
     long userRegister(String userAccount,String password,String checkPassword);


     User doLogin(String userAccount, String password, HttpServletRequest request);
}
