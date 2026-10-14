/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import org.simpleframework.xml.core.PersistenceException;

public class TextException
extends PersistenceException {
    public TextException(String text, Object ... list) {
        super(text, list);
    }

    public TextException(Throwable cause, String text, Object ... list) {
        super(cause, text, list);
    }
}

