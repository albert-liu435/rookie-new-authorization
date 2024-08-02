package com.rookie.bigdata.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @Class KeyUtils
 * @Description
 * @Author rookie
 * @Date 2024/8/2 16:36
 * @Version 1.0
 */
public class KeyUtils {


    //    public static
    public static RSAPrivateKey getRSAPrivateKey(String path) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {

        InputStream inputStream = KeyUtils.class.getClassLoader().getResourceAsStream(path);
        ByteArrayOutputStream array = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            array.write(buffer, 0, length);
        }

        // \\s+表示出现空白匹配
        String privateKey = array.toString("utf-8")
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s+", "");

        KeyFactory kf = KeyFactory.getInstance("RSA");
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) kf.generatePrivate(
                new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey)));

        return rsaPrivateKey;
    }


    public static RSAPublicKey getRSAPublicKey(String path) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        InputStream inputStream = KeyUtils.class.getClassLoader().getResourceAsStream(path);


        ByteArrayOutputStream array = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            array.write(buffer, 0, length);
        }

        // \\s+表示出现空白匹配
        String privateKey = array.toString("utf-8")
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s+", "");

        KeyFactory kf = KeyFactory.getInstance("RSA");

        byte[] x509 = Base64.getDecoder().decode(privateKey.toString());

        RSAPublicKey rsaPublicKey = (RSAPublicKey) kf.generatePublic(new X509EncodedKeySpec(x509));
        return rsaPublicKey;

    }
}
