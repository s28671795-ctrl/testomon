/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.util;

import java.beans.PropertyEditorSupport;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MemoryIntPropertyEditor
extends PropertyEditorSupport {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        Pattern p = Pattern.compile("^\\s*(\\d+)\\s*(b)?\\s*$", 2);
        Matcher m4 = p.matcher(text);
        if (m4.matches()) {
            this.setValue(Integer.parseInt(m4.group(1)));
            return;
        }
        p = Pattern.compile("^\\s*(\\d+)\\s*k(b)?\\s*$", 2);
        m4 = p.matcher(text);
        if (m4.matches()) {
            this.setValue(Integer.parseInt(m4.group(1)) * 1024);
            return;
        }
        p = Pattern.compile("^\\s*(\\d+)\\s*m(b)?\\s*$", 2);
        m4 = p.matcher(text);
        if (m4.matches()) {
            this.setValue(Integer.parseInt(m4.group(1)) * 1024 * 1024);
            return;
        }
        p = Pattern.compile("^\\s*(\\d+)\\s*g(b)?\\s*$", 2);
        m4 = p.matcher(text);
        if (m4.matches()) {
            this.setValue(Integer.parseInt(m4.group(1)) * 1024 * 1024 * 1024);
            return;
        }
        throw new IllegalArgumentException("Could convert not to a memory size: " + text);
    }

    @Override
    public String getAsText() {
        Integer value = (Integer)this.getValue();
        return value != null ? value.toString() : "";
    }
}

