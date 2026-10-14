/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.profile;

import java.io.Serializable;
import org.pac4j.core.exception.TechnicalException;

public class Color
implements Serializable {
    private static final long serialVersionUID = -28080878626869621L;
    private int red;
    private int green;
    private int blue;

    public Color(int red, int green, int blue) {
        if (red < 0 || red > 255 || green < 0 || green > 255 || blue < 0 || blue > 255) {
            throw new TechnicalException("Color's red, green or blue values must be between 0 and 255.");
        }
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int getRed() {
        return this.red;
    }

    public int getGreen() {
        return this.green;
    }

    public int getBlue() {
        return this.blue;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public String toString() {
        return this.toPaddedHexString(this.red) + this.toPaddedHexString(this.green) + this.toPaddedHexString(this.blue);
    }

    private String toPaddedHexString(int i) {
        return i < 16 ? "0" + Integer.toHexString(i).toUpperCase() : Integer.toHexString(i).toUpperCase();
    }
}

