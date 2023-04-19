package com.review.Encryptions;

import com.review.models.Product;
import com.review.models.ProductDetail;
import com.review.models.Rate;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {
    // Class private variables
    private  SecretKey key;
    private  IvParameterSpec iv;
    private  String password="";
    private  String salt="";
    public  void createkey() throws NoSuchAlgorithmException, InvalidKeySpecException{
        SecureRandom sr= new SecureRandom();
        KeyPairGenerator kpg=KeyPairGenerator.getInstance("RSA");
        kpg.initialize(1048, sr);
        if(password.equals("")){
            password=kpg.genKeyPair().getPrivate().getEncoded().toString();
            salt=kpg.genKeyPair().getPublic().getEncoded().toString();
        }
        byte[] ar={1,2,1,1,0,7,1,1,1,4,0,9,1,2,0,4};
        iv= new IvParameterSpec(ar);
        key=getkeyFromPassword(password, salt);
    }
    public  SecretKey getkeyFromPassword(String password,String salt) throws InvalidKeySpecException, NoSuchAlgorithmException{
        SecretKeyFactory factory=SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec=new PBEKeySpec(password.toCharArray(),salt.getBytes(),65536,256);
        SecretKey secret=new SecretKeySpec(factory.generateSecret(spec).getEncoded(),"AES");
        return secret;
    }
    public  String encrypt(String strToEncrypt)
    {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key,iv);
            // Return encrypted string
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(
                    StandardCharsets.UTF_8)));
        }
        catch (Exception e) {
            System.out.println("Error while encrypting: "
                    + e.toString());
        }
        return null;
    }

    // This method use to decrypt to string
    public  String decrypt(String strToDecrypt)
    {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, key,iv);

            // Return decrypted string
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        }
        catch (Exception e) {
            System.out.println("Error while decrypting: "
                    + e.toString());
        }
        return null;
    }
    public  String encryptList(List<Product> list)
    {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key,iv);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(list);
            return Base64.getEncoder().encodeToString(cipher.doFinal(bos.toByteArray()));
        }
        catch (Exception e) {
            System.out.println("Error while encrypting: "
                    + e.toString());
        }
        return null;
    }

    public List<Product> decryptList(String strToDecrypt) throws IOException, ClassNotFoundException{
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, key,iv);
            ByteArrayInputStream bis = new ByteArrayInputStream(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
            ObjectInputStream ois = new ObjectInputStream(bis);
            List<Product> list = (List<Product>)ois.readObject();
            return list;
        }
        catch (Exception e) {
            System.out.println("Error while decrypting: "
                    + e.toString());
        }
        return null;
    }
    public  String encryptListReviews(List<Rate> Rate)
    {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key,iv);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(Rate);
            return Base64.getEncoder().encodeToString(cipher.doFinal(bos.toByteArray()));
        }
        catch (Exception e) {
            System.out.println("Error while encrypting: "
                    + e.toString());
        }
        return null;
    }
    public  String encryptProductDetail(ProductDetail productDetail)
    {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key,iv);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(productDetail);
            return Base64.getEncoder().encodeToString(cipher.doFinal(bos.toByteArray()));
        }
        catch (Exception e) {
            System.out.println("Error while encrypting: "
                    + e.toString());
        }
        return null;
    }
    public List<Rate> decryptListReviews(String strToDecrypt) throws IOException, ClassNotFoundException{
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, key,iv);
            ByteArrayInputStream bis = new ByteArrayInputStream(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
            ObjectInputStream ois = new ObjectInputStream(bis);
            List<Rate> list = (List<Rate>)ois.readObject();
            return list;
        }
        catch (Exception e) {
            System.out.println("Error while decrypting: "
                    + e.toString());
        }
        return null;
    }
    public ProductDetail decryptProductDetail(String strToDecrypt) throws IOException, ClassNotFoundException{
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, key,iv);
            ByteArrayInputStream bis = new ByteArrayInputStream(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
            ObjectInputStream ois = new ObjectInputStream(bis);
            ProductDetail productDetail = (ProductDetail)ois.readObject();
            return productDetail;
        }
        catch (Exception e) {
            System.out.println("Error while decrypting: "
                    + e.toString());
        }
        return null;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

}
