/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.control;

import org.ldaptive.control.Control;

public interface RequestControl
extends Control {
    public byte[] encode();

    public boolean hasValue();
}

