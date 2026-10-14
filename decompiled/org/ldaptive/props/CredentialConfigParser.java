/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.props;

import java.util.regex.Matcher;
import org.ldaptive.props.PropertyValueParser;

public class CredentialConfigParser
extends PropertyValueParser {
    protected static final String DEFAULT_CREDENTIAL_CONFIG_CLASS = "org.ldaptive.ssl.X509CredentialConfig";

    public CredentialConfigParser(String config) {
        Matcher credentialOnlyMatcher = CONFIG_PATTERN.matcher(config);
        Matcher paramsOnlyMatcher = PARAMS_ONLY_CONFIG_PATTERN.matcher(config);
        if (credentialOnlyMatcher.matches()) {
            this.initialize(credentialOnlyMatcher.group(1).trim(), credentialOnlyMatcher.group(2).trim());
        } else if (paramsOnlyMatcher.matches()) {
            this.initialize(DEFAULT_CREDENTIAL_CONFIG_CLASS, paramsOnlyMatcher.group(1).trim());
        }
    }

    public static boolean isCredentialConfig(String config) {
        return CredentialConfigParser.isConfig(config) || CredentialConfigParser.isParamsOnlyConfig(config);
    }
}

