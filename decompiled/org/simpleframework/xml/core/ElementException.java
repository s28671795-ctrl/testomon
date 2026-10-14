/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import org.simpleframework.xml.core.PersistenceException;

public class ElementException
extends PersistenceException {
    public ElementException(String text, Object ... list) {
        super(text, list);
    }

    public ElementException(Throwable cause, String text, Object ... list) {
        super(cause, text, list);
    }
}

