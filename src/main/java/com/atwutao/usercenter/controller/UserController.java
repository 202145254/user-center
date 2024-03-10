package com.atwutao.usercenter.controller;

import com.atwutao.usercenter.model.domain.User;
import com.atwutao.usercenter.model.domain.request.UserDoLoginRequest;
import com.atwutao.usercenter.model.domain.request.UserRegisterRequest;
import com.atwutao.usercenter.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @BelongsProject: user-center
 * @BelongsPackage: com.atwutao.usercenter.controller
 * @Author: wutao
 * @CreateTime: 2024-03-10  21:25
 * @Description: user控制类
 * @Version: 1.0
 */
@RestController
public class UserController {
    @Resource
    private UserService userService;


    /*
     * @description: 用户注册
     * @param: [userRegisterRequest]
     * @return: java.lang.Long
     **/
    @PostMapping("/register")
    public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest){
         if(userRegisterRequest==null){
             return null;
         }
         String userAccount=userRegisterRequest.getUserAccount();
         String userPassword=userRegisterRequest.getUserPassword();
         String checkPassword=userRegisterRequest.getCheckPassword();

         if (StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)){
             return null;
         }

        return userService.userRegister(userAccount, userPassword, checkPassword);
    }


    /*
     * @description:用户登录
     * @param: [userDoLoginRequest, request]
     * @return: com.atwutao.usercenter.model.domain.User
     **/
    @PostMapping("/login")
    public User doLogin(@RequestBody UserDoLoginRequest userDoLoginRequest, HttpServletRequest request){
        if(userDoLoginRequest==null){
            return null;
        }
        String userAccount=userDoLoginRequest.getUserAccount();
        String userPassword=userDoLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount,userPassword)){
            return null;
        }
        return userService.doLogin(userAccount,userPassword,request);
    }

}
