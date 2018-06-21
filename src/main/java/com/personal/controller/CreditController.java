package com.personal.controller;

import com.personal.service.CreditService;
import com.user.model.DO.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author: 李亚卿
 * @Date: Created in 23:32 2018/6/20 0020
 * @Description:
 */
@Controller
public class CreditController {

    @Autowired
    private CreditService creditService;

    @RequestMapping("getCreditNum")
    @ResponseBody
    public Map getCreditNum(String userId) {
        return creditService.getCreditNum(userId);
    }
}
