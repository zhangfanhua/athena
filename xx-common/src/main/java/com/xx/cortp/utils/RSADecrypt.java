package com.xx.cortp.utils;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;


public class RSADecrypt {
    public static void main(String[] args) throws Exception {
        String encrypted = "mD0QNMfmwf3Bre5VeP6bcdgHyCAIsKvX63fbUENUX81/e5Sp021lw1CEIeosOOski7zlSKdS0k3wgwj09Qi5Aps65ae4gX4Kl6cMW70kj6UhLznyBXSTlDaStVckkLD/hjE+nzz9qz/UhdY1UAl6+6zpgZTz7icDqmw3b0GoShg=";
        String prikey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBANjS5OIM2guSO1hc" +
                "0dXCXXjhEFSaNvnxVAV8P6zOpki7ZpN7JV4WXhUYzuB1ju0/9LMDQSzYPBqvKXp0" +
                "kZ9L8LEoedZayYzYmR45OwZcSOiTEnJYC6Sg2jxtI2b/hhqoOAfzfrEelpdeNgV1" +
                "C4EhEqKVSBHACDBU3A2/1+tCxqPLAgMBAAECgYA5Hq3fg0U6AScTKzi4WIDpZFk6" +
                "AHp1NAdPfqEDtFkIFh56wdlhRQE6C5QMe8vQYqXjNvtHhhunZ/fEY8stLQNWHE0r" +
                "oFQz/lDhzCoIA0nyXwSOZF0bKuuKM3twM+Ns9ync+fTFhljKMTjF4pomZlKwK4EJ" +
                "JBZBsowbpCYHDhBi4QJBAO/n/JqYTs7NecL2FtpiYSi6HL1iGfx+hS3DKTlu8ygp" +
                "078gGRkvX4SQCcrre2C4iPYZ51YtTE73EOBe/loYIzECQQDnXoDdvxgSWlenZVxx" +
                "ElmNzJPOtwMWQQn8WGbyB8e4Lkn5axnsuaq8JExs5qtAQRfiVPXNlIWPCeQfKYUo" +
                "gh+7AkAZZbvOOfWN2x7azuaYc/XJM/q66dnKazJ6J8EDfYVsaRErmKBPlD5OcFk5" +
                "DDjhgmetdgyRiPYdHfbBag0PSKLhAkAb1j3w8AXoZ2A2braRkCCgM+XwsAo6Cjc2" +
                "WjiAlDkOSttxm9YxqiEFo+RiEdq2z14dSBWO13i+PfVpXmh7+DPzAkB+1Yk6rogc" +
                "kQ+n41U62tOJC5HMOtpQpmnxZDorIs71oz0Zmr+A4VqsCV4pgFOKIqQGVuzZYRAa" +
                "vG+O1j7VseUr";

        String result = decrypt(encrypted, prikey);
        System.out.println(result);
    }

    public static String decrypt(String cryptograph, String prikey) throws Exception {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(prikey));
        PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(keySpec);

        Cipher cipher=Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        byte [] b = Base64.getDecoder().decode(cryptograph);
        return new String(cipher.doFinal(b));
    }
}