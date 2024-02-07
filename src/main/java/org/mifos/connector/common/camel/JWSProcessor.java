package org.mifos.connector.common.camel;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.mifos.connector.common.interceptor.JWSUtil;
import org.mifos.connector.common.interceptor.WebSignatureInterceptor;
import org.mifos.connector.common.interceptor.service.JsonWebSignatureService;
import org.mifos.connector.common.util.Constant;
import org.mifos.connector.common.zeebe.ZeebeVariables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
@ConditionalOnExpression("${security.jws.enable:false}")
public class JWSProcessor implements Processor {

    @Autowired
    WebSignatureInterceptor webSignatureInterceptor;

    @Autowired
    private JsonWebSignatureService jsonWebSignatureService;

    @Value("#{'${jws.header.order}'.split(',')}")
    private List<String> headerOrder;

    @Override
    public void process(Exchange exchange) throws Exception {
        String body = exchange.getIn().getBody(String.class);
        Map<String, Object> headers = exchange.getIn().getHeaders();
        String signatureData = JWSUtil.getDataToBeHashed(headers, body, headerOrder);
        log.debug("Signature data: {}", signatureData);
        String tenant = exchange.getProperty(ZeebeVariables.TENANT_ID, String.class);
        String signature = jsonWebSignatureService.signForTenant(signatureData, tenant);
        exchange.getIn().setHeader(Constant.HEADER_JWS, signature);
    }
}
