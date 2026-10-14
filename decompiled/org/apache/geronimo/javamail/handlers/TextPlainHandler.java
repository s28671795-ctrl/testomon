/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.handlers;

import javax.activation.ActivationDataFlavor;
import org.apache.geronimo.javamail.handlers.AbstractTextHandler;

public class TextPlainHandler
extends AbstractTextHandler {
    public TextPlainHandler() {
        super(new ActivationDataFlavor(String.class, "text/plain", "Plain Text"));
    }
}

