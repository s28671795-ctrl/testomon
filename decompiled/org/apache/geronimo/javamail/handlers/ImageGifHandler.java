/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.handlers;

import java.awt.Image;
import javax.activation.ActivationDataFlavor;
import org.apache.geronimo.javamail.handlers.AbstractImageHandler;

public class ImageGifHandler
extends AbstractImageHandler {
    public ImageGifHandler() {
        super(new ActivationDataFlavor(Image.class, "image/gif", "GIF Image"));
    }
}

