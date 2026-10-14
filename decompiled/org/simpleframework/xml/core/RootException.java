/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import org.simpleframework.xml.core.PersistenceException;

public class RootException
extends PersistenceException {
    public RootException(String text, Object ... list) {
        super(text, list);
    }

    public RootException(Throwable cause, String text, Object ... list) {
        super(cause, text, list);
    }
}

