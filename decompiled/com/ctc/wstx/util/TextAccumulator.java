/*
 * Decompiled with CFR 0.152.
 */
package com.ctc.wstx.util;

public final class TextAccumulator {
    private String mText = null;
    private StringBuilder mBuilder = null;

    public boolean hasText() {
        return this.mBuilder != null || this.mText != null;
    }

    public void addText(String text) {
        int len = text.length();
        if (len > 0) {
            if (this.mText != null) {
                this.mBuilder = new StringBuilder(this.mText.length() + len);
                this.mBuilder.append(this.mText);
                this.mText = null;
            }
            if (this.mBuilder != null) {
                this.mBuilder.append(text);
            } else {
                this.mText = text;
            }
        }
    }

    public void addText(char[] buf, int start2, int end) {
        int len = end - start2;
        if (len > 0) {
            if (this.mText != null) {
                this.mBuilder = new StringBuilder(this.mText.length() + len);
                this.mBuilder.append(this.mText);
                this.mText = null;
            } else if (this.mBuilder == null) {
                this.mBuilder = new StringBuilder(len);
            }
            this.mBuilder.append(buf, start2, end - start2);
        }
    }

    public String getAndClear() {
        if (this.mText != null) {
            String result = this.mText;
            this.mText = null;
            return result;
        }
        if (this.mBuilder != null) {
            String result = this.mBuilder.toString();
            this.mBuilder = null;
            return result;
        }
        return "";
    }
}

