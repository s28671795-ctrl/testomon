/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.convert;

public class ConvertException
extends Exception {
    public ConvertException(String text, Object ... list) {
        super(String.format(text, list));
    }
}

