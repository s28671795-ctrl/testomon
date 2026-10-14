/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.model.stream;

public final class Resolution {
    public static final Resolution ZERO = new Resolution(0, 0);
    private final int width;
    private final int height;

    public Resolution(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Resolution)) {
            return false;
        }
        Resolution other = (Resolution)o;
        if (this.getWidth() != other.getWidth()) {
            return false;
        }
        return this.getHeight() == other.getHeight();
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getWidth();
        result = result * 59 + this.getHeight();
        return result;
    }

    public String toString() {
        return "Resolution(width=" + this.getWidth() + ", height=" + this.getHeight() + ")";
    }
}

