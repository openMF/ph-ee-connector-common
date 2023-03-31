package org.mifos.connector.common.interceptor.service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface JsonWebSignatureVerifier {

    boolean verify(String data, String signature, String publicKey) throws NoSuchPaddingException,
            IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException,
            InvalidKeySpecException, InvalidKeyException;

}
