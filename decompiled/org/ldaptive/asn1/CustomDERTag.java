/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.asn1;

import org.ldaptive.asn1.AbstractDERTag;

public class CustomDERTag
extends AbstractDERTag {
    private final String tagName;

    public CustomDERTag(int number, String name, boolean isConstructed) {
        super(number, isConstructed);
        this.tagName = name;
    }

    @Override
    public String name() {
        return this.tagName;
    }

    @Override
    public String toString() {
        return this.name() + "(" + this.getTagNo() + ")";
    }
}

