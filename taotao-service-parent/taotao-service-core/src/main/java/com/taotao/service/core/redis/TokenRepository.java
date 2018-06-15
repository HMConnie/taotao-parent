package com.taotao.service.core.redis;

import com.alibaba.fastjson.JSON;
import com.taotao.common.pojo.ClientType;
import com.taotao.service.core.to.TokenTO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class TokenRepository {

    /**
     * AccessToken过期时间
     */
    public static final long ACCESS_TOKEN_EXPIRED_SECOND=60L * 60 * 24 * 7;

    /**
     * RefreshToken过期时间
     */
    private static final long REFRESH_TOKEN_EXPIRED_SECOND=60L * 60 * 24 * 14;

    @Autowired
    private StringRedisTemplate redisTemplate;

    public void save(TokenTO entity) {
        redisTemplate.boundValueOps(genAccessTokenKey(entity.getAccessToken())).set(entity.getRefreshToken(),
            ACCESS_TOKEN_EXPIRED_SECOND, TimeUnit.SECONDS);

        redisTemplate
            .boundValueOps(genUserIdKey(entity.getUserId(), entity.getClientType())).set(
                entity.getAccessToken(), ACCESS_TOKEN_EXPIRED_SECOND, TimeUnit.SECONDS);

        redisTemplate.boundValueOps(genRefreshToken(entity.getRefreshToken())).set(JSON.toJSONString(entity),
            REFRESH_TOKEN_EXPIRED_SECOND, TimeUnit.SECONDS);
    }

    public void delete(String accessToken) {
        if (StringUtils.isBlank(accessToken)) {
            return;
        }
        String refreshToken=redisTemplate.boundValueOps(genAccessTokenKey(accessToken)).get();
        deleteRefreshToken(refreshToken);
    }

    public void deleteRefreshToken(String refreshToken) {
        if (StringUtils.isBlank(refreshToken)) {
            return;
        }
        TokenTO entity=getTokenByRefreshToken(refreshToken);
        if (entity == null) {
            return;
        }
        List<String> list=new ArrayList<>(3);
        list.add(genAccessTokenKey(entity.getAccessToken()));
        list.add(genUserIdKey(entity.getUserId(), entity.getClientType()));
        list.add(genRefreshToken(entity.getRefreshToken()));
        redisTemplate.delete(list);
    }

    public void delete(String userId,  ClientType clientType) {
        String key=genUserIdKey(userId, clientType);
        String accessToken=redisTemplate.boundValueOps(key).get();
        delete(accessToken);
    }

    public TokenTO getTokenByAccessToken(String accessToken) {
        if (StringUtils.isBlank(accessToken)) {
            return null;
        }
        String refreshToken=redisTemplate.boundValueOps(genAccessTokenKey(accessToken)).get();
        return getTokenByRefreshToken(refreshToken);
    }

    public TokenTO getTokenByRefreshToken(String refreshToken) {
        if (StringUtils.isBlank(refreshToken)) {
            return null;
        }
        String json=redisTemplate.boundValueOps(genRefreshToken(refreshToken)).get();
        if (StringUtils.isBlank(json)) {
            return null;
        }
        return JSON.parseObject(json, TokenTO.class);
    }

    public TokenTO getToken(String userId, ClientType clientType) {
        String accessToken=redisTemplate.boundValueOps(genUserIdKey(userId, clientType)).get();
        return getTokenByAccessToken(accessToken);
    }

    private static String genUserIdKey(String userId, ClientType clientType) {
        return new StringBuilder().append("token-userid-").append(userId).append("-clientid-").append(clientType.getId()).toString();
    }

    private static String genAccessTokenKey(String accessToken) {
        return new StringBuilder().append("token-accesstoken-").append(accessToken).toString();
    }

    private static String genRefreshToken(String refreshToken) {
        return new StringBuilder().append("token-refreshtoken-").append(refreshToken).toString();
    }
}
