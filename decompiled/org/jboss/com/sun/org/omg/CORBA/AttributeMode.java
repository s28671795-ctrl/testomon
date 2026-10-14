/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.org.omg.CORBA;

import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.portable.IDLEntity;

public class AttributeMode
implements IDLEntity {
    private int __value;
    private static int __size = 2;
    private static AttributeMode[] __array = new AttributeMode[__size];
    public static final int _ATTR_NORMAL = 0;
    public static final AttributeMode ATTR_NORMAL = new AttributeMode(0);
    public static final int _ATTR_READONLY = 1;
    public static final AttributeMode ATTR_READONLY = new AttributeMode(1);

    public int value() {
        return this.__value;
    }

    public static AttributeMode from_int(int value) {
        if (value >= 0 && value < __size) {
            return __array[value];
        }
        throw new BAD_PARAM();
    }

    protected AttributeMode(int value) {
        this.__value = value;
        AttributeMode.__array[this.__value] = this;
    }
}

