/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.mail.handlers;

import javax.activation.ActivationDataFlavor;
import org.apache.geronimo.mail.handlers.TextHandler;

public class XMLHandler
extends TextHandler {
    public XMLHandler() {
        super(new ActivationDataFlavor(String.class, "text/xml", "XML String"));
    }
}

