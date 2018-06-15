package com.taotao.service.core.service.impl;

import com.sgcai.commons.lang.utils.Dui1DuiStringUtils;
import com.taotao.common.pojo.ClientType;
import com.taotao.service.core.redis.TokenRepository;
import com.taotao.service.core.service.TokenService;
import com.taotao.service.core.to.TokenTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public TokenTO createToken(String userId, ClientType clientType) {
        TokenTO tokenTO = new TokenTO();
        tokenTO.setUserId(userId);
        tokenTO.setClientType(clientType);
        tokenTO.setAccessToken(Dui1DuiStringUtils.generateUUID());
        tokenTO.setRefreshToken(Dui1DuiStringUtils.generateUUID());
        tokenRepository.save(tokenTO);
        return tokenTO;
    }

    @Override
    public TokenTO getToken(String accessToken) {
        return tokenRepository.getTokenByAccessToken(accessToken);
    }

    @Override
    public void deleteToken(String accessToken) {
        tokenRepository.delete(accessToken);
    }

    @Override
    public TokenTO refreshToken(String refreshToken) {
        return tokenRepository.getTokenByRefreshToken(refreshToken);
    }
}
