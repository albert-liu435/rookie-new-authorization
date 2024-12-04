package com.rookie.bigdata.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rookie.bigdata.entity.Oauth2BasicUser;
import com.rookie.bigdata.entity.Oauth2ThirdAccount;
import com.rookie.bigdata.model.response.Oauth2UserinfoResult;

/**
 * @Author rookie
 * @Description 基础用户信息表 服务类
 * @Date 2024/4/9 23:19
 * @Version 1.0
 */
public interface IOauth2BasicUserService extends IService<Oauth2BasicUser> {

    /**
     * 生成用户信息
     *
     * @param thirdAccount 三方用户信息
     * @return 用户id
     */
    Integer saveByThirdAccount(Oauth2ThirdAccount thirdAccount);

    /**
     * 获取当前登录用户的信息
     *
     * @return 用户信息
     */
    Oauth2UserinfoResult getLoginUserInfo();

}
