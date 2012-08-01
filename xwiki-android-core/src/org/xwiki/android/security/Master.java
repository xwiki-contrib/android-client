package org.xwiki.android.security;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.SecretKeySpec;

import org.xwiki.android.security.util.Base64;

import android.util.Log;

/**
 * @author xwiki gsoc 2012 
 * This is the Master class that will encrypt decrypt all user passwords. 
 * This has methods to generate private keys for users and keep them safe.
 * All user's private secret keys are encrypted using the master key. 
 */
public class Master
{

    private static final String TAG = "Security";
    SecretKeySpec masterKey;
    Cipher masterCipher;
    

    public Master()
    {
        masterKey = getMasterKey();
        try {
            masterCipher = Cipher.getInstance("AES/ECB/PKCS7Padding");            
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "ENC algo missing: pwds will be saved in plaintext", e);
        } catch (NoSuchPaddingException e) {
            Log.e(TAG, "Padding scheme missing: pwds will be saved in plaintext", e);
        }
        

    }

    /**
     * Encrypt a secret string.
     * @param plainTextPwd
     * @return BASE64 encoded password cypher text string.
     */
    public String encryptPassword(String plainTextPwd)
    {
        if (masterCipher == null)
            return plainTextPwd;

        byte[] input = null;

        try {
            input = plainTextPwd.getBytes("UTF8");
            masterCipher.init(Cipher.ENCRYPT_MODE, masterKey);
            byte[] output = new byte[masterCipher.getOutputSize(input.length)];
            int outputLen = masterCipher.update(input, 0, input.length, output, 0);
            masterCipher.doFinal(output, outputLen);
            String out=Base64.encodeToString(output, true);
            return out;
        } catch (InvalidKeyException e) {
            Log.e(TAG, "invalid key. In Master");
        } catch (ShortBufferException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            Log.d(TAG, "", e);

        } catch (BadPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            Log.e(TAG, "UTF8 not supported ?");
        }
        return plainTextPwd;
    }

    /**
     * 
     * @param pwdCipherText : base 64 encoded cypher text.  
     * @return UTF8 encoded plain text.
     */
    public String decryptPassword(String pwdCipherText)
    {
        if (masterCipher == null)
            return pwdCipherText;

        byte[] input = null;

        try {
            input =Base64.decode(pwdCipherText);
            masterCipher.init(Cipher.DECRYPT_MODE, masterKey);
            /*byte[] output = new byte[masterCipher.getOutputSize(input.length)];
            int outputLen = masterCipher.update(input, 0, input.length, output, 0);
            masterCipher.doFinal(output, outputLen);*/
            byte[] output=masterCipher.doFinal(input);
            return new String(output, "UTF8");
        } catch (InvalidKeyException e) {
            Log.e(TAG, "invalid key. In Master");
//        } catch (ShortBufferException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (BadPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "UTF8 not supported ?");
        }
        return pwdCipherText;
    }
    /**
     * Encrypt a secret key.
     * @param key as a  encoded String.
     * @return BASE64 encoded password cypher text string.
     */
    public String encryptKey(String key){        
        return encryptPassword(key);
    }
    /**
     * 
     * @param keyCipher should be a base64 encoded string.
     * @return key base64 coded key String coded again with UTF8.
     */
    public String decryptKey(String keyCipher){
        return decryptPassword(keyCipher);
    }
    
    public byte[] generateRawKey(String algorithm){
     // Generate a key
        KeyGenerator keyGen;
        try {
            keyGen = KeyGenerator.getInstance(algorithm);
            SecretKey key = keyGen.generateKey();

            // Get the bytes of the key
            byte[] keyBytes = key.getEncoded();
            return keyBytes;
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;        
    }
    
    public String generateEncKey(String algorithm){
        byte[] keyBytes=generateRawKey(algorithm);
        return Base64.encodeToString(keyBytes, true);
    }

    private SecretKeySpec getMasterKey()
    {
        byte[] keyBytes =
            new byte[] {0x01, 0x05, 0x07, 0x03, 0x04, 0x08, 0x05, 0x12, 0x17, 0x09, 0x0a, 0x0c, 0x05, 0x09, 0x1e, 0x1a,
            0x1a, 0x11, 0x14, 0x13, 0x14, 0x19, 0x16, 0x17};
        // TODO save this somewhere safe ;-)
        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
        return key;
    }

}
