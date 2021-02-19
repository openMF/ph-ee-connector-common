package org.mifos.mojaloop;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Spliterator;
import java.util.UUID;
import java.util.function.Consumer;

import static java.util.Spliterators.spliteratorUnknownSize;
import static java.util.stream.StreamSupport.stream;

/**
 * !!!READ COMMENTS!!!
 * Compatible with Mojaloop version: 11.0.0
 */
@SpringBootApplication
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class OnboardDfsps {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${mojaloop.local}")
    private boolean isLocalMojaloop;

    @Value("${local.central-ledger-host}")
    private String localCentralLedger;

    @Value("${onboarding.enabled:false}")
    private boolean onboardingEnabled;

    @Value("${oracle.host}")
    private String oracleHost;

    @Value("${oracle.endpoint}")
    private String oraclePath;

    @Value("${mojaloop.host}")
    private String mojaHost;

    @Value("${mojaloop.currency}")
    private String mojaCurrency;

    @Value("${mojaloop.contact-email}")
    private String mojaConectacEmail;

    @Value("${mojaloop.central-ledger-service}")
    private String mojaCentralLedgerService;

    @Value("${mojaloop.account-lookup-service}")
    private String mojaAccountLookupService;

    @Value("${mojaloop.do-hub-onboard:true}")
    private boolean doHubOnboard;

    @Value("${mojaloop.do-oracle-onboard:true}")
    private boolean doOracleOnboard;

    @Value("${mojaloop.do-dfsp-onboard:true}")
    private boolean doDfspOnboard;

    @Value("${mojaloop.participants.hub.endpoints}")
    private String participantsHubEndpointsPath;

    @Value("${mojaloop.participants.hub.accounts}")
    private String participantsHubAccountsPath;

    @Value("${mojaloop.oracles.endpoint}")
    private String oraclesPath;

    @Value("${mojaloop.oracles.type}")
    private String oraclesType;

    @Value("${mojaloop.participants.endpoint}")
    private String participantsPath;

    @Value("${mojaloop.participants.position-and-limit-endpoint}")
    private String participantsPositionAndLimitPath;

    @Value("${mojaloop.participants.registration-endpoint}")
    private String participantsRegistrationPath;

    @Value("${mojaloop.participants.accounts-endpoint}")
    private String participantsAccountsPath;

    @Autowired
    private DfspProperties dfspProperties;

    @Autowired
    private CallbackMappingProperties callbackMappingProperties;

    @Autowired
    private RestTemplate template;

    public OnboardDfsps() {
        System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
    }

    @Test
    public void onboard() {
        /**
         * Check also if you want to execute against a local installment or remote with peroperty: {mojaloop.local}
         */
        if(!onboardingEnabled) {
            logger.info("Onboarding turned off");
            return;
        }

        /*
         * these should be only run once / Mojaloop deployment, turn off manually after first run
         */
        if (doHubOnboard) {
            addHubAccountReconciliation();
            addHubAccountMultilateralSettlement();
            setHubEndpointSettlementTransferPositionChangeEmail();
            setHubEndpointNetDebitCapAdjustmentEmail();
            setHubEndpointNetDebitCapThresholdBreachEmail();
        }

        /*
         * external oracle system called by Mojaloop to lookup participants, not the same as oracles in Mojaloop context
         * this should be only run once / Mojaloop deployment, turn off manually after first run
         */
        if (doOracleOnboard) {
            createOracle();
        }

        /*
         * every dfsp can be added separately, turn off selected ones from property
         */
        if (doDfspOnboard) {
            for (Dfsp dfsp : dfspProperties.getDfsps()) {
                logger.info("Dfsp {} enabled: {}", dfsp.getId(), dfsp.isEnabled());
                if (dfsp.isEnabled()) {
                    logger.info("Onboarding dfsp: {}", dfsp.getId());
                    if(dfsp.isRegisterOnlyCallbackUrls()) {
                        logger.info("Updating only dfsp callback urls: {}", dfsp.getId());
                        updateCallbackUrls(dfsp);
                        continue;
                    }
                    String response = addDfsp(dfsp);
                    int settlementAccountId = ((JSONObject) stream(spliteratorUnknownSize(new JSONObject(response).getJSONArray("accounts").iterator(), Spliterator.ORDERED), false)
                            .filter(a -> "SETTLEMENT".equals(((JSONObject) a).getString("ledgerAccountType")))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("No SETTLEMENT account found!"))).getInt("id");
                    addInitialPositionAndLimit(dfsp);
                    updateCallbackUrls(dfsp);
                    recordFundsInDfsp(dfsp, settlementAccountId);
                    /**
                     * if participants were already added to the external oracle before -> this part should be skipped
                     * this part is not dependant to mojaloop installment
                     */
                    if(dfsp.isAddToExternalOracle()) {
                        addParticipantToDfsp(dfsp); // TODO multiple participants in single dfsp
                    }
                }
            }
        }
    }

    private void updateCallbackUrls(Dfsp dfsp) {
        for (CallbackMapping mapping : callbackMappingProperties.getCallbackMappings()) {
            addCallbackUrl(dfsp, mapping);
        }
    }

    private void addHubAccountReconciliation() {
        JSONObject body = new JSONObject();
        String hub = "HUB_RECONCILIATION";
        body.put("type", hub);
        body.put("currency", mojaCurrency);

        rest((isLocalMojaloop ? localCentralLedger : mojaHost) + participantsHubAccountsPath, mojaCentralLedgerService, body.toString(), h -> {
        });
        logger.info("Hub '{}' added", hub);
    }

    private void addHubAccountMultilateralSettlement() {
        JSONObject body = new JSONObject();
        String hub = "HUB_MULTILATERAL_SETTLEMENT";
        body.put("type", hub);
        body.put("currency", mojaCurrency);

        rest((isLocalMojaloop ? localCentralLedger : mojaHost) + participantsHubAccountsPath, mojaCentralLedgerService, body.toString(), h -> {
        });
        logger.info("Hub '{}' added", hub);
    }

    private void setHubEndpointSettlementTransferPositionChangeEmail() {
        JSONObject body = new JSONObject();
        String email = "SETTLEMENT_TRANSFER_POSITION_CHANGE_EMAIL";
        body.put("type", email);
        body.put("value", mojaConectacEmail);

        rest((isLocalMojaloop ? localCentralLedger : mojaHost) + participantsHubEndpointsPath, mojaCentralLedgerService, body.toString(), h -> {
        });
        logger.info("Email '{}' added", email);
    }

    private void setHubEndpointNetDebitCapAdjustmentEmail() {
        JSONObject body = new JSONObject();
        String email = "NET_DEBIT_CAP_ADJUSTMENT_EMAIL";
        body.put("type", email);
        body.put("value", mojaConectacEmail);

        rest((isLocalMojaloop ? localCentralLedger : mojaHost) + participantsHubEndpointsPath, mojaCentralLedgerService, body.toString(), h -> {
        });
        logger.info("Email '{}' added", email);
    }

    private void setHubEndpointNetDebitCapThresholdBreachEmail() {
        JSONObject body = new JSONObject();
        String email = "NET_DEBIT_CAP_THRESHOLD_BREACH_EMAIL";
        body.put("type", email);
        body.put("value", mojaConectacEmail);

        rest((isLocalMojaloop ? localCentralLedger : mojaHost) + participantsHubEndpointsPath, mojaCentralLedgerService, body.toString(), h -> {
        });
        logger.info("Email '{}' added", email);
    }

    private void createOracle() {
        JSONObject body = new JSONObject();
        body.put("oracleIdType", oraclesType);
        body.put("currency", mojaCurrency);
        body.put("isDefault", true);
        JSONObject endpoint = new JSONObject();
        endpoint.put("value", oracleHost + "/oracle");
        endpoint.put("endpointType", "URL");
        body.put("endpoint", endpoint);

        rest(mojaHost + oraclesPath, mojaAccountLookupService, body.toString(), h -> h.add("Date", "2019-09-20 08:52:19"));
        logger.info("Oracle type '{}' with currency '{}' added", oraclesType, mojaCurrency);
    }

    private String addDfsp(Dfsp dfsp) {
        JSONObject body = new JSONObject();
        body.put("name", dfsp.getId());
        body.put("currency", mojaCurrency);

        String response = rest((isLocalMojaloop ? localCentralLedger : mojaHost) + participantsPath, mojaCentralLedgerService, body.toString(), h -> {
        });
        logger.info("Dfsp added: {}", dfsp.getId());
        return response;
    }

    private void addInitialPositionAndLimit(Dfsp dfsp) {
        JSONObject body = new JSONObject();
        body.put("initialPosition", 0);
        body.put("currency", mojaCurrency);
        JSONObject limit = new JSONObject();
        limit.put("type", "NET_DEBIT_CAP");
        limit.put("value", dfsp.getFundsInPrepareAmount());
        body.put("limit", limit);

        rest((isLocalMojaloop ? localCentralLedger : mojaHost) + participantsPositionAndLimitPath.replace("{dfspid}", dfsp.getId()), mojaCentralLedgerService, body.toString(), h -> {
        });
        logger.info("Dfsp limits and position added");
    }

    private void addCallbackUrl(Dfsp dfsp, CallbackMapping mapping) {
        JSONObject body = new JSONObject();
        body.put("type", mapping.getType());
        String registeredValue = mapping.getType().contains("EMAIL") ? mapping.getValue().replace("{dfspDomain}", mojaConectacEmail) :
                mapping.getValue().replace("{dfspDomain}", dfsp.getDomain());
        body.put("value", registeredValue);

        rest((isLocalMojaloop ? localCentralLedger : mojaHost) + participantsRegistrationPath.replace("{dfspid}", dfsp.getId()), mojaCentralLedgerService, body.toString(), h -> {
        });
        logger.info("Registration success type: {} value: {}", mapping.getType(), registeredValue);
    }

    private void recordFundsInDfsp(Dfsp dfsp, int settlementAccountId) {
        JSONObject body = new JSONObject();
        body.put("transferId", UUID.randomUUID().toString());
        body.put("externalReference", "string");
        body.put("action", "recordFundsIn");
        body.put("reason", "string");
        JSONObject amount = new JSONObject();
        amount.put("amount", Integer.parseInt(dfsp.getFundsInPrepareAmount()));
        amount.put("currency", mojaCurrency);
        body.put("amount", amount);
        JSONObject extensionList = new JSONObject();
        JSONArray extensionArray = new JSONArray();
        JSONObject extension = new JSONObject();
        extension.put("key", "string");
        extension.put("value", "string");
        extensionArray.put(extension);
        extensionList.put("extension", extensionArray);
        body.put("extensionList", extensionList);

        rest((isLocalMojaloop ? localCentralLedger : mojaHost) + participantsAccountsPath.replace("{dfspid}", dfsp.getId()).replace("{settlementAccountId}", String.valueOf(settlementAccountId)),
                mojaCentralLedgerService,
                body.toString(), h -> {
                });
        logger.info("Dfsp fund recorded: {} on settlement account: {}", Integer.parseInt(dfsp.getFundsInPrepareAmount()), settlementAccountId);
    }

    private void addParticipantToDfsp(Dfsp dfsp) {
        JSONObject body = new JSONObject();
        body.put("fspId", dfsp.getId());
        body.put("currency", mojaCurrency);

        rest(oracleHost + oraclePath.replace("{partyIdType}", dfsp.getPartyIdType()).replace("{partyIdentifier}", dfsp.getPartyIdentifier()),
                null,
                body.toString(), h -> h.add("Date", "2019-09-20 08:52:19"));
        logger.info("Client with type {} value {} added to {}", dfsp.getPartyIdType(), dfsp.getPartyIdentifier(), dfsp.getId());
    }

    private String rest(String url, String service, String body, Consumer<HttpHeaders> extraHeaders) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (service != null) {
            headers.add("Host", service);
        }
        extraHeaders.accept(headers);

        try {
            ResponseEntity<String> exchange = template.exchange(url, HttpMethod.POST, new HttpEntity<>(body, headers), String.class);
            if (exchange.getStatusCodeValue() < 200 || exchange.getStatusCodeValue() > 299) {
                logger.error("Invalid code {} response {}", exchange.getStatusCodeValue(), exchange.getBody());
                throw new RuntimeException("Invalid response!");
            } else {
                logger.info("Succes: {} status: {}", exchange.getBody(), exchange.getStatusCode());
                return exchange.getBody();
            }
        } catch (Exception ex) {
            logger.error("Error!", ex);
            throw new RuntimeException("Invalid response!");
        }
    }
}