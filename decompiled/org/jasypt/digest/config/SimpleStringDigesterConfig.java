/*
 * Decompiled with CFR 0.152.
 */
package org.jasypt.digest.config;

import org.jasypt.commons.CommonUtils;
import org.jasypt.digest.config.SimpleDigesterConfig;
import org.jasypt.digest.config.StringDigesterConfig;

public class SimpleStringDigesterConfig
extends SimpleDigesterConfig
implements StringDigesterConfig {
    private Boolean unicodeNormalizationIgnored = null;
    private String stringOutputType = null;
    private String prefix = null;
    private String suffix = null;

    public void setUnicodeNormalizationIgnored(Boolean unicodeNormalizationIgnored) {
        this.unicodeNormalizationIgnored = unicodeNormalizationIgnored;
    }

    public void setUnicodeNormalizationIgnored(String unicodeNormalizationIgnored) {
        this.unicodeNormalizationIgnored = unicodeNormalizationIgnored != null ? CommonUtils.getStandardBooleanValue(unicodeNormalizationIgnored) : null;
    }

    public void setStringOutputType(String stringOutputType) {
        this.stringOutputType = CommonUtils.getStandardStringOutputType(stringOutputType);
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    @Override
    public Boolean isUnicodeNormalizationIgnored() {
        return this.unicodeNormalizationIgnored;
    }

    @Override
    public String getStringOutputType() {
        return this.stringOutputType;
    }

    @Override
    public String getPrefix() {
        return this.prefix;
    }

    @Override
    public String getSuffix() {
        return this.suffix;
    }
}

