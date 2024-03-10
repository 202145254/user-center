package com.atwutao.usercenter.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @BelongsProject: user-center
 * @BelongsPackage: com.atwutao.usercenter.model.domain.request
 * @Author: wutao
 * @CreateTime: 2024-03-10  23:59
 * @Description: TODO
 * @Version: 1.0
 */
@Data
public class UserDoLoginRequest implements Serializable {
    private static  final long serialVersionUID=3132256334L;
    private String   userAccount;
    private String   userPassword;
}
