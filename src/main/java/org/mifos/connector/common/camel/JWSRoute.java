package org.mifos.connector.common.camel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnExpression("${security.jws.enable:false}")
public class JWSRoute extends ErrorHandlerRouteBuilder {

    @Autowired
    private JWSProcessor jwsProcessor;

    @Override
    public void configure() {
        from("direct:set-jws-signature")
                .id("set-jws-signature")
                .process(jwsProcessor);
    }

}
