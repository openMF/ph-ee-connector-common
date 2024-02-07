package org.mifos.connector.common.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.*;

public final class SecurityUtil {

    private SecurityUtil() {}

    public static String hash(String data) {
        return new DigestUtils("SHA3-256").digestAsHex(data);
    }

    /**
     * Encrypts the [content] using the [privateKey]
     *
     * @param content
     *            data to be encrypted
     * @param key
     *            encryption key
     * @return encrypted data
     * @throws NoSuchPaddingException
     *             see @getSecretKey
     * @throws IllegalBlockSizeException
     *             see @encryptFromCipher
     * @throws NoSuchAlgorithmException
     *             see @getCipher
     * @throws BadPaddingException
     *             see @encryptFromCipher
     * @throws InvalidKeySpecException
     *             see @getSecretKey
     * @throws InvalidKeyException
     *             see @encrypt
     */
    public static String signContent(String content, String key) throws NoSuchPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, BadPaddingException, InvalidKeySpecException, InvalidKeyException {

        return encryptUsingPublicKey(content, key);
    }

    /**
     * Generates [SecretKey] instance using custom password and salt
     *
     * @param key
     *            the base key used for generating secret
     * @return [SecretKey] An instance of the [SecretKey]
     */
    public static SecretKey getSecretKey(String key) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("RSA");
        KeySpec spec = new PBEKeySpec(key.toCharArray(), key.getBytes(), 65536, 2048);
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "RSA");
    }

    /**
     * Generates [PublicKey] object from String public key
     *
     * @param key
     *            string value of public key
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PublicKey getPublicKeyFromString(String key) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = Base64.decodeBase64(key);
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(publicKeySpec);
    }

    public static String getStringFromPublicKey(PublicKey publicKey) {
        byte[] keyBytes = publicKey.getEncoded();
        return new String(Base64.encodeBase64(keyBytes), Charset.defaultCharset());
    }

    /**
     * Generates [PrivateKey] object from String public key
     *
     * @param key
     *            string value of public key
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PrivateKey getPrivateKeyFromString(String key) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = Base64.decodeBase64(key);
        EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = getKeyFactory();
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * Applies given cipher on a plain text
     *
     * @param input
     *            text to be encoded
     * @param cipher
     *            teh instance of the [Cipher]
     * @return [String] encrypted data as a Base64 encoded text
     */
    private static String encryptFromCipher(String input, Cipher cipher) throws IllegalBlockSizeException, BadPaddingException {
        byte[] cipherText = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));
        return Base64.encodeBase64String(cipherText);
    }

    /**
     * Applies given cipher on a plain text
     *
     * @param input
     *            text to be encoded
     * @param cipher
     *            teh instance of the [Cipher]
     * @return [String] encrypted data as a Base64 encoded text
     */
    private static String decryptFromCipher(String input, Cipher cipher) throws IllegalBlockSizeException, BadPaddingException {
        byte[] cipherText = cipher.doFinal(Base64.decodeBase64(input));
        return new String(cipherText, StandardCharsets.UTF_8);
    }

    // get key factory
    private static KeyFactory getKeyFactory() throws NoSuchAlgorithmException {
        return KeyFactory.getInstance("RSA");
    }

    /**
     * @return [Cipher] returns the default instance of [Cipher]
     */
    private static Cipher getCipher() throws NoSuchPaddingException, NoSuchAlgorithmException {
        return Cipher.getInstance("RSA");
    }

    private static String applyCipher(String input, Key key, int cipherMode)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = getCipher();
        switch (cipherMode) {
            case (Cipher.ENCRYPT_MODE):
                cipher.init(Cipher.ENCRYPT_MODE, key);
                return encryptFromCipher(input, cipher);
            case (Cipher.DECRYPT_MODE):
                cipher.init(Cipher.DECRYPT_MODE, key);
                return decryptFromCipher(input, cipher);
            default:
                return null;
        }
    }

    /**
     * Encrypts the string data using [key] (SecretKey) and [iv] (IvParameterSpec)
     *
     * @param input
     *            text to be encoded
     * @param encKey
     *            secret key to be used for encryption
     * @return [String] encoded data as plain text
     */
    public static String encryptUsingPublicKey(String input, String encKey) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {
        PublicKey publicKey = getPublicKeyFromString(encKey);
        return encrypt(input, publicKey);
    }

    public static String encryptUsingPrivateKey(String input, String encKey) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {
        PrivateKey publicKey = getPrivateKeyFromString(encKey);
        return encrypt(input, publicKey);
    }

    public static String encrypt(String input, PublicKey publicKey)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        return applyCipher(input, publicKey, Cipher.ENCRYPT_MODE);
    }

    public static String encrypt(String input, PrivateKey privateKey)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        return applyCipher(input, privateKey, Cipher.ENCRYPT_MODE);
    }

    public static String decryptUsingPrivateKey(String input, String decKey) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {
        PrivateKey privateKey = getPrivateKeyFromString(decKey);
        return decrypt(input, privateKey);
    }

    public static String decryptUsingPublicKey(String input, String decKey) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {
        PublicKey privateKey = getPublicKeyFromString(decKey);
        return decrypt(input, privateKey);
    }

    public static String decrypt(String input, PublicKey publicKey)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        return applyCipher(input, publicKey, Cipher.DECRYPT_MODE);
    }

    public static String decrypt(String input, PrivateKey privateKey)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        return applyCipher(input, privateKey, Cipher.DECRYPT_MODE);
    }
}
