package com.xx;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author xiaoxing
 * @create 2021-12-15 13:15
 */
@SpringBootTest
public class JasyptTests {

    private StringEncryptor stringEncryptor;

    @Autowired
    public JasyptTests(StringEncryptor stringEncryptor) {
        this.stringEncryptor = stringEncryptor;
    }

    @Test
    public void testSecret() {

        // String secret = stringEncryptor.encrypt("root");
        // System.out.println("root=" + secret);
        //
        // String localhost = stringEncryptor.encrypt("localhost");
        // System.out.println("localhost=" + localhost);

        String decrypt = stringEncryptor.decrypt("vCjTcQvSRDGAyeX61tNyUQ==");
        System.out.println(decrypt);
    }
}
