/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import org.simpleframework.xml.core.PersistenceException;

public class MethodException
extends PersistenceException {
    public MethodException(String text, Object ... list) {
        super(text, list);
    }

    public MethodException(Throwable cause, String text, Object ... list) {
        super(cause, text, list);
    }
}

