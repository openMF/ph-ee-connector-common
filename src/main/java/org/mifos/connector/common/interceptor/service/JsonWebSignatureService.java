package org.mifos.connector.common.interceptor.service;

import lombok.Getter;
import org.mifos.connector.common.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Component
@Getter
public class JsonWebSignatureService implements JsonWebSignature {

    @Value("${jws.key.public}")
    private String publicKeyString;

    @Value("${jws.key.private}")
    private String privateKeyString;

    /**
     * Takes data and signature. And verifies if signature is valid or not
     *
     * @param data data for which signature to be verified as a string
     * @param signature signature in string format
     * @return [boolean] true if signature is verified false otherwise
     * @throws NoSuchPaddingException thrown while parsing public key
     * @throws IllegalBlockSizeException thrown while parsing public key
     * @throws NoSuchAlgorithmException thrown while parsing public key
     * @throws BadPaddingException thrown while parsing public key
     * @throws InvalidKeySpecException thrown while parsing public key
     * @throws InvalidKeyException thrown while parsing public key
     */
    public boolean verify(String data, String signature) throws
            NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException,
            BadPaddingException, InvalidKeySpecException, InvalidKeyException {
        return verify(data, signature, publicKeyString);
    }

    /**
     * Takes data and create the corresponding signature
     *
     * @param data the data which is to be signed
     * @return signature for the data passed in form of String
     * @throws NoSuchPaddingException thrown while parsing public key
     * @throws IllegalBlockSizeException thrown while parsing public key
     * @throws NoSuchAlgorithmException thrown while parsing public key
     * @throws BadPaddingException thrown while parsing public key
     * @throws InvalidKeySpecException thrown while parsing public key
     * @throws InvalidKeyException thrown while parsing public key
     */
    public String sign(String data) throws
            NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException,
            BadPaddingException, InvalidKeySpecException, InvalidKeyException {
        return create(data, privateKeyString);
    }


    @Override
    public boolean verify(String data, String signature, String publicKey) throws
            NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException,
            BadPaddingException, InvalidKeySpecException, InvalidKeyException {
        String hashedBody = SecurityUtil.hash(data);
        String decodedHash = SecurityUtil.decryptUsingPublicKey(signature, publicKeyString);
        return hashedBody.equals(decodedHash);
    }

    @Override
    public String create(String data, String privateKey) throws
            NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException,
            BadPaddingException, InvalidKeySpecException, InvalidKeyException {
        String hashedBody = SecurityUtil.hash(data);
        return SecurityUtil.encryptUsingPrivateKey(hashedBody, privateKey);
    }

}
