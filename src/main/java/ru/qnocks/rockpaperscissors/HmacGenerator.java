package ru.qnocks.rockpaperscissors;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HmacGenerator {

    private final String ALGORITHM = "HmacSHA256";

    private String key;

    public HmacGenerator() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
            SecureRandom random = new SecureRandom();
            keyGenerator.init(random);
            SecretKey secretKey = keyGenerator.generateKey();
            key = new BigInteger(1, secretKey.getEncoded()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            key = "";
        }
    }

    public String getKey() {
        return key;
    }

    public String getHmac(String value)  {
        try {
            Mac hmac = Mac.getInstance(ALGORITHM);
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            hmac.init(secretKey);
            return Hex.encodeHexString(hmac.doFinal(value.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            return "";
        }
    }
}
