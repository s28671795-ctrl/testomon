/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import org.simpleframework.xml.core.PersistenceException;

public class InstantiationException
extends PersistenceException {
    public InstantiationException(String text, Object ... list) {
        super(text, list);
    }

    public InstantiationException(Throwable cause, String text, Object ... list) {
        super(cause, text, list);
    }
}

