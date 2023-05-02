package com.mastercard.developers.elevate.accelerator.controller;

import com.google.gson.Gson;
import com.mastercard.developers.elevate.accelerator.generated.invokers.ApiException;
import com.mastercard.developers.elevate.accelerator.generated.models.CheckEligibility;
import com.mastercard.developers.elevate.accelerator.generated.models.Eligibility;
import com.mastercard.developers.elevate.accelerator.generated.models.PartnerBenefitDetails;
import com.mastercard.developers.elevate.accelerator.generated.models.RedemptionInfo;
import com.mastercard.developers.elevate.accelerator.generated.models.Redemptions;
import com.mastercard.developers.elevate.accelerator.service.ElevateAcceleratorServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ElevateAcceleratorServiceImpl elevateAcceleratorService;

    @PostMapping( value = "/eligibilities")
    public String eligibilities(@RequestBody CheckEligibility checkEligibility ) throws ApiException {
        Eligibility resposne = elevateAcceleratorService.checkEligibility(checkEligibility);
        return new Gson().toJson(resposne);
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
}
