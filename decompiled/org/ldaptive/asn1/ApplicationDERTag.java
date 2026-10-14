/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.asn1;

import org.ldaptive.asn1.AbstractDERTag;

public class ApplicationDERTag
extends AbstractDERTag {
    public static final String TAG_NAME = "APP";
    public static final int TAG_CLASS = 64;

    public ApplicationDERTag(int number, boolean isConstructed) {
        super(number, isConstructed);
    }

    @Override
    public int getTagByte() {
        return super.getTagByte() | 0x40;
    }

    @Override
    public String name() {
        return TAG_NAME + "(" + this.getTagNo() + ")";
    }

    @Override
    public String toString() {
        return this.name();
    }
}

