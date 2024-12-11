package com.mastercard.developers.mbep.controller;

import com.google.gson.Gson;
import com.mastercard.developers.mbep.generated.invokers.ApiException;
import com.mastercard.developers.mbep.generated.models.CardToken;
import com.mastercard.developers.mbep.generated.models.CardTokenInfo;
import com.mastercard.developers.mbep.generated.models.CheckEligibility;
import com.mastercard.developers.mbep.generated.models.Eligibility;
import com.mastercard.developers.mbep.generated.models.PartnerBenefitDetails;
import com.mastercard.developers.mbep.generated.models.RedemptionInfo;
import com.mastercard.developers.mbep.generated.models.Redemptions;
import com.mastercard.developers.mbep.service.MBEPServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.mastercard.developers.mbep.generated.models.RedemptionByRealTimePan;
import com.mastercard.developers.mbep.generated.models.RedemptionByRealTimeToken;
import com.mastercard.developers.mbep.generated.models.CheckEligibilityByPan;
import com.mastercard.developers.mbep.generated.models.CheckEligibilityByToken;

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

    @PostMapping( value = "/eligibilities")
    public String eligibilities(@RequestBody CheckEligibility checkEligibility ) throws ApiException {
        Eligibility response = mbepService.checkEligibility(checkEligibility);
        return new Gson().toJson(response);
    }

    @PostMapping(value = "/redemptions")
    public String redemptions(@RequestBody Redemptions redemptions) throws ApiException {
        RedemptionInfo response = mbepService.redemption(redemptions);
        return new Gson().toJson(response);
    }

    @GetMapping (value = "/benefits")
    public String benefits() throws ApiException {
        PartnerBenefitDetails response = mbepService.benefits();
        return new Gson().toJson(response);
    }

    @PostMapping(value = "/payment-tokens")
    public String storePaymentToken(@RequestBody CardToken cardToken) throws ApiException {
        CardTokenInfo response = mbepService.saveToken(cardToken);
        return new Gson().toJson(response);
    }

    @PostMapping(value = "/eligibilities/pan")
    public String eligibilitiesByPan(@RequestBody CheckEligibilityByPan checkEligibilityByPan) throws ApiException {
        return new Gson().toJson(mbepService.checkEligibilityByPan(checkEligibilityByPan));
    }

    @PostMapping(value = "/eligibilities/token")
    public String eligibilitiesByToken(@RequestBody CheckEligibilityByToken checkEligibilityByToken) throws ApiException {
        return new Gson().toJson(mbepService.checkEligibilityByToken(checkEligibilityByToken));
    }

    @PostMapping(value = "/redemptions/real-time/token")
    public String realTimeRedemptionByToken(@RequestBody RedemptionByRealTimeToken redemptionByRealTimeToken) throws ApiException {
        return new Gson().toJson(mbepService.createRedemptionByRealTimeToken(redemptionByRealTimeToken));
    }

    @PostMapping(value = "/redemptions/real-time/pan")
    public String realTimeRedemptionByPan(@RequestBody RedemptionByRealTimePan redemptionByRealTimePan) throws ApiException {
        return new Gson().toJson(mbepService.createRedemptionByRealTimePan(redemptionByRealTimePan));
    }
}
