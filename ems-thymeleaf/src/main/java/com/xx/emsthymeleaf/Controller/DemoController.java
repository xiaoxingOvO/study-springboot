package com.xx.emsthymeleaf.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xiaoxing
 * @create 2021-12-06 12:41
 */
@Controller
@RequestMapping("demo")
public class DemoController {
    private static final Logger log = LoggerFactory.getLogger(DemoController.class);

    @RequestMapping("demo")
    public String demo(Model model){
        log.debug("demo ok!");
        model.addAttribute("msg","hello");
        return "demo";
    }
}
