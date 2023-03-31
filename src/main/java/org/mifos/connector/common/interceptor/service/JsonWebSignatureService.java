package org.mifos.connector.common.interceptor.service;

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
public class JsonWebSignatureService implements JsonWebSignatureVerifier {

    @Value("${jws.key.public}")
    private String publicKeyString;

    /**
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


    @Override
    public boolean verify(String data, String signature, String publicKey) throws
            NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException,
            BadPaddingException, InvalidKeySpecException, InvalidKeyException {
        String hashedBody = SecurityUtil.hash(data);
        String decodedHash = SecurityUtil.decrypt(signature, publicKeyString);
        return hashedBody.equals(decodedHash);
    }

}
