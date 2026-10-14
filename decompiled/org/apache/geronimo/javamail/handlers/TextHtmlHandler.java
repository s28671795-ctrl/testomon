/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.handlers;

import javax.activation.ActivationDataFlavor;
import org.apache.geronimo.javamail.handlers.AbstractTextHandler;

public class TextHtmlHandler
extends AbstractTextHandler {
    public TextHtmlHandler() {
        super(new ActivationDataFlavor(String.class, "text/html", "HTML Text"));
    }
}

