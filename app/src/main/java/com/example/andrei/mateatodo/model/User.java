package com.example.andrei.mateatodo.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.util.Base64;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Andrei on 1/15/2018.
 */

@Entity(tableName = "user")
public class User {

    @SerializedName("id")
    @Expose
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @SerializedName("username")
    @ColumnInfo(name = "username")
    private String username;

    @SerializedName("password")
    @ColumnInfo(name = "password")
    private String password;

    @SerializedName("email")
    @ColumnInfo(name = "email")
    private String email;

    public User(String username, String password, String email) {
        this.username = username;
        try {
            String aesCrypt = new AESCrypt().encrypt(password);
            this.password = aesCrypt;
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        try {
            String aesCrypt = AESCrypt.encrypt(password);
            this.password = aesCrypt;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User() {
    }

    public User(User anUser) {
        this.id = anUser.getId();
        this.username = anUser.getUsername();
        this.password = anUser.getPassword();
        this.email = anUser.getEmail();
    }

    public static class AESCrypt {
        private static final String ALGORITHM = "AES";
        private static final String KEY = "1Hbfh667adfDEJ78";

        public static String encrypt(String value) throws Exception {
            Key key = generateKey();
            Cipher cipher = Cipher.getInstance(AESCrypt.ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedByteValue = cipher.doFinal(value.getBytes("utf-8"));
            String encryptedValue64 = Base64.encodeToString(encryptedByteValue, Base64.DEFAULT);
            return encryptedValue64;

        }

        public static String decrypt(String value) throws Exception {
            Key key = generateKey();
            Cipher cipher = Cipher.getInstance(AESCrypt.ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedValue64 = Base64.decode(value, Base64.DEFAULT);
            byte[] decryptedByteValue = cipher.doFinal(decryptedValue64);
            String decryptedValue = new String(decryptedByteValue, "utf-8");
            return decryptedValue;

        }

        private static Key generateKey() throws Exception {
            Key key = new SecretKeySpec(AESCrypt.KEY.getBytes(), AESCrypt.ALGORITHM);
            return key;
        }
    }


}
