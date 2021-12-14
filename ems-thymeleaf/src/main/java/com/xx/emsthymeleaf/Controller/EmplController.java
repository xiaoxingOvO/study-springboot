package com.xx.emsthymeleaf.Controller;

import com.xx.emsthymeleaf.mapper.EmplMapper;
import com.xx.emsthymeleaf.pojo.Employee;
import com.xx.emsthymeleaf.service.EmplService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import sun.java2d.loops.FillPath;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author xiaoxing
 * @create 2021-12-06 15:36
 */
@Controller
@RequestMapping("employee")
public class EmplController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private EmplService emplService;

    @Value("${photo.file.dir}")
    private String realpath;


    @Autowired
    public EmplController(EmplService emplService) {
        this.emplService = emplService;
    }



    /**
     * 删除员工
     * @param id
     * @return
     */
    @RequestMapping("delete")
    public String delete(Integer id){
        log.debug("删除员工id:{}",id);
        //删除头像
        String photo = emplService.findById(id).getPhoto();
        File file = new File(realpath, photo);
        if (file.exists())file.delete();
        //删除数据
        emplService.delete(id);
        return "redirect:/employee/lists";
    }

    /**
     * 更新员工信息
     * @return
     */
    @RequestMapping("update")
    public String update(Employee employee,MultipartFile img) throws IOException {
        log.debug("更新之后的员工信息:id:{},姓名:{},薪资:{},生日:{}",
                employee.getId(),employee.getName(), employee.getSalary(), employee.getBirthday());
        //判断是否更新头像
        boolean notEmpty = !img.isEmpty();
        log.debug("是否更新头像:{}",notEmpty);
        if (notEmpty){
            //1.删除老的头像
            String oldPhoto = emplService.findById(employee.getId()).getPhoto();
            File file = new File(realpath, oldPhoto);
            if (file.exists())file.delete();
            //2.上传新的头像
            String originalFilename = img.getOriginalFilename();
            String newFileName = uploadPhoto(img,originalFilename);
            employee.setPhoto(newFileName);
        }
        //2.没有更新头像直接更新基本信息
        emplService.update(employee);
        return "redirect:/employee/lists";
    }

    //上传头像
    private String uploadPhoto(MultipartFile img,String originalFilename) throws IOException {
        String fileNamePrefix = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        String fileNameSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFileName = fileNamePrefix + fileNameSuffix;
        img.transferTo(new File(realpath,newFileName));
        return newFileName;
    }

    /**
     * 根据id查询员工信息
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("detail")
    public String detail(Integer id,Model model){
        log.debug("当前查询员工id:{}",id);
        //1.根据id查询员工
        Employee employee = emplService.findById(id);
        model.addAttribute("employee",employee);
        return "updateEmp";
    }

    /**
     * 保存员工信息
     *
     * @return
     */
    @RequestMapping("save")
    public String save(Employee employee, MultipartFile img) throws IOException {
        log.debug("姓名:{},薪资:{},生日:{}", employee.getName(), employee.getSalary(), employee.getBirthday());
        log.debug("头像名称:{}", img.getOriginalFilename());
        log.debug("上传的路径:{}", realpath);
        //1.处理头像的上传&修改文件名
        String originalFilename = img.getOriginalFilename();
        String newFileName = uploadPhoto(img, originalFilename);

        //保存员工信息
        employee.setPhoto(newFileName);//保存头像名字
        emplService.save(employee);

        return "redirect:/employee/lists";
    }


    /**
     * 员工列表
     *
     * @return
     */
    @RequestMapping("lists")
    public String lists(Model model) {
        log.debug("查询所有员工信息");
        List<Employee> employeeList = emplService.lists();
        model.addAttribute("employeeList", employeeList);
        return "emplist";
    }

    ;
}
