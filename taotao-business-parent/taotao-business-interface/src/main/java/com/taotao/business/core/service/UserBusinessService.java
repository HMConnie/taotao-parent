package com.taotao.business.core.service;

import com.taotao.business.core.to.UserBTO;

public interface UserBusinessService {
    UserBTO getUserById(String id);
}
