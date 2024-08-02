package com.rookie.bigdata.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Class KeyUtilsTest
 * @Description
 * @Author rookie
 * @Date 2024/8/2 16:42
 * @Version 1.0
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class KeyUtilsTest {

    @Test
    void getRSAPrivateKey()throws Exception{
        RSAPrivateKey rsaPrivateKey = KeyUtils.getRSAPrivateKey("pkcs/app.key");
    }

    @Test
    void getRSAPublicKey()throws Exception{

        RSAPublicKey rsaPublicKey = KeyUtils.getRSAPublicKey("pkcs/app.pub");

    }

}
