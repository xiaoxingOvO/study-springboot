package com.xx.smsthymeleaf.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xiaoxing
 * @create 2021-12-04 11:48
 */
@Controller
@RequestMapping("demo")
public class TestController {

    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @RequestMapping(value = "demo")
    public String demo(Model model) {
        log.debug("demo ok");
        model.addAttribute("msg","hello thymeleaf");
        return "demo";
    }

}
