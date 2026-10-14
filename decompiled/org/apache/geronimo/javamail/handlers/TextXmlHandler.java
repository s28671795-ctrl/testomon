/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.handlers;

import javax.activation.ActivationDataFlavor;
import org.apache.geronimo.javamail.handlers.AbstractTextHandler;

public class TextXmlHandler
extends AbstractTextHandler {
    public TextXmlHandler() {
        super(new ActivationDataFlavor(String.class, "text/xml", "XML Text"));
    }
}

