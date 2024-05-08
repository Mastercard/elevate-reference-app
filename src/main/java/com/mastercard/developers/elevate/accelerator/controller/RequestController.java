package com.mastercard.developers.elevate.accelerator.controller;

import com.google.gson.Gson;
import com.mastercard.developers.elevate.accelerator.generated.invokers.ApiException;
import com.mastercard.developers.elevate.accelerator.generated.models.CardToken;
import com.mastercard.developers.elevate.accelerator.generated.models.CardTokenInfo;
import com.mastercard.developers.elevate.accelerator.generated.models.CheckEligibility;
import com.mastercard.developers.elevate.accelerator.generated.models.Eligibility;
import com.mastercard.developers.elevate.accelerator.generated.models.PartnerBenefitDetails;
import com.mastercard.developers.elevate.accelerator.generated.models.RedemptionInfo;
import com.mastercard.developers.elevate.accelerator.generated.models.Redemptions;
import com.mastercard.developers.elevate.accelerator.service.ElevateAcceleratorServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/*
* This class is used to invoke elevate APIs
*/
@Slf4j
@RestController
public class RequestController {

    private final ElevateAcceleratorServiceImpl elevateAcceleratorService;

    public RequestController(ElevateAcceleratorServiceImpl elevateAcceleratorService) {
        this.elevateAcceleratorService = elevateAcceleratorService;
    }

    @PostMapping( value = "/eligibilities")
    public String eligibilities(@RequestBody CheckEligibility checkEligibility ) throws ApiException {
        Eligibility response = elevateAcceleratorService.checkEligibility(checkEligibility);
        return new Gson().toJson(response);
    }

    @PostMapping(value = "/redemptions")
    public String redemptions(@RequestBody Redemptions redemptions) throws ApiException {
        RedemptionInfo response = elevateAcceleratorService.redemption(redemptions);
        return new Gson().toJson(response);
    }

    @GetMapping (value = "/benefits")
    public String benefits() throws ApiException {
        PartnerBenefitDetails response = elevateAcceleratorService.benefits();
        return new Gson().toJson(response);
    }

    @PostMapping(value = "/payment-tokens")
    public String storePaymentToken(@RequestBody CardToken cardToken) throws ApiException {
        CardTokenInfo response = elevateAcceleratorService.saveToken(cardToken);
        return new Gson().toJson(response);
    }
}
