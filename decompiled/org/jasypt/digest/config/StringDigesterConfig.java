/*
 * Decompiled with CFR 0.152.
 */
package org.jasypt.digest.config;

import org.jasypt.digest.config.DigesterConfig;

public interface StringDigesterConfig
extends DigesterConfig {
    public Boolean isUnicodeNormalizationIgnored();

    public String getStringOutputType();

    public String getPrefix();

    public String getSuffix();
}

