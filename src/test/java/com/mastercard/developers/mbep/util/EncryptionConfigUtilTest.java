package com.mastercard.developers.mbep.util;

import com.mastercard.developer.encryption.FieldLevelEncryptionConfig;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import java.util.Properties;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class EncryptionConfigUtilTest {

    @InjectMocks
    EncryptionConfigUtil encryptionConfigUtil;

    @Test
    void getEncryptionConfig_Exception_Test() {
        FieldLevelEncryptionConfig fieldLevelEncryptionConfig = encryptionConfigUtil.getEncryptionConfig(null);
        assertNull(fieldLevelEncryptionConfig);
    }

    @Test
    void getEncryptionConfig_Positive_Test() {
        Properties properties = new Properties();
        properties.setProperty("mastercard.mbep.client.api.base.path","test1");
        properties.setProperty("mastercard.mbep.client.p12.path","test2");
        properties.setProperty("mastercard.mbep.client.ref.app.consumer.key","test3");
        properties.setProperty("mastercard.mbep.client.ref.app.keystore.alias","test4");
        properties.setProperty("mastercard.mbep.client.ref.app.keystore.password","test5");
        properties.setProperty("mastercard.mbep.client.ref.app.encryption.file","src/test/resources/test.pem");
        FieldLevelEncryptionConfig fieldLevelEncryptionConfig = encryptionConfigUtil.getEncryptionConfig(properties);
        assertNotNull(fieldLevelEncryptionConfig);
        assertNotNull(fieldLevelEncryptionConfig.getEncryptionCertificateFingerprint());
    }

}
