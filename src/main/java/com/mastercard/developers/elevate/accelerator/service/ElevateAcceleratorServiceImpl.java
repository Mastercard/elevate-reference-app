package com.mastercard.developers.elevate.accelerator.service;

import com.google.gson.Gson;
import com.mastercard.developers.elevate.accelerator.generated.apis.ElevateApi;
import com.mastercard.developers.elevate.accelerator.generated.invokers.ApiException;
import com.mastercard.developers.elevate.accelerator.generated.models.CardToken;
import com.mastercard.developers.elevate.accelerator.generated.models.CardTokenInfo;
import com.mastercard.developers.elevate.accelerator.generated.models.CheckEligibility;
import com.mastercard.developers.elevate.accelerator.generated.models.Eligibility;
import com.mastercard.developers.elevate.accelerator.generated.models.PartnerBenefitDetails;
import com.mastercard.developers.elevate.accelerator.generated.models.RedemptionInfo;
import com.mastercard.developers.elevate.accelerator.generated.models.Redemptions;
import com.mastercard.developers.elevate.accelerator.helper.RequestHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/*
 * This class is used to call elevate APIs
 * ElevateApi class is used to encrypt request payload
 */
@Slf4j
@Service
public class ElevateAcceleratorServiceImpl {

    private static final String REQUEST_API = "\nRequest API: ";

    private static final String RESPONSE = "\nRESPONSE : ";

    @Value("${mastercard.elevate.client.api.base.path}")
    private String baseUrl;

    ElevateApi elevateApi = new ElevateApi(RequestHelper.signRequest());

    public Eligibility checkEligibility(CheckEligibility checkEligibility) throws ApiException {
            String requestPayload = new Gson().toJson(checkEligibility);
            String request = REQUEST_API + baseUrl + "/eligibilities\n" +
                    "Source: checkEligibility \nRequest Payload : " + requestPayload;
            log.info(request);
            Eligibility eligibility = elevateApi.checkEligibility(checkEligibility);
            log.info(RESPONSE + eligibility.toString());
            return eligibility;

    }

    public RedemptionInfo redemption(Redemptions redemptions) throws ApiException {
            String requestPayload = new Gson().toJson(redemptions);
            String request = REQUEST_API + baseUrl + "/redemptions\n" +
                    "Source: redemption \nRequest Payload : " + requestPayload;
            log.info(request);
            RedemptionInfo redemptionInfo = elevateApi.createRedemption(redemptions);
            log.info(RESPONSE + redemptionInfo.toString());
            return redemptionInfo;
    }

    public PartnerBenefitDetails benefits() throws ApiException {
            String request = REQUEST_API + baseUrl + "/benefits\n" +
                    "Source: benefits \n ";
            log.info(request);
            PartnerBenefitDetails partnerBenefitDetails = elevateApi.getBenefits();
            log.info(RESPONSE + partnerBenefitDetails.toString());
            return  partnerBenefitDetails;
    }

    public CardTokenInfo saveToken(CardToken cardToken) throws ApiException {
        String requestPayload = new Gson().toJson(cardToken);
        String request = REQUEST_API + baseUrl + "/saveToken\n" +
                "Source: saveToken \nRequest Payload : " + requestPayload;
        log.info(request);
        CardTokenInfo tokenInfo = elevateApi.saveToken(cardToken);
        log.info(RESPONSE + tokenInfo.toString());
        return tokenInfo;
    }
}
