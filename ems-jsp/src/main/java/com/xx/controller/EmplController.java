package com.xx.controller;

import com.xx.entity.Empl;
import com.xx.service.EmplService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author xiaoxing
 * @create 2021-12-12 11:19
 */
@Controller
@RequestMapping("empl")
public class EmplController {

    private static final Logger log = LoggerFactory.getLogger(EmplController.class);
    private EmplService emplService;

    @Autowired
    public EmplController(EmplService emplService) {
        this.emplService = emplService;
    }

    @RequestMapping("delete")
    public String delete(Integer id){
        emplService.delete(id);
        return "redirect:/empl/list";
    }


    /**
     * 更新员工信息
     * @param empl
     * @return
     */
    @RequestMapping("update")
    public String update(Empl empl){
        //1.更新数据
        emplService.update(empl);
        //2.跳转到员工列表
        return "redirect:/empl/list";
    }

    /**
     * 员工详细信息
     * @return
     */
    @RequestMapping("detail")
    public String detail(Integer id,Model model){
        log.debug("id:{}",id);
        //1.根据id查询员工信息
        Empl empl = emplService.selectById(id);
        //2.将查询到的员工信息保存到作用域
        model.addAttribute("empl",empl);
        //3.跳转到更新页面展示修改员工信息
        return "updateEmp";
    }


    /**
     * 添加员工信息
     * @return
     */
    @RequestMapping("add")
    public String addEmpl(Empl empl){
        log.debug("员工姓名:{},工资:{},生日:{},性别:{}",
                empl.getName(),empl.getSalary(),empl.getBirthday(),empl.getGender());
        //保存员工
        emplService.add(empl);
        return "redirect:/empl/list";
    }


    /**
     * 员工列表
     * @param model
     * @return
     */
    @RequestMapping("list")
    public String listEmployee(Model model){
        //获取员工列表
        List<Empl> emplList = emplService.list();
        model.addAttribute("emplList",emplList);
        return "emplist";//forward跳转
    }

}
