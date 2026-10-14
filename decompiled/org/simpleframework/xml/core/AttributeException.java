/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import org.simpleframework.xml.core.PersistenceException;

public class AttributeException
extends PersistenceException {
    public AttributeException(String text, Object ... list) {
        super(text, list);
    }

    public AttributeException(Throwable cause, String text, Object ... list) {
        super(cause, text, list);
    }
}

