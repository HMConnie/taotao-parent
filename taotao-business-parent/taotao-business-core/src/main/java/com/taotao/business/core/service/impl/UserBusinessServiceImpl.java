package com.taotao.business.core.service.impl;

import com.sgcai.commons.lang.utils.BeanConvertUtils;
import com.taotao.business.core.service.UserBusinessService;
import com.taotao.business.core.to.UserBTO;
import com.taotao.service.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserBusinessServiceImpl implements UserBusinessService {


    @Autowired
    private UserService userService;

    @Override
    public UserBTO getUserById(String id) {
        return BeanConvertUtils.convert(userService.getUserById(id), UserBTO.class);
    }
}
