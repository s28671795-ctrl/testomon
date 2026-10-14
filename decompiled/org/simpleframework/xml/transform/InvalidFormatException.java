/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.transform;

import org.simpleframework.xml.transform.TransformException;

public class InvalidFormatException
extends TransformException {
    public InvalidFormatException(String text, Object ... list) {
        super(String.format(text, list), new Object[0]);
    }

    public InvalidFormatException(Throwable cause, String text, Object ... list) {
        super(String.format(text, list), new Object[]{cause});
    }
}

