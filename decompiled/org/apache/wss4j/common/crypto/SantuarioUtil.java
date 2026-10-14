/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.crypto;

import java.security.Provider;
import org.apache.jcp.xml.dsig.internal.dom.XMLDSigRI;

public final class SantuarioUtil {
    private SantuarioUtil() {
    }

    public static Provider getSantuarioProvider() {
        return new XMLDSigRI();
    }
}

