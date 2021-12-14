package com.xx;

import com.xx.utils.VerifyCodeUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SpringBootTest
class EmsJspApplicationTests {


    /**
     * 验证码测试
     */
    @Test
    void contextLoads() throws IOException {
        //1.生成数字
        String code = VerifyCodeUtils.generateVerifyCode(4);
        System.out.println(code);
        //2.生成图片
        // VerifyCodeUtils.outputImage(220,80,os,code);

    }

}
