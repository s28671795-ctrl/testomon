/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.util;

import java.beans.PropertyEditorSupport;

public class BooleanEditor
extends PropertyEditorSupport {
    @Override
    public String getJavaInitializationString() {
        return String.valueOf((Boolean)this.getValue());
    }

    @Override
    public String getAsText() {
        return this.getJavaInitializationString();
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text.toLowerCase().equals("true")) {
            this.setValue(Boolean.TRUE);
        } else if (text.toLowerCase().equals("false")) {
            this.setValue(Boolean.FALSE);
        } else {
            throw new IllegalArgumentException(text);
        }
    }

    @Override
    public String[] getTags() {
        String[] result = new String[]{"true", "false"};
        return result;
    }
}

