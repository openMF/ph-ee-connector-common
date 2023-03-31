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
import java.util.Arrays;

public class SecurityUtil {

    public static String hash(String data) {
        return new DigestUtils("SHA3-256").digestAsHex(data);
    }

    /**
     * Encrypts the [content] using the [privateKey]
     *
     * @param content    data to be encrypted
     * @param key encryption key
     * @return encrypted data
     * @throws NoSuchPaddingException    see @getSecretKey
     * @throws IllegalBlockSizeException see @encryptFromCipher
     * @throws NoSuchAlgorithmException  see @getCipher
     * @throws BadPaddingException       see @encryptFromCipher
     * @throws InvalidKeySpecException   see @getSecretKey
     * @throws InvalidKeyException       see @encrypt
     */
    public static String signContent(String content, String key) throws
            NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException,
            BadPaddingException, InvalidKeySpecException, InvalidKeyException {

        return encrypt(content, key);
    }

    /**
     * Generates [SecretKey] instance using custom password and salt
     *
     * @param key the base key used for generating secret
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
     * @param key string value of public key
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
     * @param key string value of public key
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
     * @param input  text to be encoded
     * @param cipher teh instance of the [Cipher]
     * @return [String] encrypted data as a Base64 encoded text
     */
    private static String encryptFromCipher(String input, Cipher cipher) throws IllegalBlockSizeException, BadPaddingException {
        byte[] cipherText = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));
        return Base64.encodeBase64String(cipherText);
    }

    /**
     * Applies given cipher on a plain text
     *
     * @param input  text to be encoded
     * @param cipher teh instance of the [Cipher]
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

    private static String applyCipher(String input, Key key, int cipherMode) throws
            NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = getCipher();
        switch (cipherMode) {
            case(Cipher.ENCRYPT_MODE):
                cipher.init(Cipher.ENCRYPT_MODE, key);
                return encryptFromCipher(input, cipher);
            case(Cipher.DECRYPT_MODE):
                cipher.init(Cipher.DECRYPT_MODE, key);
                return decryptFromCipher(input, cipher);
            default:
                return null;
        }
    }

    /**
     * Encrypts the string data using [key] (SecretKey) and [iv] (IvParameterSpec)
     *
     * @param input  text to be encoded
     * @param encKey secret key to be used for encryption
     * @return [String] encoded data as plain text
     */
    public static String encrypt(String input, String encKey) throws
            NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {
        PublicKey publicKey = getPublicKeyFromString(encKey);
        return encrypt(input, publicKey);
    }

    public static String encrypt(String input, PublicKey publicKey) throws
            NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        return applyCipher(input, publicKey, Cipher.ENCRYPT_MODE);
    }

    public static String encrypt(String input, PrivateKey privateKey) throws
            NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        return applyCipher(input, privateKey, Cipher.ENCRYPT_MODE);
    }

    public static String decrypt(String input, String decKey) throws
            NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {
        PrivateKey privateKey = getPrivateKeyFromString(decKey);
        return decrypt(input, privateKey);
    }

    public static String decrypt(String input, PublicKey publicKey) throws
            NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        return applyCipher(input, publicKey, Cipher.DECRYPT_MODE);
    }

    public static String decrypt(String input, PrivateKey privateKey) throws
            NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        return applyCipher(input, privateKey, Cipher.DECRYPT_MODE);
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        String publicKeyString = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtPuP1m/jpt5O71c+5oNjfaZCCFuiz63936hoL2SfGvJtbRZjKZf5XHF8terfBzXb0W8HQ6o/204HOg/mm/fVGjdM29kiIB26u12hWNlJybqNvcUWDww0i54OkXPtvVoDG2DyTz6sCy5Y3sTxyVB5+hWs+lLeSyJ8cHzBH67HLfgsuZ+e3QNJkKtps/3Hkn7GE6gG4VApepdLNALvJY8v2l70Xkc4R2RRPhfBl5pWF+pFAUcXHRarybr+irjFLpM86ph/K1g/EuZ/LPiYGcizSR/U5jZhYDABzacPwEfqKILiLuzjCip3DpahfN0vZ/5Nel8+3y5zRVDDlamTqjjbiwIDAQAB";
        String privateKeyString = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC0+4/Wb+Om3k7vVz7mg2N9pkIIW6LPrf3fqGgvZJ8a8m1tFmMpl/lccXy16t8HNdvRbwdDqj/bTgc6D+ab99UaN0zb2SIgHbq7XaFY2UnJuo29xRYPDDSLng6Rc+29WgMbYPJPPqwLLljexPHJUHn6Faz6Ut5LInxwfMEfrsct+Cy5n57dA0mQq2mz/ceSfsYTqAbhUCl6l0s0Au8ljy/aXvReRzhHZFE+F8GXmlYX6kUBRxcdFqvJuv6KuMUukzzqmH8rWD8S5n8s+JgZyLNJH9TmNmFgMAHNpw/AR+ooguIu7OMKKncOlqF83S9n/k16Xz7fLnNFUMOVqZOqONuLAgMBAAECggEANNEtmxEwSOSb+LFng/JYOLUqlDHaA+3tJzaIoTwmSsDxOmLMMblOZrIgCR8wU3ReYHKclhy7Yg8VgNZfIKllIa992LM3iFPkyQV8LufK5vpwny9DTsTrGMvZyI0ilp4MRhM24/WQU/sEqI6lWXEJB/kHcE563UaFNnbSDaL+MeW7FgGT8lu8/IvTdTYPF5fj6ifcEn0hEZ/aLEg0m9DRWwIdcdqsJRIc0ugSeYTedtrdp6TBoPNgrIL2Jde+cJndWVFAbvpHza7bq6ej9wZbUZx8ICB+VhicVHAfBxvQL9Yr5B/ud8cUmmAg1t5Oe5JG8UIUD8oS2jD9bwj+NJKKUQKBgQDwBO205S4PhDkL55nZzLAC0CmmWMJhyAUpzar0YBo28K+L9Z3q7IfMmkIgaCQG1o0XIZYWpS0p13F1/ldLJTIfjZGRDSjran48ILu54P1EPaLRGymwdBqsvCJITSTxaurXW0ekRsXkcjGaxzqG3niZunCvDODVQBME4rgiVvJFZQKBgQDBCF5dqw+skPJTgUD9dude7f5qCDP//APTH3qUwExLEmHnMPOkXD//C3mu+jLuHHvuULwl19CkuNE4jJ+GgbuEYnrmgx/X5r7RRORYY/hZVcAhXZsuRwjaEuC9m9efvE3d4E9cDaiQIOweChsND0EVi9L4oJAhuXwR8+9B+arGLwKBgFE+2dfp2/WUpFrLQuDe0JWjMPYGBYZj1puX6s5d2YHPZxzRP2tONYmkjc26crd92LSDwfJYZzlKnDV8qr/dD2Ju4V9gPQGzQpfH3MPGzPRUiNCPiUUZiA4AgPpIYsD1mBjd5RpOep4hqXjjB4SvudMPsSUQDusgjU+SDxJQrCGhAoGBAKfz3hdlxSeCnjWl2qQulrV0Ic6kAIqT/cfuNbvDbR5Mij6bywGQ+mWw2Fk0fKfMxM/gEzRiCLmpzPCE+jAQJNXU0dZK9KPnstNmO7/ki6s+/wKI7YJgcAU+M6kGNaBYOO/6QVJ419c/rfGdHVhJk3lpxVBqc73EI32DXwNqdfolAoGAaCG2oOBtJViAPmcU9ZqCMWAU2A2XL3OpYqLiqXunqrGi19oo0Yq19KzeGWQ+IYgE+GCNvYaFE0t9spil9WCPPWCcv7w53R8KM0f3v7eqRTWCNDSXI1pfPJXduonIvqybAiG8nF8yPe7iyGlixY/vznfJqXGq8xCTgFs8ACOWKeE=";

        String data = "hello world";
        PrivateKey privateKey = SecurityUtil.getPrivateKeyFromString(privateKeyString);
        PublicKey publicKey = SecurityUtil.getPublicKeyFromString(publicKeyString);

        String encDt = SecurityUtil.encrypt(data, privateKey);
        System.out.println("Encrypted data: " + encDt);

        String decryptedData = SecurityUtil.decrypt(encDt, publicKey);
        System.out.println("Decrypted data: " + decryptedData);
        System.out.println("Is test valid" + data.equals(decryptedData));
        assert data.equals(decryptedData);
    }

}
