package com.taotao.service.core.service;


import com.taotao.common.exception.CustomException;
import com.taotao.common.pojo.ClientType;
import com.taotao.service.core.to.TokenTO;

public interface TokenService {

    /**
     * 创建TOKEN
     *
     * @param userId     用户ID
     * @param clientType 客户端类型
     * @return
     */
    TokenTO createToken(String userId, ClientType clientType);

    /**
     * 根据登录令牌获取TOKEN
     *
     * @param accessToken 登录令牌
     * @return
     */
    TokenTO getToken(String accessToken);

    /**
     * 根据登录令牌删除
     *
     * @param accessToken 登录令牌
     */
    void deleteToken(String accessToken);

    /**
     * 刷新新令牌
     *
     * @param refreshToken 刷新令牌
     * @return
     * @throws CustomException
     */
    TokenTO refreshToken(String refreshToken);
}
