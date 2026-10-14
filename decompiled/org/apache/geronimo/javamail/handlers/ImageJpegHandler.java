/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.handlers;

import java.awt.Image;
import javax.activation.ActivationDataFlavor;
import org.apache.geronimo.javamail.handlers.AbstractImageHandler;

public class ImageJpegHandler
extends AbstractImageHandler {
    public ImageJpegHandler() {
        super(new ActivationDataFlavor(Image.class, "image/jpeg", "JPEG Image"));
    }
}

