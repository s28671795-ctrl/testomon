/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.extended;

import org.ldaptive.LdapUtils;
import org.ldaptive.extended.ExtendedResponse;

public final class WhoAmIResponseParser {
    private WhoAmIResponseParser() {
    }

    public static String parse(ExtendedResponse response) {
        return LdapUtils.utf8Encode(response.getResponseValue(), false);
    }
}

