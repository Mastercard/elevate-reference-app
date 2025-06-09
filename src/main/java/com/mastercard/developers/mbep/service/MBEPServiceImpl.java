package com.mastercard.developers.mbep.service;

import com.google.gson.Gson;
import com.mastercard.developers.mbep.generated.apis.MbepApi;
import com.mastercard.developers.mbep.generated.invokers.ApiException;
import com.mastercard.developers.mbep.generated.models.AccrueRedemptionBySpend;
import com.mastercard.developers.mbep.generated.models.AccrueRedemptionByTransaction;
import com.mastercard.developers.mbep.generated.models.CardToken;
import com.mastercard.developers.mbep.generated.models.CardTokenInfo;
import com.mastercard.developers.mbep.generated.models.CheckEligibility;
import com.mastercard.developers.mbep.generated.models.CheckEligibilityByPan;
import com.mastercard.developers.mbep.generated.models.CheckEligibilityByToken;
import com.mastercard.developers.mbep.generated.models.Eligibility;
import com.mastercard.developers.mbep.generated.models.PartnerBenefitDetails;
import com.mastercard.developers.mbep.generated.models.RedemptionByRealTime;
import com.mastercard.developers.mbep.generated.models.RedemptionInfo;
import com.mastercard.developers.mbep.generated.models.Redemptions;
import com.mastercard.developers.mbep.helper.RequestHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/*
    [GEN-AI-ASSISTED]
 */
/*
 * This class is used to call mbep APIs
 * MBEPApi class is used to encrypt request payload
 */
@Slf4j
@Service
public class MBEPServiceImpl {

    private static final String REQUEST_API = "\nRequest API: ";

    private static final String RESPONSE = "\nRESPONSE : ";

    @Value("${mastercard.mbep.client.api.base.path}")
    private String baseUrl;

    MbepApi mbepApi = new MbepApi(RequestHelper.signRequest());

    /**
     * Checks eligibility.
     *
     * @param checkEligibility the check eligibility request payload
     * @return the eligibility response
     * @throws ApiException if an error occurs while calling the API
     */
    public Eligibility checkEligibility(CheckEligibility checkEligibility) throws ApiException {
        String requestPayload = new Gson().toJson(checkEligibility);
        String request = REQUEST_API + baseUrl + "/eligibilities\n" +
                "Source: checkEligibility \nRequest Payload : " + requestPayload;
        log.info(request);
        Eligibility eligibility = mbepApi.checkEligibility(checkEligibility);
        log.info(RESPONSE + eligibility.toString());
        return eligibility;

    }

    /**
     * Checks eligibility by token.
     *
     * @param checkEligibilityByToken the check eligibility by token request payload
     * @return the eligibility response
     * @throws ApiException if an error occurs while calling the API
     */
    public Eligibility checkEligibilityByToken(CheckEligibilityByToken checkEligibilityByToken) throws ApiException {
        String requestPayload = new Gson().toJson(checkEligibilityByToken);
        String request = REQUEST_API + baseUrl + "/eligibilities/token\n" +
                "Source: checkEligibility by Token \nRequest Payload : " + requestPayload;
        log.info(request);
        Eligibility eligibility = mbepApi.checkEligibilityByToken(checkEligibilityByToken);
        log.info(RESPONSE + eligibility.toString());
        return eligibility;

    }

    /**
     * Creates a redemption.
     *
     * @param redemptions the redemptions request payload
     * @return the redemption info response
     * @throws ApiException if an error occurs while calling the API
     */
    public RedemptionInfo redemption(Redemptions redemptions) throws ApiException {
        String requestPayload = new Gson().toJson(redemptions);
        String request = REQUEST_API + baseUrl + "/redemptions\n" +
                "Source: redemption \nRequest Payload : " + requestPayload;
        log.info(request);
        RedemptionInfo redemptionInfo = mbepApi.createRedemption(redemptions);
        log.info(RESPONSE + redemptionInfo.toString());
        return redemptionInfo;
    }

    /**
     * Creates a real-time redemption.
     *
     * @param redemptionByRealTime the redemption by real-time request payload
     * @return the redemption info response
     * @throws ApiException if an error occurs while calling the API
     */
    public RedemptionInfo createRedemptionByRealTime(RedemptionByRealTime redemptionByRealTime) throws ApiException {
        String requestPayload = new Gson().toJson(redemptionByRealTime);
        String request = REQUEST_API + baseUrl + "/redemptions/real-time\n" +
                "Source: real time redemption \nRequest Payload : " + requestPayload;
        log.info(request);
        RedemptionInfo redemptionInfo = mbepApi.createRedemptionByRealTime(redemptionByRealTime);
        log.info(RESPONSE + redemptionInfo.toString());
        return redemptionInfo;
    }

    /**
     * Creates a redemption by accrued transaction.
     *
     * @param accrueRedemptionByTransaction the accrue redemption by transaction request payload
     * @return the redemption info response
     * @throws ApiException if an error occurs while calling the API
     */
    public RedemptionInfo createRedemptionByAccrueTransaction(AccrueRedemptionByTransaction accrueRedemptionByTransaction) throws ApiException {
        String requestPayload = new Gson().toJson(accrueRedemptionByTransaction);
        String request = REQUEST_API + baseUrl + "/redemptions/accrued/transactions\n" +
                "Source: accrued redemption with transaction \nRequest Payload : " + requestPayload;
        log.info(request);
        RedemptionInfo redemptionInfo = mbepApi.createRedemptionByAccrueTransactions(accrueRedemptionByTransaction);
        log.info(RESPONSE + redemptionInfo.toString());
        return redemptionInfo;
    }

    /**
     * Creates a redemption by accrued spend.
     *
     * @param accrueRedemptionBySpend the accrue redemption by spend request payload
     * @return the redemption info response
     * @throws ApiException if an error occurs while calling the API
     */
    public RedemptionInfo createRedemptionByAccrueSpend(AccrueRedemptionBySpend accrueRedemptionBySpend) throws ApiException {
        String requestPayload = new Gson().toJson(accrueRedemptionBySpend);
        String request = REQUEST_API + baseUrl + "/redemptions/accrued/spend\n" +
                "Source: accrued redemption with spend \nRequest Payload : " + requestPayload;
        log.info(request);
        RedemptionInfo redemptionInfo = mbepApi.createRedemptionByAccrueSpend(accrueRedemptionBySpend);
        log.info(RESPONSE + redemptionInfo.toString());
        return redemptionInfo;
    }

    /**
     * Retrieves partner benefit details.
     *
     * @return the partner benefit details response
     * @throws ApiException if an error occurs while calling the API
     */
    public PartnerBenefitDetails benefits() throws ApiException {
        String request = REQUEST_API + baseUrl + "/benefits\n" +
                "Source: benefits \n ";
        log.info(request);
        PartnerBenefitDetails partnerBenefitDetails = mbepApi.getBenefits();
        log.info(RESPONSE + partnerBenefitDetails.toString());
        return  partnerBenefitDetails;
    }

    /**
     * Saves a card token.
     *
     * @param cardToken the card token request payload
     * @return the card token info response
     * @throws ApiException if an error occurs while calling the API
     */
    public CardTokenInfo saveToken(CardToken cardToken) throws ApiException {
        String requestPayload = new Gson().toJson(cardToken);
        String request = REQUEST_API + baseUrl + "/saveToken\n" +
                "Source: saveToken \nRequest Payload : " + requestPayload;
        log.info(request);
        CardTokenInfo tokenInfo = mbepApi.saveToken(cardToken);
        log.info(RESPONSE + tokenInfo.toString());
        return tokenInfo;
    }

    /**
     * Checks eligibility by PAN.
     *
     * @param checkEligibilityByPan the check eligibility by PAN request payload
     * @return the eligibility response
     * @throws ApiException if an error occurs while calling the API
     */
    public Eligibility checkEligibilityByPan(CheckEligibilityByPan checkEligibilityByPan) throws ApiException {
        String requestPayload = new Gson().toJson(checkEligibilityByPan);
        String request = REQUEST_API + baseUrl + "/eligibilities/pan\n" +
                "Source: checkEligibility by PAN \nRequest Payload : " + requestPayload;
        log.info(request);
        Eligibility eligibility = mbepApi.checkEligibilityByPan(checkEligibilityByPan);
        log.info(RESPONSE + eligibility.toString());
        return eligibility;

    }
}
