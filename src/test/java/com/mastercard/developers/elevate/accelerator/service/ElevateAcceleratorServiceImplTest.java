package com.mastercard.developers.elevate.accelerator.service;

import com.mastercard.developers.elevate.accelerator.generated.apis.ElevateApi;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ElevateAcceleratorServiceImplTest {

    private static final String BASE_URL = "https://sandbox.api.mastercard.com/elevate";

    @InjectMocks
    ElevateAcceleratorServiceImpl elevateAcceleratorService;

    @Mock
    ElevateApi elevateApi;

    private static final String OK = "OK";
    private static final String ID = "390008_0265_2921";
    private static final String ABC_MERCHANT = "abcmerchant";
    private static final String PRODUCT_ID = "158384";
    private static final String REDEMPTION_CODE = "k86n7a7";
    private static final String REDEMPTION_URL = "https://www.abcmerchant.com";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField( elevateAcceleratorService , "baseUrl", BASE_URL);
    }
    @Test
    void checkEligibility() throws ApiException {
        Eligibility eligibility = new Eligibility();
        eligibility.setMsg(OK);
        EligibilityData data = new EligibilityData();
        data.setEligible(true);
        data.setEligibilityId(ID);
        data.setAccessCode(ABC_MERCHANT);
        data.setProductId(PRODUCT_ID);
        data.setRedemptionCode(REDEMPTION_CODE);
        data.setRedemptionURL(REDEMPTION_URL);
        eligibility.setData(data);
        when(elevateApi.checkEligibility(any())).thenReturn(eligibility);
        elevateAcceleratorService.checkEligibility(checkEligibilityObject());
        verify(elevateApi).checkEligibility(any(CheckEligibility.class));
    }

    @Test
    void redemption() throws ApiException{
        RedemptionInfo redemptionInfo = new RedemptionInfo();
        redemptionInfo.setMsg(OK);
        RedemptionInfoData data = new RedemptionInfoData();
        data.setRedemptionId(ID);
        redemptionInfo.setData(data);
        when(elevateApi.createRedemption(any())).thenReturn(redemptionInfo);
        elevateAcceleratorService.redemption(redemptionsObject());
        verify(elevateApi).createRedemption(any(Redemptions.class));

    }

    @Test
    void benefits() throws ApiException{
        PartnerBenefitDetails partnerBenefitDetails = new PartnerBenefitDetails();
        partnerBenefitDetails.setMsg("OK");
        PartnerBenefitDetailsData partnerBenefitDetailsData =new PartnerBenefitDetailsData();
        partnerBenefitDetailsData.setPartnerId(ID);
        Benefit benefit = new Benefit();
        benefit.setMerchantName("Elevate API");
        benefit.setProductId(18318);
        benefit.setAccessCode("MyAC-611-20044");
        benefit.setActive(true);
        List<Benefit> list = new ArrayList<>();
        list.add(benefit);
        partnerBenefitDetailsData.setBenefits(list);
        partnerBenefitDetails.setData(partnerBenefitDetailsData);
        when(elevateApi.getBenefits()).thenReturn(partnerBenefitDetails);
        elevateAcceleratorService.benefits();
        verify(elevateApi).getBenefits();

    }


    private Redemptions redemptionsObject(){
        Redemptions redemptions = new Redemptions();
        redemptions.setPartnerId(669);
        redemptions.setEligibilityId("129017_161041_1179");
        redemptions.setSpendAmount(BigDecimal.valueOf(109.99));
        redemptions.setSpendCurrencyCode("USD");
        redemptions.setBenefitAmountGiven(BigDecimal.valueOf(50.99));
        redemptions.setBenefitCurrencyCode("USD");
        redemptions.setBenefitEndTime("2023-05-04T00:00:00Z");
        redemptions.setBenefitStartTime("2022-05-04T00:00:00Z");
        redemptions.setExternalIdentifier("EXT_ID-9a5k7");
        redemptions.setIsDefaultCardOnFile(1);
        redemptions.setRedeemedTime("2023-04-04T00:00:00Z");
        redemptions.setRedemptionCode("k86n7a7");
        redemptions.setRedemptionURL("https://www.abcmerchant.com");
        redemptions.setCreditCardNumber("5555555555556001");
        return redemptions;
    }

    private CheckEligibility checkEligibilityObject(){
        CheckEligibility checkEligibility = new CheckEligibility();
        checkEligibility.setExpirationYear("24");
        checkEligibility.setPartnerId(669);
        checkEligibility.setExpirationMonth("04");
        checkEligibility.setCardHolderName("A K Chauhan");
        checkEligibility.setEmail("ak.chauhan@hotmail.com");
        checkEligibility.setProductId(161041);
        checkEligibility.setAccessCode("MyAC-611-20044");
        checkEligibility.setCreditCardNumber("5555555555556001");
        return checkEligibility;
    }

}
