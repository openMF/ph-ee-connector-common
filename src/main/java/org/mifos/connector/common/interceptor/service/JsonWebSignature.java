package org.mifos.connector.common.interceptor.service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public interface JsonWebSignature {

    boolean verify(String data, String signature, String publicKey) throws NoSuchPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, BadPaddingException, InvalidKeySpecException, InvalidKeyException;

    String create(String data, String privateKey) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException,
            BadPaddingException, InvalidKeySpecException, InvalidKeyException;
}
