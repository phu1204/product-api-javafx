package com.review.Encryptions;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.security.spec.PKCS8EncodedKeySpec;
import javax.crypto.Cipher;

public class RSA {
    public  PublicKey publickey;
    private  PrivateKey privatekey;
    public byte[] keyp;
    public  void createkey() throws NoSuchAlgorithmException{
        SecureRandom sr=new SecureRandom();
        KeyPairGenerator kpg=KeyPairGenerator.getInstance("RSA");
        kpg.initialize(1024,sr);
        KeyPair kp=kpg.genKeyPair();
        publickey=kp.getPublic();
        privatekey=kp.getPrivate();
    }
    public  String mahoa(String s) {
        try {

            // Tạo public key
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyp);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PublicKey pubKey = factory.generatePublic(spec);
            Cipher c = Cipher.getInstance("RSA");
            c.init(Cipher.ENCRYPT_MODE, pubKey);
            byte encryptOut[] = c.doFinal(s.getBytes());
            String strEncrypt = Base64.getEncoder().encodeToString(encryptOut);
            return strEncrypt;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "fall";
    }
    public  String giaima(String s) {
        try {
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(privatekey.getEncoded());
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PrivateKey priKey = factory.generatePrivate(spec);

            // Giải mã dữ liệu
            Cipher c = Cipher.getInstance("RSA");
            c.init(Cipher.DECRYPT_MODE, priKey);
            byte decryptOut[] = c.doFinal(Base64.getDecoder().decode(
                    s));

            return new String(decryptOut);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "fall";
    }
}

