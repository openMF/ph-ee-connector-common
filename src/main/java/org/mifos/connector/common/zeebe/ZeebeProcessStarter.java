package org.mifos.connector.common.zeebe;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import org.mifos.connector.common.util.MpesaUtils;
import org.mifos.connector.common.util.ZeebeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class ZeebeProcessStarter {
    private static Logger logger = LoggerFactory.getLogger(ZeebeProcessStarter.class);

    @Autowired
    private ZeebeClient zeebeClient;

    @Autowired
    private ZeebeUtil zeebeUtil;

    @Value("${transaction-id-length}")
    private int transactionIdLength;


    public String startZeebeWorkflow(String workflowId, String request, Map<String, Object> extraVariables) {
        String transactionId = zeebeUtil.generateTransactionId();

        Map<String, Object> variables = new HashMap<>();
        variables.put(ZeebeVariables.TRANSACTION_ID, transactionId);
        variables.put(ZeebeVariables.CHANNEL_REQUEST, request);
        variables.put(ZeebeVariables.ORIGIN_DATE, Instant.now().toEpochMilli());
        if (extraVariables != null) {
            variables.putAll(extraVariables);
        }

        logger.info("starting workflow HERE:");
        // TODO if successful transfer response arrives in X timeout return it otherwise do callback
        ProcessInstanceEvent instance = zeebeClient.newCreateInstanceCommand()
                .bpmnProcessId(workflowId)
                .latestVersion()
                .variables(variables)
                .send()
                .join();

        logger.info("zeebee workflow instance from process {} started with transactionId {}, instance key: {}", workflowId, transactionId, instance.getProcessInstanceKey());
        return transactionId;
    }

    public String startMpesaZeebeWorkflow(String workflowId, String request, Map<String, Object> extraVariables) {
        String transactionId = zeebeUtil.customSizeTransactionId();

        Map<String, Object> variables = new HashMap<>();
        variables.put(ZeebeVariables.TRANSACTION_ID, transactionId);
        variables.put(ZeebeVariables.MPESA_CHANNEL_REQUEST, request);
        variables.put(ZeebeVariables.CHANNEL_REQUEST, MpesaUtils.mpesaChannelRequestToChannelRequestConvertor(request));
        variables.put(ZeebeVariables.ORIGIN_DATE, Instant.now().toEpochMilli());
        if (extraVariables != null) {
            variables.putAll(extraVariables);
        }

        logger.info("starting workflow HERE:");
        // TODO if successful transfer response arrives in X timeout return it otherwise do callback
        ProcessInstanceEvent instance = zeebeClient.newCreateInstanceCommand()
                .bpmnProcessId(workflowId)
                .latestVersion()
                .variables(variables)
                .send()
                .join();

        logger.info("zeebee workflow instance from process {} started with transactionId {}, instance key: {}", workflowId, transactionId, instance.getProcessInstanceKey());
        return transactionId;
    }

    public String startZeebeWorkflowC2B(String workflowId, Map<String, Object> variables) {

        String transactionId = zeebeUtil.generateTransactionId();
        variables.put(ZeebeVariables.TRANSACTION_ID, transactionId);

        logger.info("starting workflow HERE:");
        ProcessInstanceEvent instance = zeebeClient.newCreateInstanceCommand()
                .bpmnProcessId(workflowId)
                .latestVersion()
                .variables(variables)
                .send()
                .join();

        logger.info("zeebee workflow instance from process {} started with transactionId {}, instance key: {}", workflowId, transactionId, instance.getProcessInstanceKey());
        return transactionId;
    }
}
