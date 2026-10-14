/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.control;

import org.ldaptive.asn1.DERBuffer;
import org.ldaptive.control.Control;

public interface ResponseControl
extends Control {
    public void decode(DERBuffer var1);
}

