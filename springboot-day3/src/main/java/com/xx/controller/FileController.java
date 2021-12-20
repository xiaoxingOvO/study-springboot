package com.xx.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("file")
public class FileController {

    private static final Logger log = LoggerFactory.getLogger(FileController.class);

    @Value("${file.upload.dir}")
    private String realPath;

    @Value("${file.download.dir}")
    private String realPath1;

    /**
     * 文件下载
     *
     * @param fileName
     */
    @RequestMapping("download")
    public void download(String fileName, HttpServletResponse response) throws IOException {
        log.debug("当前下载文件名是: {}", fileName);
        log.debug("当前下载文件目录: {}", realPath1);
        //1.去指定目录中读取文件
        File file = new File(realPath1, fileName);
        //2.将文件读取为文件输入流
        FileInputStream is = new FileInputStream(file);
        //2.5 获取响应流之前 一定要设置以附件形式下载 attachment:附件
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        //3.获取响应输出流
        ServletOutputStream os = response.getOutputStream();
        //4.输入流复制给输出流
        /*int len=0;
        byte[] b = new byte[1024];
        while(true){
            len = is.read(b);
            if(len==-1)break;
            os.write(b,0,len);
        }*/
        FileCopyUtils.copy(is, os);
    }


    /**
     * 第二种文件上传
     * 注意: 这种方式适用于任何一种部署方式 推荐使用这种方式
     *
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping("uploadByJarDeploy")
    public String uploadByJarDeploy(MultipartFile file) throws IOException {
        //文件名
        String originalFilename = file.getOriginalFilename();
        log.debug("文件名: {}", originalFilename);
        log.debug("文件大小: {}", file.getSize());
        log.debug("文件类型: {}", file.getContentType());

        //改名
        String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFileName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + ext;


        //上传文件到哪
        file.transferTo(new File(realPath, newFileName));

        return "redirect:/upload.jsp";
    }


    /**
     * 第一种方式
     * 用来测试文件上传 - 这种方式:不能用于jar包部署
     * 注意:这种方式存在局限性,不推荐在使用这种方式进行文件上传了
     *
     * @return
     */
    @RequestMapping("upload")
    public String upload(MultipartFile file, HttpServletRequest request) throws IOException {//定义:接收文件对象 multipartFile file变量名要与form中input type="file"标签name属性名一致
        //文件名
        String originalFilename = file.getOriginalFilename();
        log.debug("文件名: {}", originalFilename);
        log.debug("文件大小: {}", file.getSize());
        log.debug("文件类型: {}", file.getContentType());

        //1.根据相对 上传 "upload" 获取绝对路径(真实路径) /users/桌面....   服务器: /home/springboot_day4..
        String realPath = request.getSession().getServletContext().getRealPath("/upload");
        log.debug("获取绝对路径: {}", realPath);

        //2.上传文件  参数1: 将文件写入到那个目录 aa.txt  .md xxx.xxx.xxx.md
        //修改文件名
        String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFileName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + ext;
        file.transferTo(new File(realPath, newFileName));

        return "redirect:/upload.jsp";
    }
}
