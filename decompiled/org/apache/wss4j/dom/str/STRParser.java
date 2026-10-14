/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.str;

import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.dom.str.STRParserParameters;
import org.apache.wss4j.dom.str.STRParserResult;

public interface STRParser {
    public STRParserResult parseSecurityTokenReference(STRParserParameters var1) throws WSSecurityException;

    public static enum REFERENCE_TYPE {
        ISSUER_SERIAL,
        THUMBPRINT_SHA1,
        KEY_IDENTIFIER,
        DIRECT_REF;

    }
}

