/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.asn1;

import org.ldaptive.asn1.AbstractDERTag;

public class ContextDERTag
extends AbstractDERTag {
    public static final String TAG_NAME = "CTX";
    public static final int TAG_CLASS = 128;

    public ContextDERTag(int number, boolean isConstructed) {
        super(number, isConstructed);
    }

    @Override
    public int getTagByte() {
        return super.getTagByte() | 0x80;
    }

    @Override
    public String name() {
        return TAG_NAME + "(" + this.getTagNo() + ")";
    }
}

