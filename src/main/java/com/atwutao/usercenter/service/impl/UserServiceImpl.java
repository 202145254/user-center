package com.atwutao.usercenter.service.impl;
import java.util.Date;

import com.atwutao.usercenter.model.domain.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.atwutao.usercenter.service.UserService;
import com.atwutao.usercenter.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 86185
 * @description 针对表【user(用户)】的数据库操作Service实现
 * @createDate 2024-03-10 16:44:02
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;
    private final String SALT="DS_wt";

    private static  final String USER_LOGIN_STATE="userLoginState";

    @Override
    public long userRegister(String userAccount, String password, String checkPassword) {
        //1.非空校验
        if (StringUtils.isAnyBlank(userAccount, password, checkPassword)) {
            return -1;
        }
        //2.长度校验
        if (userAccount.length() < 4) {
            return -1;
        }
        if (password.length() < 6 || checkPassword.length() < 6) {
            return -1;
        }
        //3.账户不包含特殊字符
        String regEx = "\\pP|\\pS|\\s+";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(userAccount);
        if (matcher.find()) {
            return -1;
        }
        //4.密码和校验密码相同
        if (!password.equals(checkPassword)) {
            return -1;
        }
        //5.账号不能重复
        QueryWrapper qw=new QueryWrapper();
        qw.eq("userAccount", userAccount);
        long count = this.count(qw);
        if(count>0){
            return -1;
        }

        //对密码进行加密

        String encryptedPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());

        //插入数据
        User user=new User();
        user.setUserAccount(userAccount);
        user.setPassword(encryptedPassword);
        boolean save = this.save(user);
        if (!save){
            return -1;
        }


        return user.getId();
    }

    @Override
    public User doLogin(String userAccount, String password, HttpServletRequest request) {
        //1.非空校验
        if (StringUtils.isAnyBlank(userAccount, password)) {
            return null;
        }
        //2.长度校验
        if (userAccount.length() < 4) {
            return null;
        }
        if (password.length() < 6 ) {
            return null;
        }
        //3.账户不包含特殊字符
        String regEx = "\\pP|\\pS|\\s+";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(userAccount);
        if (matcher.find()) {
            return null;
        }


        //对密码进行加密
        String encryptedPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
        //4.查询用户是否存在
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("password",encryptedPassword);
        User user = userMapper.selectOne(queryWrapper);
        if(user == null ){
            log.info("user login failed, userAccount cannot match userPassword");
            return null;
        }

        //5.用户信息脱敏
        User safetyUser=new User();
        safetyUser.setId(user.getId());
        safetyUser.setUsername(user.getUsername());
        safetyUser.setUserAccount(user.getUserAccount());
        safetyUser.setAvatarUrl(user.getAvatarUrl());
        safetyUser.setPhone(user.getPhone());
        safetyUser.setGender(user.getGender());
        safetyUser.setEmail(user.getEmail());
        safetyUser.setUserStatus(0);
        safetyUser.setCreateTime(new Date());
        safetyUser.setUpdateTime(new Date());
        safetyUser.setIsDelete(0);

        //6.记录用户的登录态
        request.setAttribute(USER_LOGIN_STATE,safetyUser);

        return safetyUser;
    }
}




