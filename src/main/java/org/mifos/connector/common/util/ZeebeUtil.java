package org.mifos.connector.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ZeebeUtil {

    @Value("${transaction-id-length}")
    private int transactionIdLength;

    public String generateTransactionId() {
        return UUID.randomUUID().toString();
    }

    public String randomCharOfSize(int size) {
        String data = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        char[] arr = data.toCharArray();
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < size; i++) {
            int index = (int) (Math.random() * (data.length()));
            s.append(arr[index]);
        }
        return s.toString();
    }

    public String customSizeTransactionId() {
        String transactionId = generateTransactionId();
        if(transactionIdLength == -1 || transactionIdLength < 13) {
            return transactionId;
        }
        String originalUUID = transactionId.replace("-","");
        String uuid12digits = originalUUID.substring(0, 12);
        String randomString = randomCharOfSize(transactionIdLength - 12);
        return uuid12digits + randomString;

    }
}
