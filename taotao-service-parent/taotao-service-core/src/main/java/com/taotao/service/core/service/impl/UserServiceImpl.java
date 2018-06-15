package com.taotao.service.core.service.impl;

import com.sgcai.commons.lang.utils.BeanConvertUtils;
import com.taotao.service.core.dao.UserMapper;
import com.taotao.service.core.entity.User;
import com.taotao.service.core.service.UserService;
import com.taotao.service.core.to.UserTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserTO getUserById(String id) {
        User user  = userMapper.selectByPrimaryKey(Long.valueOf(id));
        return BeanConvertUtils.convert(user,UserTO.class);
    }
}
