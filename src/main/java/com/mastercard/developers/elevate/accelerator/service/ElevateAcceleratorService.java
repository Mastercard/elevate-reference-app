package com.mastercard.developers.elevate.accelerator.service;

import com.google.gson.Gson;
import com.mastercard.developers.elevate.accelerator.generated.apis.ElevateApi;
import com.mastercard.developers.elevate.accelerator.generated.invokers.ApiException;
import com.mastercard.developers.elevate.accelerator.generated.models.CheckEligibility;
import com.mastercard.developers.elevate.accelerator.generated.models.Eligibility;
import com.mastercard.developers.elevate.accelerator.generated.models.RedemptionInfo;
import com.mastercard.developers.elevate.accelerator.generated.models.Redemptions;
import com.mastercard.developers.elevate.accelerator.helper.RequestHelper;

import java.util.logging.Logger;

public class ElevateAcceleratorService {

    private final Logger logger = Logger.getLogger(ElevateAcceleratorService.class.getName());

    private static final String BASE_URL = "mastercard.elevate.client.api.base.path";
    private static final String CHECK_ELIGIBILITY = "checkEligibility";
    private static final String REDEMPTION = "redemption";
    private static final String CHECKING_ELIGIBILITY = "CHECKING ELIGIBILITY";
    private static final String BENEFIT_REDEMPTION = "BENEFIT REDEMPTION";

    ElevateApi elevateApi = new ElevateApi(RequestHelper.signRequest());

    public void callElevateServiceApis(String[] args) {

        if (checkForScenario(args, CHECK_ELIGIBILITY)) {
            logScenario(CHECKING_ELIGIBILITY);
            checkEligibility(elevateApi);
        }

        if (checkForScenario(args, REDEMPTION)) {
            logScenario(BENEFIT_REDEMPTION);
            redemption(elevateApi);
        }
    }

    private void checkEligibility(ElevateApi elevateApi) {
        try {
            CheckEligibility checkEligibility = RequestHelper.getCheckEligibilityPayload();
            logger.info( "\nRequest API: " + RequestHelper.getProperty(BASE_URL) + "/eligibilities \n" +
                    "Source: checkEligibility \n" +
                    "Request Payload : " + new Gson().toJson(maskPayload(checkEligibility))
            );
            Eligibility eligibility = elevateApi.checkEligibility(checkEligibility);
            logResponse(eligibility);
        }catch (ApiException exception){
            logger.info("Exception occurred while checking eligibility: " + exception.getMessage());
            logResponse(exception.getResponseBody());
        }
    }

    private void redemption(ElevateApi elevateApi) {
        try {
            Redemptions redemptions = RequestHelper.getRedemptionsPayload();
            logger.info( "\nRequest API: " + RequestHelper.getProperty(BASE_URL) + "/redemptions \n" +
                    "Source: redemption \n" +
                    "Request Payload : " + new Gson().toJson(maskPayload(redemptions))
            );
            RedemptionInfo redemptionInfo = elevateApi.createRedemption(redemptions);
            logResponse(redemptionInfo);
        }catch (ApiException exception){
            logger.info("Exception occurred while redemption: " + exception.getMessage());
            logResponse(exception.getResponseBody());
        }
    }

    private boolean checkForScenario(String[] args, String scenario) {
        if(args == null || args.length == 0)
            return true;
        else
            return (args[0].contains(scenario));
    }

    private void logScenario(String scenario) {
        String message = "\n--------------------------------------------------------------------" +
                "\n -------------------------------------------------------------------" +
                "\n----------------------- " + scenario + " -----------------------" +
                "\n--------------------------------------------------------------------" +
                "\n--------------------------------------------------------------------\n";
        logger.info(message);
    }

    private void logResponse(Object response) {
        Gson gson = new Gson();
        String responseString = "\n--------------------------- RESPONSE -------------------------------\n"
                + gson.toJson(response)
                + "\n--------------------------------------------------------------------\n";
        logger.info(responseString);
    }

    private CheckEligibility maskPayload(CheckEligibility checkEligibility){
        CheckEligibility mask = new CheckEligibility();
        mask.setPartnerId(checkEligibility.getPartnerId());
        mask.setCreditCardNumber(checkEligibility.getCreditCardNumber());
        mask.setCardHolderName(checkEligibility.getCardHolderName());
        mask.setProductId(checkEligibility.getProductId());
        mask.setEmail(checkEligibility.getEmail());
        mask.setAccessCode(checkEligibility.getAccessCode());
        mask.setCreditCardNumber("************" + checkEligibility.getCreditCardNumber().substring(12,16));
        return mask;
    }

    private Redemptions maskPayload(Redemptions redemptions){
        Redemptions mask = new Redemptions();
        mask.setPartnerId(redemptions.getPartnerId());
        mask.setEligibilityId(redemptions.getEligibilityId());
        mask.setSpendAmount(redemptions.getSpendAmount());
        mask.setSpendCurrencyCode(redemptions.getSpendCurrencyCode());
        mask.setBenefitAmountGiven(redemptions.getBenefitAmountGiven());
        mask.setBenefitCurrencyCode(redemptions.getBenefitCurrencyCode());
        mask.setCreditCardNumber("************" + redemptions.getCreditCardNumber().substring(12,16));
        return mask;
    }
}
