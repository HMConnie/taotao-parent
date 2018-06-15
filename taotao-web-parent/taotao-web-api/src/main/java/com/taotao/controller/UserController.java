package com.taotao.controller;

import com.taotao.business.core.service.UserBusinessService;
import com.taotao.business.core.to.UserBTO;
import com.taotao.common.pojo.TaotaoResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserBusinessService userBusinessService;

    @RequestMapping(value = "/get",method = RequestMethod.GET)
    @ResponseBody
    public TaotaoResult getUser(@RequestParam("id")String id){
        UserBTO userBTO = userBusinessService.getUserById(id);
        return TaotaoResult.ok(userBTO);
    }
}
