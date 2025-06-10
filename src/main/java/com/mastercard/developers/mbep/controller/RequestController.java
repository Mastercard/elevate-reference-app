package com.mastercard.developers.mbep.controller;

import com.google.gson.Gson;
import com.mastercard.developers.mbep.generated.invokers.ApiException;
import com.mastercard.developers.mbep.generated.models.AccrueRedemptionBySpend;
import com.mastercard.developers.mbep.generated.models.AccrueRedemptionByTransaction;
import com.mastercard.developers.mbep.generated.models.CardToken;
import com.mastercard.developers.mbep.generated.models.CheckEligibility;
import com.mastercard.developers.mbep.generated.models.CheckEligibilityByPan;
import com.mastercard.developers.mbep.generated.models.CheckEligibilityByToken;
import com.mastercard.developers.mbep.generated.models.RedemptionByRealTime;
import com.mastercard.developers.mbep.generated.models.Redemptions;
import com.mastercard.developers.mbep.service.MBEPServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/*
 * This class is used to invoke mbep APIs
 */
@Slf4j
@RestController
public class RequestController {

    private final MBEPServiceImpl mbepService;

    public RequestController(MBEPServiceImpl mbepService) {
        this.mbepService = mbepService;
    }

    /**
     * Endpoint to check eligibility.
     *
     * @param checkEligibility the check eligibility request payload
     * @return the eligibility response as JSON
     * @throws ApiException if an error occurs while calling the API
     */
    @PostMapping( value = "/eligibilities")
    public String eligibilities(@RequestBody CheckEligibility checkEligibility ) throws ApiException {
        return new Gson().toJson( mbepService.checkEligibility(checkEligibility));
    }

    /**
     * Endpoint to check eligibility by token.
     *
     * @param checkEligibilityByToken the check eligibility by token request payload
     * @return the eligibility response as JSON
     * @throws ApiException if an error occurs while calling the API
     */
    @PostMapping(value = "/eligibilities/token")
    public String eligibilitiesByToken(@RequestBody CheckEligibilityByToken checkEligibilityByToken) throws ApiException {
        return new Gson().toJson(mbepService.checkEligibilityByToken(checkEligibilityByToken));
    }

    /**
     * Endpoint to create a redemption.
     *
     * @param redemptions the redemptions request payload
     * @return the redemption info response as JSON
     * @throws ApiException if an error occurs while calling the API
     */
    @PostMapping(value = "/redemptions")
    public String redemptions(@RequestBody Redemptions redemptions) throws ApiException {
        return new Gson().toJson(mbepService.redemption(redemptions));
    }

    /**
     * Endpoint to retrieve partner benefit details.
     *
     * @return the partner benefit details response as JSON
     * @throws ApiException if an error occurs while calling the API
     */
    @GetMapping (value = "/benefits")
    public String benefits() throws ApiException {
        return new Gson().toJson(mbepService.benefits());
    }

    /**
     * Endpoint to save a card token.
     *
     * @param cardToken the card token request payload
     * @return the card token info response as JSON
     * @throws ApiException if an error occurs while calling the API
     */
    @PostMapping(value = "/payment-tokens")
    public String storePaymentToken(@RequestBody CardToken cardToken) throws ApiException {
        return new Gson().toJson(mbepService.saveToken(cardToken));
    }

    /**
     * Endpoint to check eligibility by PAN.
     *
     * @param checkEligibilityByPan the check eligibility by PAN request payload
     * @return the eligibility response as JSON
     * @throws ApiException if an error occurs while calling the API
     */
    @PostMapping(value = "/eligibilities/pan")
    public String eligibilitiesByPan(@RequestBody CheckEligibilityByPan checkEligibilityByPan) throws ApiException {
        return new Gson().toJson(mbepService.checkEligibilityByPan(checkEligibilityByPan));
    }

    /**
     * Endpoint to create a real-time redemption.
     *
     * @param redemptionByRealTime the redemption by real-time request payload
     * @return the redemption info response as JSON
     * @throws ApiException if an error occurs while calling the API
     */
    @PostMapping(value = "/redemptions/real-time")
    public String realTimeRedemption(@RequestBody RedemptionByRealTime redemptionByRealTime) throws ApiException {
        return new Gson().toJson(mbepService.createRedemptionByRealTime(redemptionByRealTime));
    }

    /**
     * Endpoint to create a redemption by accrued transaction.
     *
     * @param accrueRedemptionByTransaction the accrue redemption by transaction request payload
     * @return the redemption info response as JSON
     * @throws ApiException if an error occurs while calling the API
     */
    @PostMapping(value = "/redemptions/accrued/transactions")
    public String accrueRedemptionByTransaction(@RequestBody AccrueRedemptionByTransaction accrueRedemptionByTransaction) throws ApiException {
        return new Gson().toJson(mbepService.createRedemptionByAccrueTransaction(accrueRedemptionByTransaction));
    }

    /**
     * Endpoint to create a redemption by accrued spend.
     *
     * @param accrueRedemptionBySpend the accrue redemption by spend request payload
     * @return the redemption info response as JSON
     * @throws ApiException if an error occurs while calling the API
     */
    @PostMapping(value = "/redemptions/accrued/spend")
    public String accrueRedemptionBySpend(@RequestBody AccrueRedemptionBySpend accrueRedemptionBySpend) throws ApiException {
        return new Gson().toJson(mbepService.createRedemptionByAccrueSpend(accrueRedemptionBySpend));
    }

}
