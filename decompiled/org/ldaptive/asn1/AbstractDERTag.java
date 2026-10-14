/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.asn1;

import org.ldaptive.asn1.DERTag;

public abstract class AbstractDERTag
implements DERTag {
    private final int tagNo;
    private final boolean constructed;

    public AbstractDERTag(int number, boolean isConstructed) {
        this.tagNo = number;
        this.constructed = isConstructed;
    }

    @Override
    public int getTagNo() {
        return this.tagNo;
    }

    @Override
    public boolean isConstructed() {
        return this.constructed;
    }

    @Override
    public int getTagByte() {
        return this.constructed ? this.tagNo | 0x20 : this.tagNo;
    }

    public String toString() {
        return this.name() + "(" + this.tagNo + ")";
    }
}

