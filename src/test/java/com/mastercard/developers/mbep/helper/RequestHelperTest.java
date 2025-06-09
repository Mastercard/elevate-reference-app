package com.mastercard.developers.mbep.helper;

import com.mastercard.developers.mbep.generated.invokers.ApiClient;
import com.mastercard.developers.mbep.generated.models.CheckEligibility;
import com.mastercard.developers.mbep.generated.models.Redemptions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RequestHelperTest {

    private static final String KEY_BASE_URL = "mastercard.mbep.client.api.base.path";

    private static final String VALUE_BASE_URL = "https://sandbox.api.mastercard.com/elevate";

    private static final String ACCESS_CODE = "ae-amazonprime";
    private static final String CURRENCY_CODE = "USD";
    private static final String CARDHOLDER_NAME = "A K Chauhan";
    private static final String EMAIL = "ak.chauhan@hotmail.com";
    private static final String EXPIRY_MONTH = "04";
    private static final String EXPIRY_YEAR = "24";
    private static final String BENEFIT_END_TIME = "2023-05-04T00:00:00Z";
    private static final String BENEFIT_START_TIME = "2022-05-04T00:00:00Z";
    private static final String BENEFIT_REDEMPTION_TIME = "2023-04-04T00:00:00Z";
    private static final String EXTERNAL_IDENTIFIER = "EXT_ID-9a5k7";
    private static final String REDEMPTION_CODE = "k86n7a7";
    private static final String REDEMPTION_URL = "https://www.amazonprime.com";
    private static final int IS_DEFAULT_CARD_ON_FILE = 1;
    private static final BigDecimal SPEND_AMOUNT = BigDecimal.valueOf(109.99);
    private static final BigDecimal BENEFIT_AMOUNT_GIVEN = BigDecimal.valueOf(50.99);

    @Test
    void testGetProperty(){
        String value = RequestHelper.getProperty(KEY_BASE_URL);
        assertEquals(VALUE_BASE_URL, value);
    }

    @Test
    void testLoadProperties(){
        RequestHelper.loadProperties();
        assertEquals(VALUE_BASE_URL, RequestHelper.getProperty(KEY_BASE_URL));
    }

    @Test
    void testClientId() {
        ApiClient apiClient = RequestHelper.signRequest();
        assertNotNull(apiClient);
        assertEquals(VALUE_BASE_URL, apiClient.getBasePath());
    }

    @Test
    void testCheckEligibilityPayload(){
        CheckEligibility checkEligibilityPayload = RequestHelper.getCheckEligibilityPayload();
        assertEquals(ACCESS_CODE, checkEligibilityPayload.getAccessCode());
        assertEquals(CARDHOLDER_NAME, checkEligibilityPayload.getPaymentCard().getCardHolderName());
        assertEquals(EMAIL, checkEligibilityPayload.getEmail());
        assertEquals(EXPIRY_MONTH, checkEligibilityPayload.getPaymentCard().getExpirationMonth());
        assertEquals(EXPIRY_YEAR, checkEligibilityPayload.getPaymentCard().getExpirationYear());
    }

    @Test
    void testRedemptionsPayload(){
        Redemptions redemptions = RequestHelper.getRedemptionsPayload();
        assertEquals(CURRENCY_CODE, redemptions.getBenefitCurrencyCode());
        assertEquals(BENEFIT_AMOUNT_GIVEN, redemptions.getBenefitAmountGiven());
        assertEquals(SPEND_AMOUNT, redemptions.getSpendAmount());
        assertEquals(CURRENCY_CODE, redemptions.getSpendCurrencyCode());
        assertEquals(BENEFIT_END_TIME, redemptions.getBenefitEndTime());
        assertEquals(BENEFIT_START_TIME, redemptions.getBenefitStartTime());
        assertEquals(EXTERNAL_IDENTIFIER, redemptions.getExternalIdentifier());
        assertEquals(BENEFIT_REDEMPTION_TIME, redemptions.getRedeemedTime());
        assertEquals(REDEMPTION_CODE, redemptions.getRedemptionCode());
        assertEquals(REDEMPTION_URL, redemptions.getRedemptionURL());
        assertEquals(IS_DEFAULT_CARD_ON_FILE, redemptions.getIsDefaultCardOnFile());
    }

    @Test
    void testInvalidPropertiesFileError(){
        RequestHelper.setProp(null);
        RequestHelper.setPropertyFile("invalidFile");
        RequestHelper.loadProperties();
        assertNull(RequestHelper.getProperty(KEY_BASE_URL));
        RequestHelper.setPropertyFile("application.properties");
    }

    @Test
    void testNullPropertiesFileError(){
        RequestHelper.setProp(null);
        RequestHelper.setPropertyFile(null);
        assertThrows(IllegalArgumentException.class, RequestHelper::loadProperties);
        RequestHelper.setPropertyFile("application.properties");
    }

    @Test
    void testInvalidClientId() {
        RequestHelper.setProp(getProperties());
        assertThrows(IllegalArgumentException.class, RequestHelper::signRequest);
        RequestHelper.setProp(null);
    }

    @Test
    void testResourceContent(){
        assertThrows(IllegalArgumentException.class, ()-> RequestHelper.resourceContent(null));
    }

    private Properties getProperties(){
        Properties properties = new Properties();
        properties.setProperty("","");
        return properties;
    }

}
