package com.mastercard.developers.elevate.accelerator.util;

import com.mastercard.developer.encryption.FieldLevelEncryptionConfig;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.assertNull;

public class EncryptionConfigUtilTest {

    @InjectMocks
    EncryptionConfigUtil encryptionConfigUtil;

    @Test
    void getEncryptionConfig_Exception_Test() {
        FieldLevelEncryptionConfig fieldLevelEncryptionConfig = encryptionConfigUtil.getEncryptionConfig(null);
        assertNull(fieldLevelEncryptionConfig);
    }


}
