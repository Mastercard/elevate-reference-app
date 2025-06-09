package com.mastercard.developers.mbep.service;


import com.mastercard.developers.mbep.generated.apis.MbepApi;
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
import com.mastercard.developers.mbep.generated.models.PaymentCard;
import com.mastercard.developers.mbep.generated.models.PaymentToken;
import com.mastercard.developers.mbep.generated.models.RedemptionByRealTime;
import com.mastercard.developers.mbep.generated.models.RedemptionInfo;
import com.mastercard.developers.mbep.generated.models.RedemptionInfoData;
import com.mastercard.developers.mbep.generated.models.Redemptions;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MBEPServiceImplTest {

    private static final String BASE_URL = "https://sandbox.api.mastercard.com/elevate";

    @InjectMocks
    MBEPServiceImpl mbepService;

    @Mock
    MbepApi mbepApi;

    private static final String OK = "OK";
    private static final String ID = "390008_0265_2921";
    private static final String AMAZON = "amazon";
    private static final String PRODUCT_ID = "158384";
    private static final String REDEMPTION_CODE = "k86n7a7";
    private static final String REDEMPTION_URL = "https://www.amazonprime.com";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(mbepService, "baseUrl", BASE_URL);
    }
    @Test
    void checkEligibility() throws ApiException {
        Eligibility eligibility = new Eligibility();
        eligibility.setMsg(OK);
        EligibilityData data = new EligibilityData();
        data.setEligible(true);
        data.setEligibilityId(ID);
        data.setAccessCode(AMAZON);
        data.setProductId(PRODUCT_ID);
        data.setRedemptionCode(REDEMPTION_CODE);
        data.setRedemptionURL(REDEMPTION_URL);
        eligibility.setData(data);
        when(mbepApi.checkEligibility(any())).thenReturn(eligibility);
        mbepService.checkEligibility(checkEligibilityObject());
        verify(mbepApi).checkEligibility(any(CheckEligibility.class));
    }

    @Test
    void checkEligibilityByToken() throws ApiException {
        Eligibility eligibility = new Eligibility();
        eligibility.setMsg(OK);
        EligibilityData data = new EligibilityData();
        data.setEligible(true);
        data.setEligibilityId(ID);
        data.setAccessCode(AMAZON);
        data.setProductId(PRODUCT_ID);
        data.setRedemptionCode(REDEMPTION_CODE);
        data.setRedemptionURL(REDEMPTION_URL);
        eligibility.setData(data);
        when(mbepApi.checkEligibilityByToken(any())).thenReturn(eligibility);
        mbepService.checkEligibilityByToken(checkEligibilityByTokenObject());
        verify(mbepApi).checkEligibilityByToken(any(CheckEligibilityByToken.class));
    }

    @Test
    void checkEligibilityPan() throws ApiException {
        Eligibility eligibility = new Eligibility();
        eligibility.setMsg(OK);
        EligibilityData data = new EligibilityData();
        data.setEligible(true);
        data.setEligibilityId(ID);
        data.setAccessCode(AMAZON);
        data.setProductId(PRODUCT_ID);
        data.setRedemptionCode(REDEMPTION_CODE);
        data.setRedemptionURL(REDEMPTION_URL);
        eligibility.setData(data);
        when(mbepApi.checkEligibilityByPan(any())).thenReturn(eligibility);
        mbepService.checkEligibilityByPan(checkEligibilityPanObject());
        verify(mbepApi).checkEligibilityByPan(any(CheckEligibilityByPan.class));
    }

    @Test
    void checkEligibilityException() throws ApiException {
        when(mbepApi.checkEligibility(any())).thenThrow(new ApiException());
        assertThrows(ApiException.class, () -> mbepService.checkEligibility(checkEligibilityObject()));
        verify(mbepApi).checkEligibility(any(CheckEligibility.class));
    }

    @Test
    void redemption() throws ApiException{
        RedemptionInfo redemptionInfo = new RedemptionInfo();
        redemptionInfo.setMsg(OK);
        RedemptionInfoData data = new RedemptionInfoData();
        data.setRedemptionId(ID);
        redemptionInfo.setData(data);
        when(mbepApi.createRedemption(any())).thenReturn(redemptionInfo);
        mbepService.redemption(redemptionsObject());
        verify(mbepApi).createRedemption(any(Redemptions.class));

    }

    @Test
    void testRealTimeRedemption() throws ApiException{
        RedemptionInfo redemptionInfo = new RedemptionInfo();
        redemptionInfo.setMsg(OK);
        RedemptionInfoData data = new RedemptionInfoData();
        data.setRedemptionId(ID);
        redemptionInfo.setData(data);
        when(mbepApi.createRedemptionByRealTime(any())).thenReturn(redemptionInfo);
        mbepService.createRedemptionByRealTime(redemptionByRealTimeObject());
        verify(mbepApi).createRedemptionByRealTime(any(RedemptionByRealTime.class));
    }

    @Test
    void testRedemptionByAccruedTransaction() throws ApiException{
        RedemptionInfo redemptionInfo = new RedemptionInfo();
        redemptionInfo.setMsg(OK);
        RedemptionInfoData data = new RedemptionInfoData();
        data.setRedemptionId(ID);
        redemptionInfo.setData(data);
        when(mbepApi.createRedemptionByAccrueTransactions(any())).thenReturn(redemptionInfo);
        mbepService.createRedemptionByAccrueTransaction(redemptionByAccruedTransactionObject());
        verify(mbepApi).createRedemptionByAccrueTransactions(any(AccrueRedemptionByTransaction.class));
    }

    @Test
    void testRedemptionByAccruedSpend() throws ApiException{
        RedemptionInfo redemptionInfo = new RedemptionInfo();
        redemptionInfo.setMsg(OK);
        RedemptionInfoData data = new RedemptionInfoData();
        data.setRedemptionId(ID);
        redemptionInfo.setData(data);
        when(mbepApi.createRedemptionByAccrueSpend(any())).thenReturn(redemptionInfo);
        mbepService.createRedemptionByAccrueSpend(redemptionByAccruedSpendObject());
        verify(mbepApi).createRedemptionByAccrueSpend(any(AccrueRedemptionBySpend.class));
    }

    @Test
    void checkRedemptionException() throws ApiException {
        when(mbepApi.createRedemption(any())).thenThrow(new ApiException());
        assertThrows(ApiException.class, () -> mbepService.redemption(redemptionsObject()));
        verify(mbepApi).createRedemption(any(Redemptions.class));
    }

    @Test
    void benefits() throws ApiException{
        PartnerBenefitDetails partnerBenefitDetails = new PartnerBenefitDetails();
        partnerBenefitDetails.setMsg(OK);
        PartnerBenefitDetailsData partnerBenefitDetailsData =new PartnerBenefitDetailsData();
        partnerBenefitDetailsData.setPartnerId(ID);
        Benefit benefit = new Benefit();
        benefit.setMerchantName("Elevate API");
        benefit.setProductId(18318);
        benefit.setAccessCode("ae-amazonprime");
        benefit.setActive(true);
        List<Benefit> list = new ArrayList<>();
        list.add(benefit);
        partnerBenefitDetailsData.setBenefits(list);
        partnerBenefitDetails.setData(partnerBenefitDetailsData);
        when(mbepApi.getBenefits()).thenReturn(partnerBenefitDetails);
        mbepService.benefits();
        verify(mbepApi).getBenefits();

    }

    @Test
    void saveTokenSuccess() throws ApiException {
        CardToken cardToken = new CardToken();
        CardTokenInfo tokenInfo = new CardTokenInfo().msg(OK);
        when(mbepApi.saveToken(cardToken)).thenReturn(tokenInfo);
        CardTokenInfo cardTokenInfo = mbepService.saveToken(cardToken);
        verify(mbepApi).saveToken(cardToken);
        assertEquals(tokenInfo.getMsg(), cardTokenInfo.getMsg());
    }

    @Test
    void saveTokenException() throws ApiException {
        CardToken cardToken = new CardToken();
        when(mbepApi.saveToken(cardToken)).thenThrow(new ApiException());
        try {
            mbepService.saveToken(cardToken);
        }catch ( ApiException ignored){
            verify(mbepApi).saveToken(cardToken);
        }
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
        redemptions.setRedemptionURL("https://www.amazonprime.com");
        redemptions.paymentNumber(BigDecimal.valueOf(5555555555556001L));
        return redemptions;
    }

    private RedemptionByRealTime redemptionByRealTimeObject(){
        RedemptionByRealTime redemptions = new RedemptionByRealTime();
        redemptions.setPartnerId(669);
        redemptions.setEligibilityId("129017_161041_1179");
        redemptions.setBenefitEndTime("2023-05-04T00:00:00Z");
        redemptions.setBenefitStartTime("2022-05-04T00:00:00Z");
        redemptions.setExternalIdentifier("EXT_ID-9a5k7");
        redemptions.setIsDefaultCardOnFile(1);
        redemptions.setRedeemedTime("2023-04-04T00:00:00Z");
        redemptions.setRedemptionCode("k86n7a7");
        redemptions.setRedemptionURL("https://www.amazonprime.com");
        return redemptions;
    }

    private AccrueRedemptionByTransaction redemptionByAccruedTransactionObject() {
        AccrueRedemptionByTransaction redemptions = new AccrueRedemptionByTransaction();
        redemptions.setPartnerId(669);
        redemptions.setEligibilityId("129017_161041_1179");
        redemptions.setAccruedTransactionsCount(BigDecimal.valueOf(109.99));
        redemptions.setCurrencyCode("USD");
        redemptions.benefitWorth(BigDecimal.valueOf(50.99));
        redemptions.benefitType("money");
        return redemptions;
    }

    private AccrueRedemptionBySpend redemptionByAccruedSpendObject() {
        AccrueRedemptionBySpend redemptions = new AccrueRedemptionBySpend();
        redemptions.setPartnerId(669);
        redemptions.setEligibilityId("129017_161041_1179");
        redemptions.setAccruedSpend(BigDecimal.valueOf(2));
        redemptions.setCurrencyCode("USD");
        redemptions.benefitWorth(BigDecimal.valueOf(50.99));
        redemptions.benefitType("money");
        return redemptions;
    }

    private CheckEligibility checkEligibilityObject(){
        CheckEligibility checkEligibility = new CheckEligibility();
        checkEligibility.setPartnerId(669);
        checkEligibility.setEmail("ak.chauhan@hotmail.com");
        checkEligibility.setProductId(161041);
        checkEligibility.setAccessCode("ae-amazonprime");
        checkEligibility.setPaymentCard(getPaymentCard());
        return checkEligibility;
    }

    private CheckEligibilityByPan checkEligibilityPanObject(){
        CheckEligibilityByPan checkEligibilityPan = new CheckEligibilityByPan();
        checkEligibilityPan.setPartnerId(669);
        checkEligibilityPan.setEmail("ak.chauhan@hotmail.com");
        checkEligibilityPan.setProductId(161041);
        checkEligibilityPan.setAccessCode("ae-amazonprime");
        checkEligibilityPan.setPaymentCard(getPaymentCard());
        return checkEligibilityPan;
    }

    private CheckEligibilityByToken checkEligibilityByTokenObject(){
        CheckEligibilityByToken checkEligibilityByToken = new CheckEligibilityByToken();
        checkEligibilityByToken.setPartnerId(669);
        checkEligibilityByToken.setEmail("ak.chauhan@hotmail.com");
        checkEligibilityByToken.setProductId(161041);
        checkEligibilityByToken.setAccessCode("ae-amazonprime");
        checkEligibilityByToken.setPaymentToken(getPaymentToken());
        return checkEligibilityByToken;
    }

    private PaymentCard getPaymentCard(){
        PaymentCard paymentCard=new PaymentCard();
        paymentCard.setPaymentNumber(BigDecimal.valueOf(5555555555556001L));
        paymentCard.setCardHolderName("A K Chauhan");
        paymentCard.setExpirationMonth("04");
        paymentCard.setExpirationYear("24");
        return paymentCard;
    }

    private PaymentToken getPaymentToken(){
        PaymentToken paymentToken=new PaymentToken();
        paymentToken.setToken("MTFToken12355555555555561005555555555556647");
        paymentToken.setTokenType("partner");
        paymentToken.setPspId(String.valueOf(669));
        return paymentToken;
    }

}
