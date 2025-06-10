package com.mastercard.developers.mbep.controller;

import com.mastercard.developers.mbep.generated.invokers.ApiException;
import com.mastercard.developers.mbep.generated.models.AccrueRedemptionBySpend;
import com.mastercard.developers.mbep.generated.models.AccrueRedemptionByTransaction;
import com.mastercard.developers.mbep.generated.models.Benefit;
import com.mastercard.developers.mbep.generated.models.CardToken;
import com.mastercard.developers.mbep.generated.models.CardTokenInfo;
import com.mastercard.developers.mbep.generated.models.CheckEligibility;
import com.mastercard.developers.mbep.generated.models.CheckEligibilityByPan;
import com.mastercard.developers.mbep.generated.models.CheckEligibilityByToken;
import com.mastercard.developers.mbep.generated.models.Eligibility;
import com.mastercard.developers.mbep.generated.models.EligibilityData;
import com.mastercard.developers.mbep.generated.models.PartnerBenefitDetails;
import com.mastercard.developers.mbep.generated.models.PartnerBenefitDetailsData;
import com.mastercard.developers.mbep.generated.models.RedemptionByRealTime;
import com.mastercard.developers.mbep.generated.models.RedemptionInfo;
import com.mastercard.developers.mbep.generated.models.RedemptionInfoData;
import com.mastercard.developers.mbep.generated.models.Redemptions;
import com.mastercard.developers.mbep.service.MBEPServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RequestControllerTest {

    public static final String OK = "OK";
    @InjectMocks
    RequestController requestController;

    @Mock
    MBEPServiceImpl elevateAcceleratorService;

    @Mock
    Redemptions redemptionPayload;

    @Mock
    CheckEligibility eligibilityPayload;

    @Mock
    CheckEligibilityByToken eligibilityPayloadByToken;

    @Mock
    CheckEligibilityByPan eligibilityPayloadByPan;

    @Mock
    AccrueRedemptionByTransaction accrueRedemptionByTransaction;

    @Mock
    AccrueRedemptionBySpend accrueRedemptionBySpend;

    @Mock
    RedemptionByRealTime redemptionByRealTime;

    @Test
    void eligibility() throws ApiException {

        Eligibility eligibility = new Eligibility();
        eligibility.msg(OK);
        EligibilityData eligibilityData =new EligibilityData();
        eligibilityData.setEligible(true);
        eligibilityData.setEligibilityId("1234_5467_50");
        eligibilityData.setRedemptionURL("https://www.amazonxxx.com");
        eligibilityData.setRedemptionCode("k86n7a7");
        eligibilityData.setProductId("6");
        eligibilityData.setAccessCode("20");
        eligibility.setData(eligibilityData);
        when(elevateAcceleratorService.checkEligibility(any())).thenReturn(eligibility);
        String eligibilityResponseEntity = requestController.eligibilities(eligibilityPayload);

        assertNotNull(eligibilityResponseEntity);
    }

    @Test
    void eligibilityByToken() throws ApiException {

        Eligibility eligibility = new Eligibility();
        eligibility.msg(OK);
        EligibilityData eligibilityData = new EligibilityData();
        eligibilityData.setEligible(true);
        eligibilityData.setEligibilityId("1234_5467_50");
        eligibilityData.setRedemptionURL("https://www.amazonxxx.com");
        eligibilityData.setRedemptionCode("k86n7a7");
        eligibilityData.setProductId("6");
        eligibilityData.setAccessCode("20");
        eligibility.setData(eligibilityData);
        when(elevateAcceleratorService.checkEligibilityByToken(any())).thenReturn(eligibility);
        String eligibilityResponseEntity = requestController.eligibilitiesByToken(eligibilityPayloadByToken);

        assertNotNull(eligibilityResponseEntity);
    }

    @Test
    void eligibilityByPan() throws ApiException {

        Eligibility eligibility = new Eligibility();
        eligibility.msg(OK);
        EligibilityData eligibilityData = new EligibilityData();
        eligibilityData.setEligible(true);
        eligibilityData.setEligibilityId("1234_5467_50");
        eligibilityData.setRedemptionURL("https://www.amazonxxx.com");
        eligibilityData.setRedemptionCode("k86n7a7");
        eligibilityData.setProductId("6");
        eligibilityData.setAccessCode("20");
        eligibility.setData(eligibilityData);
        when(elevateAcceleratorService.checkEligibilityByPan(any())).thenReturn(eligibility);
        String eligibilityResponseEntity = requestController.eligibilitiesByPan(eligibilityPayloadByPan);

        assertNotNull(eligibilityResponseEntity);
    }


    @Test
    void eligibilityFailure() throws ApiException {
        when(elevateAcceleratorService.checkEligibility(any())).thenThrow(new ApiException());
        assertThrows(ApiException.class, () -> requestController.eligibilities(eligibilityPayload));
        verify(elevateAcceleratorService).checkEligibility(any(CheckEligibility.class));
    }

    @Test
    void redemptions() throws ApiException{

        RedemptionInfo redemptionInfo = new RedemptionInfo();
        redemptionInfo.setMsg(OK);
        RedemptionInfoData redemptionInfoData =new RedemptionInfoData();
        redemptionInfoData.setRedemptionId("265342347");
        redemptionInfo.setData(redemptionInfoData);
        when(elevateAcceleratorService.redemption(any())).thenReturn(redemptionInfo);
        String redemptionInfoResponseEntity = requestController.redemptions(redemptionPayload);
        assertNotNull(redemptionInfoResponseEntity);
    }

    @Test
    void testRealTimeRedemption() throws ApiException{
        RedemptionInfo redemptionInfo = new RedemptionInfo();
        redemptionInfo.setMsg(OK);
        RedemptionInfoData redemptionInfoData =new RedemptionInfoData();
        redemptionInfoData.setRedemptionId("265342347");
        redemptionInfo.setData(redemptionInfoData);
        when(elevateAcceleratorService.createRedemptionByRealTime(any())).thenReturn(redemptionInfo);
        String redemptionInfoResponseEntity = requestController.realTimeRedemption(redemptionByRealTime);
        assertNotNull(redemptionInfoResponseEntity);
    }

    @Test
    void testAccruedRedemptionByTransaction() throws ApiException {
        RedemptionInfo redemptionInfo = new RedemptionInfo();
        redemptionInfo.setMsg(OK);
        RedemptionInfoData redemptionInfoData =new RedemptionInfoData();
        redemptionInfoData.setRedemptionId("265342347");
        redemptionInfo.setData(redemptionInfoData);
        when(elevateAcceleratorService.createRedemptionByAccrueTransaction(any())).thenReturn(redemptionInfo);
        String redemptionInfoResponseEntity = requestController.accrueRedemptionByTransaction(accrueRedemptionByTransaction);
        assertNotNull(redemptionInfoResponseEntity);
    }

    @Test
    void testAccruedRedemptionBySpend() throws ApiException {
        RedemptionInfo redemptionInfo = new RedemptionInfo();
        redemptionInfo.setMsg(OK);
        RedemptionInfoData redemptionInfoData =new RedemptionInfoData();
        redemptionInfoData.setRedemptionId("265342347");
        redemptionInfo.setData(redemptionInfoData);
        when(elevateAcceleratorService.createRedemptionByAccrueSpend(any())).thenReturn(redemptionInfo);
        String redemptionInfoResponseEntity = requestController.accrueRedemptionBySpend(accrueRedemptionBySpend);
        assertNotNull(redemptionInfoResponseEntity);
    }

    @Test
    void redemptionsFailure() throws ApiException{
        when(elevateAcceleratorService.redemption(any())).thenThrow(new ApiException());
        assertThrows(ApiException.class, () -> requestController.redemptions(redemptionPayload));
        verify(elevateAcceleratorService).redemption(any(Redemptions.class));
    }

    @Test
    void benefits() throws ApiException{

        PartnerBenefitDetails partnerBenefitDetails = new PartnerBenefitDetails();
        partnerBenefitDetails.setMsg(OK);
        PartnerBenefitDetailsData partnerBenefitDetailsData =new PartnerBenefitDetailsData();
        partnerBenefitDetailsData.setPartnerId("265342347");
        Benefit benefit = new Benefit();
        benefit.setMerchantName("Elevate API");
        benefit.setProductId(18318);
        benefit.setAccessCode("ae-amazonprime");
        benefit.setActive(true);
        List<Benefit> list = new ArrayList<>();
        list.add(benefit);
        partnerBenefitDetailsData.setBenefits(list);
        partnerBenefitDetails.setData(partnerBenefitDetailsData);
        when(elevateAcceleratorService.benefits()).thenReturn(partnerBenefitDetails);
        String benefitsResponseEntity = requestController.benefits();
        assertNotNull(benefitsResponseEntity);
    }

    @Test
    void saveTokenSuccess() throws ApiException {
        CardToken cardToken = new CardToken();
        CardTokenInfo tokenInfo = new CardTokenInfo();
        tokenInfo.setMsg(OK);
        when(elevateAcceleratorService.saveToken(cardToken)).thenReturn(tokenInfo);
        String response = requestController.storePaymentToken(cardToken);
        assertNotNull(response);
        verify(elevateAcceleratorService).saveToken(cardToken);
        assertTrue(response.contains(OK));
    }

    @Test
    void saveTokenException() throws ApiException {
        CardToken cardToken = new CardToken();
        when(elevateAcceleratorService.saveToken(cardToken)).thenThrow(new ApiException());
        try {
            requestController.storePaymentToken(cardToken);
        }catch (ApiException ignored) {
            verify(elevateAcceleratorService).saveToken(cardToken);
        }
    }
}
