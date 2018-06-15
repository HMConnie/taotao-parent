package com.taotao.service.core.to;

import com.sgcai.commons.lang.base.BasicTO;
import com.taotao.common.pojo.ClientType;

public class TokenTO extends BasicTO {
    private static final long serialVersionUID = 1L;
    private String userId;
    private String accessToken;
    private String refreshToken;
    private ClientType clientType;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public ClientType getClientType() {
        return clientType;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }
}
