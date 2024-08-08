package com.rookie.bigdata.util;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.digest.DigestUtil;
import org.springframework.util.Base64Utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Objects;

/**
 * @Class PKCEUtils
 * @Description https://blog.csdn.net/weixin_44951259/article/details/132597283
 * @Author rookie
 * @Date 2024/8/8 16:27
 * @Version 1.0
 */
public class PKCEUtils {


    public static void t() {
        // PCKE 流程所需参数
// codeVerifier：随机编号
        String codeVerifier = IdUtil.fastSimpleUUID();

        Base64.getUrlEncoder().encodeToString(DigestUtil.sha256(codeVerifier))
                .replaceAll("=", "")
                .replaceAll("\\+", "-")
                .replaceAll("/", "_");
// codeChallenge：codeVerifier 的 SHA256
//        String codeChallenge = Base64Utils.encodeToUrlSafeString()
//                .replaceAll("=", "")
//                .replaceAll("\\+", "-")
//                .replaceAll("/", "_");

    }


    // 生成随机码
    public static String generateCodeVerifier() {
        SecureRandom random = new SecureRandom();
        byte[] codeVerifier = new byte[32];
        random.nextBytes(codeVerifier);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(codeVerifier);
    }

    // 生成令牌(challenge)
    public static String generateCodeChallenge(String codeVerifier) throws NoSuchAlgorithmException {
        Objects.requireNonNull(codeVerifier);
        String codeChallenge = Base64.getUrlEncoder().withoutPadding().encodeToString(
                MessageDigest.getInstance("SHA-256").digest(codeVerifier.getBytes(StandardCharsets.US_ASCII)));
        return codeChallenge;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String codeVerifier = generateCodeVerifier();
        String codeChallenge = generateCodeChallenge(codeVerifier);

        System.out.println("Code Verifier: " + codeVerifier);
        System.out.println("Code Challenge: " + codeChallenge);
    }
}
