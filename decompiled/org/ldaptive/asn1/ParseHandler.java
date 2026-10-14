/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.asn1;

import org.ldaptive.asn1.DERBuffer;
import org.ldaptive.asn1.DERParser;

public interface ParseHandler {
    public void handle(DERParser var1, DERBuffer var2);
}

