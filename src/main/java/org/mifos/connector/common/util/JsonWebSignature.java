package org.mifos.connector.common.util;

import lombok.Getter;
import org.springframework.util.StringUtils;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.UUID;

/**
 * This class represents a JSON Web Signature (JWS) object. It contains a hashed data field and a method to generate a
 * signature using a private key.
 *
 * Usage example:
 *
 * JsonWebSignatureBuilder jwsBuilder = new JsonWebSignature.JsonWebSignatureBuilder();
 * jwsBuilder.setClientCorrelationId(clientCorrelationId)
 * jwsBuilder.setTenantId(tenantId)
 * jwsBuilder.setData(absoluteFilePath);
 *
 * JsonWebSignature jwsSignature = jwsBuilder.build();
 *
 * String signature = jwsSignature.getSignature(privateKey);
 *
 */
@Getter
public class JsonWebSignature {

    /**
     * The hashed data used in the JWS object.
     */
    private String data;

    /**
     * Generates a signature using a private key.
     *
     * @param privateKey the private key used to generate the signature
     * @return the generated signature
     * @throws NoSuchPaddingException if the padding algorithm is not available
     * @throws IllegalBlockSizeException if the block size is not valid for this encryption algorithm
     * @throws NoSuchAlgorithmException if the encryption algorithm is not available
     * @throws BadPaddingException if the padding is invalid
     * @throws InvalidKeySpecException if the key specification is invalid
     * @throws InvalidKeyException if the key is invalid
     */
    public String getSignature(String privateKey) throws NoSuchPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, BadPaddingException, InvalidKeySpecException, InvalidKeyException {
        return SecurityUtil.encryptUsingPrivateKey(this.data, privateKey);
    }

    /**
     * This class provides a builder for creating a JSON Web Signature (JWS) object.
     */
    public static class JsonWebSignatureBuilder {

        /**
         * The batch ID used in the JWS object.
         */
        private String batchId;
        /**
         * The client correlation ID used in the JWS object.
         */
        private String clientCorrelationId;
        /**
         * The tenant ID used in the JWS object.
         */
        private String tenantId;
        /**
         * The data used in the JWS object.
         */
        private String data;
        /**
         * A flag indicating whether the data is a file.
         */
        private boolean isDataAFile = false;
        /**
         * The JWS data separator used to separate the JWS header and payload.
         */
        protected static String jwsDataSeparator = ":";

        /**
         * Sets the batch ID for the JWS object.
         *
         * @param batchId The batch ID to set.
         * @return This builder object.
         */
        public JsonWebSignatureBuilder setBatchId(String batchId) {
            this.batchId = batchId;
            return this;
        }

        /**
         * Sets the client correlation ID for the JWS object.
         *
         * @param clientCorrelationId The client correlation ID to set.
         * @return This builder object.
         */
        public JsonWebSignatureBuilder setClientCorrelationId(String clientCorrelationId) {
            this.clientCorrelationId = clientCorrelationId;
            return this;
        }

        /**
         * Sets the tenant ID for the JWS object.
         *
         * @param tenantId The tenant ID to set.
         * @return This builder object.
         */
        public JsonWebSignatureBuilder setTenantId(String tenantId) {
            this.tenantId = tenantId;
            return this;
        }

        /**
         * Sets the data/filepath for the JWS object.
         * In case of file, make sure to pass the absolute file path
         *
         * @param data The data to set.
         * @return This builder object.
         */
        public JsonWebSignatureBuilder setData(String data) {
            this.data = data;
            return this;
        }

        /**
         * Sets whether the data is a file or not.
         *
         * @param isDataAFile Whether the data is a file or not.
         * @return This builder object.
         */
        public JsonWebSignatureBuilder setIsDataAsFile(boolean isDataAFile) {
            this.isDataAFile = isDataAFile;
            return this;
        }

        /**
         * Builds the JWS object using the provided parameters.
         *
         * @return The JWS object.
         * @throws IOException If an error occurs while reading the file data.
         */
        public JsonWebSignature build() throws IOException {
            JsonWebSignature jsonWebSignature = new JsonWebSignature();
            jsonWebSignature.data = SecurityUtil.hash(getDataToBeHashed());
            return jsonWebSignature;
        }

        /**
         * Gets the data to be hashed for the JWS object.
         *
         * @return The data to be hashed.
         * @throws IOException If an error occurs while reading the file data.
         */
        private String getDataToBeHashed() throws IOException {
            StringBuilder jwsDataToBeHashedBuilder = new StringBuilder();

            if (!StringUtils.hasText(this.batchId) && !StringUtils.hasText(this.clientCorrelationId) &&
                    !StringUtils.hasText(this.tenantId) && !StringUtils.hasText(this.data)) {
                throw new RuntimeException("Signature must contain at least one identifier among " +
                        "[batchId, clientCorrelationId, tenantId, data/filepath]");
            }

            if (StringUtils.hasText(this.batchId)) {
                jwsDataToBeHashedBuilder.append(this.batchId).append(JsonWebSignatureBuilder.jwsDataSeparator);
            }

            if (StringUtils.hasText(this.clientCorrelationId)) {
                jwsDataToBeHashedBuilder.append(this.clientCorrelationId).append(JsonWebSignatureBuilder.jwsDataSeparator);
            }

            if (StringUtils.hasText(this.tenantId)) {
                jwsDataToBeHashedBuilder.append(this.tenantId).append(JsonWebSignatureBuilder.jwsDataSeparator);
            }

            if (StringUtils.hasText(this.data)) {
                if (isDataAFile) {
                    String fileContent = Files.readString(Paths.get(data));
                    jwsDataToBeHashedBuilder.append(fileContent).append(JsonWebSignatureBuilder.jwsDataSeparator);
                } else {
                    jwsDataToBeHashedBuilder.append(data).append(JsonWebSignatureBuilder.jwsDataSeparator);
                }
            }

            // remove the last "separator" and return the string
            return jwsDataToBeHashedBuilder.substring(0, jwsDataToBeHashedBuilder.length()-1);
        }
    }
}
