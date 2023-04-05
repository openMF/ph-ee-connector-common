package org.mifos.connector.common.interceptor.service;

import lombok.Getter;
import org.mifos.connector.common.interceptor.properties.TenantKeysProperties;
import org.mifos.connector.common.util.CertificateUtil;
import org.mifos.connector.common.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;

@Component
@Getter
public class JsonWebSignatureService implements JsonWebSignature {

    @Autowired
    private TenantKeysProperties tenantKeysProperties;

    /**
     * Takes data,signature and tenantName. And verifies if signature is valid or not.
     * TenantName is used to fetch respective keys
     *
     * @param data data for which signature to be verified as a string
     * @param signature signature in string format
     * @param tenantName name of the tenant
     * @return [boolean] true if signature is verified false otherwise
     * @throws NoSuchPaddingException thrown while parsing public key
     * @throws IllegalBlockSizeException thrown while parsing public key
     * @throws NoSuchAlgorithmException thrown while parsing public key
     * @throws BadPaddingException thrown while parsing public key
     * @throws InvalidKeySpecException thrown while parsing public key
     * @throws InvalidKeyException thrown while parsing public key
     */
    public boolean verifyForTenant(String data, String signature, String tenantName) throws
            NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException,
            BadPaddingException, InvalidKeySpecException, InvalidKeyException, CertificateException {
        String certificate = tenantKeysProperties.getCertificate(tenantName);
        String publicKeyString = CertificateUtil.getPublicKey(certificate);
        return verify(data, signature, publicKeyString);
    }

    /**
     * Takes data and create the corresponding signature.
     * TenantName is used to fetch respective keys
     *
     * @param data the data which is to be signed
     * @param tenantName name of the tenant
     * @return signature for the data passed in form of String
     * @throws NoSuchPaddingException thrown while parsing public key
     * @throws IllegalBlockSizeException thrown while parsing public key
     * @throws NoSuchAlgorithmException thrown while parsing public key
     * @throws BadPaddingException thrown while parsing public key
     * @throws InvalidKeySpecException thrown while parsing public key
     * @throws InvalidKeyException thrown while parsing public key
     */
    public String signForTenant(String data, String tenantName) throws
            NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException,
            BadPaddingException, InvalidKeySpecException, InvalidKeyException {
        String privateKey = tenantKeysProperties.getPrivateKey(tenantName);
        return create(data, privateKey);
    }


    @Override
    public boolean verify(String data, String signature, String publicKey) throws
            NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException,
            BadPaddingException, InvalidKeySpecException, InvalidKeyException {
        String hashedBody = SecurityUtil.hash(data);
        String decodedHash = SecurityUtil.decryptUsingPublicKey(signature, publicKey);
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
