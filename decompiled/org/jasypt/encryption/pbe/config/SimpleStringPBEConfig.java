/*
 * Decompiled with CFR 0.152.
 */
package org.jasypt.encryption.pbe.config;

import org.jasypt.commons.CommonUtils;
import org.jasypt.encryption.pbe.config.SimplePBEConfig;
import org.jasypt.encryption.pbe.config.StringPBEConfig;

public class SimpleStringPBEConfig
extends SimplePBEConfig
implements StringPBEConfig {
    private String stringOutputType = null;

    public void setStringOutputType(String stringOutputType) {
        this.stringOutputType = CommonUtils.getStandardStringOutputType(stringOutputType);
    }

    @Override
    public String getStringOutputType() {
        return this.stringOutputType;
    }
}

