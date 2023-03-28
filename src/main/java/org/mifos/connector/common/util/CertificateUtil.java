package org.mifos.connector.common.util;

import org.apache.commons.codec.binary.Base64;
import sun.security.x509.X509CertImpl;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class CertificateUtil {

    /**
     * Takes the base64 encoded string of certificate and returns the instance of the X509Certificate class
     *
     * @param encodedCertificate the base64 encoded string of certificate
     * @return [X509Certificate] the instance of X509Certificate class
     */
    public static X509Certificate parseX509Certificate(String encodedCertificate) throws CertificateException {
        byte[] certificateBytes = Base64.decodeBase64(encodedCertificate);
        return new X509CertImpl(certificateBytes);
    }

    /**
     * Takes the X509Certificate certificate object and extracts the public key from it
     *
     * @param certificate the instance of X509Certificate
     * @return [PublicKey] the instance of PublicKey class
     */
    public static PublicKey parseRSAPublicKey(X509Certificate certificate) {
        return certificate.getPublicKey();
    }

}
