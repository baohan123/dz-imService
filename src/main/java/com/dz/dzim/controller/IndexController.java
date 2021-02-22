package com.dz.dzim.controller;

//import com.dz.dzim.service.SendMsgService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.Map;

@Controller
@Component
public class IndexController {
    @RequestMapping(value = "/index1", method = RequestMethod.GET)
    public String index1() {
        return "index1";
    }

    @RequestMapping(value = "/index2", method = RequestMethod.GET)
    public String index2() {
        return "index2";
    }

    @RequestMapping(value = "/index3", method = RequestMethod.GET)
    public String index3() {
        return "index3";
    }


    @RequestMapping(value = "/mqIndex", method = RequestMethod.GET)
    public String mqIndex() {
        System.out.println("------------");
        return "mq_index";
    }

    @RequestMapping(value = "/index_kf1", method = RequestMethod.GET)
    public String index_kf1() {

    return "index_kf1";
    }

    @RequestMapping(value = "/index_kf2", method = RequestMethod.GET)
    public String index_kf2() {
        return "index_kf2";
    }

    @RequestMapping(value = "/index_kf3", method = RequestMethod.GET)
    public String index_kf3() {
        return "index_kf3";
    }

    @RequestMapping(value = "/index_kf4", method = RequestMethod.GET)
    public String index_kf4() {
        return "index_kf4";
    }

    @RequestMapping(value = "/index_user1", method = RequestMethod.GET)
    public String index_user1() {
        return "index_user1";
    }

    @RequestMapping(value = "/index_user2", method = RequestMethod.GET)
    public String index_user2() {
        return "index_user2";
    }

    @RequestMapping(value = "/index_user3", method = RequestMethod.GET)
    public String index_user3() {
        return "index_user3";
    }

    @RequestMapping(value = "/index_user4", method = RequestMethod.GET)
    public String index_user4() {
        return "index_user4";
    }
}
