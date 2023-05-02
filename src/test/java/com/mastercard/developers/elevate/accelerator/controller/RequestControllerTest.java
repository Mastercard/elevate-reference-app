package com.mastercard.developers.elevate.accelerator.controller;

import com.mastercard.developers.elevate.accelerator.generated.invokers.ApiException;
import com.mastercard.developers.elevate.accelerator.generated.models.Benefit;
import com.mastercard.developers.elevate.accelerator.generated.models.CheckEligibility;
import com.mastercard.developers.elevate.accelerator.generated.models.Eligibility;
import com.mastercard.developers.elevate.accelerator.generated.models.EligibilityData;
import com.mastercard.developers.elevate.accelerator.generated.models.PartnerBenefitDetails;
import com.mastercard.developers.elevate.accelerator.generated.models.PartnerBenefitDetailsData;
import com.mastercard.developers.elevate.accelerator.generated.models.RedemptionInfo;
import com.mastercard.developers.elevate.accelerator.generated.models.RedemptionInfoData;
import com.mastercard.developers.elevate.accelerator.generated.models.Redemptions;
import com.mastercard.developers.elevate.accelerator.service.ElevateAcceleratorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RequestControllerTest {


    @InjectMocks
    RequestController requestController;

    @Mock
    ElevateAcceleratorServiceImpl elevateAcceleratorService;

    @Mock
    Redemptions redemptionPayload;

    @Mock
    CheckEligibility eligibilityPayload;


    @BeforeEach
    void setUp() {

    }

    @Test
    void eligibilities() throws ApiException {

        Eligibility eligibility = new Eligibility();
        eligibility.msg("ok");
        EligibilityData eligibilityData =new EligibilityData();
        eligibilityData.setEligible(true);
        eligibilityData.setEligibilityId("1234_5467_50");
        eligibilityData.setRedemptionURL("https://www.abcmerchant.com");
        eligibilityData.setRedemptionCode("k86n7a7");
        eligibilityData.setProductId("6");
        eligibilityData.setAccessCode("20");
        eligibility.setData(eligibilityData);
        when(elevateAcceleratorService.checkEligibility(any())).thenReturn(eligibility);
        String eligibilityResponseEntity = requestController.eligibilities(eligibilityPayload);

        assertNotNull(eligibilityResponseEntity);
    }

    @Test
    void redemptions() throws ApiException{

        RedemptionInfo redemptionInfo = new RedemptionInfo();
        redemptionInfo.setMsg("OK");
        RedemptionInfoData redemptionInfoData =new RedemptionInfoData();
        redemptionInfoData.setRedemptionId("265342347");
        redemptionInfo.setData(redemptionInfoData);
        when(elevateAcceleratorService.redemption(any())).thenReturn(redemptionInfo);
        String redemptionInfoResponseEntity = requestController.redemptions(redemptionPayload);
        assertNotNull(redemptionInfoResponseEntity);
    }

    @Test
    void benefits() throws ApiException{

        PartnerBenefitDetails partnerBenefitDetails = new PartnerBenefitDetails();
        partnerBenefitDetails.setMsg("OK");
        PartnerBenefitDetailsData partnerBenefitDetailsData =new PartnerBenefitDetailsData();
        partnerBenefitDetailsData.setPartnerId("265342347");
        Benefit benefit = new Benefit();
        benefit.setMerchantName("Elevate API");
        benefit.setProductId(18318);
        benefit.setAccessCode("MyAC-611-20044");
        benefit.setActive(true);
        List<Benefit> list = new ArrayList<>();
        list.add(benefit);
        partnerBenefitDetailsData.setBenefits(list);
        partnerBenefitDetails.setData(partnerBenefitDetailsData);
        when(elevateAcceleratorService.benefits()).thenReturn(partnerBenefitDetails);
        String benefitsResponseEntity = requestController.benefits();
        assertNotNull(benefitsResponseEntity);
    }
}
