package com.rookie.bigdata.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

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
@Slf4j
class KeyUtilsTest {

    @Test
    void getRSAPrivateKey()throws Exception{
        RSAPrivateKey rsaPrivateKey = KeyUtils.getRSAPrivateKey("pkcs/app.key");
    }

    @Test
    void getRSAPublicKey()throws Exception{

        RSAPublicKey rsaPublicKey = KeyUtils.getRSAPublicKey("pkcs/app.pub");

    }
    @Test
    void test01() throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = md.digest("oXewzwdnnkTqlCSteHt9i8OYCWltjpQ-AjGTentF6E0".getBytes(StandardCharsets.US_ASCII));
        String encodedVerifier = Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
        System.out.println(encodedVerifier);
        //GrTUK58RJrTL9OT3yKRGHK0x_abkf7LjKCI2bOk9DtM
        //GrTUK58RJrTL9OT3yKRGHK0x_abkf7LjKCI2bOk9DtM
        log.info("生成数据:{}",encodedVerifier);
    }

}
