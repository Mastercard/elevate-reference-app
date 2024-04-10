package com.mastercard.developers.mbep.service;

import com.google.gson.Gson;


import com.mastercard.developers.mbep.generated.apis.MbepApi;
import com.mastercard.developers.mbep.generated.invokers.ApiException;
import com.mastercard.developers.mbep.generated.models.*;
import com.mastercard.developers.mbep.helper.RequestHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    public Eligibility checkEligibility(CheckEligibility checkEligibility) throws ApiException {
        String requestPayload = new Gson().toJson(checkEligibility);
        String request = REQUEST_API + baseUrl + "/eligibilities\n" +
                "Source: checkEligibility \nRequest Payload : " + requestPayload;
        log.info(request);
        Eligibility eligibility = mbepApi.checkEligibility(checkEligibility);
        log.info(RESPONSE + eligibility.toString());
        return eligibility;

    }

    public RedemptionInfo redemption(Redemptions redemptions) throws ApiException {
        String requestPayload = new Gson().toJson(redemptions);
        String request = REQUEST_API + baseUrl + "/redemptions\n" +
                "Source: redemption \nRequest Payload : " + requestPayload;
        log.info(request);
        RedemptionInfo redemptionInfo = mbepApi.createRedemption(redemptions);
        log.info(RESPONSE + redemptionInfo.toString());
        return redemptionInfo;
    }

    public PartnerBenefitDetails benefits() throws ApiException {
        String request = REQUEST_API + baseUrl + "/benefits\n" +
                "Source: benefits \n ";
        log.info(request);
        PartnerBenefitDetails partnerBenefitDetails = mbepApi.getBenefits();
        log.info(RESPONSE + partnerBenefitDetails.toString());
        return  partnerBenefitDetails;
    }

    public CardTokenInfo saveToken(CardToken cardToken) throws ApiException {
        String requestPayload = new Gson().toJson(cardToken);
        String request = REQUEST_API + baseUrl + "/saveToken\n" +
                "Source: saveToken \nRequest Payload : " + requestPayload;
        log.info(request);
        CardTokenInfo tokenInfo = mbepApi.saveToken(cardToken);
        log.info(RESPONSE + tokenInfo.toString());
        return tokenInfo;
    }
}
