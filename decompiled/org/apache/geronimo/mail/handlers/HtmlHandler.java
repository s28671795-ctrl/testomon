/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.mail.handlers;

import javax.activation.ActivationDataFlavor;
import org.apache.geronimo.mail.handlers.TextHandler;

public class HtmlHandler
extends TextHandler {
    public HtmlHandler() {
        super(new ActivationDataFlavor(String.class, "text/html", "HTML String"));
    }
}

