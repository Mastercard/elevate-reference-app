package com.mastercard.developers.elevate.accelerator.util;

import com.mastercard.developer.encryption.FieldLevelEncryptionConfig;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

class EncryptionConfigUtilTest {

    @Test
    void getEncryptionConfig_Exception_Test() {
        FieldLevelEncryptionConfig fieldLevelEncryptionConfig = EncryptionConfigUtil.getEncryptionConfig(null);
        assertNull(fieldLevelEncryptionConfig);
    }


}
