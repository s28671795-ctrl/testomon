/*
 * Decompiled with CFR 0.152.
 */
package org.jasypt.encryption.pbe.config;

import org.jasypt.commons.CommonUtils;
import org.jasypt.encryption.pbe.config.StringPBEConfig;
import org.jasypt.encryption.pbe.config.WebPBEConfig;

public class WebStringPBEConfig
extends WebPBEConfig
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

