/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.soap;

import java.beans.PropertyEditorSupport;
import org.apache.cxf.binding.soap.Soap11;
import org.apache.cxf.binding.soap.Soap12;
import org.apache.cxf.binding.soap.SoapVersion;

public class SoapVersionEditor
extends PropertyEditorSupport {
    private SoapVersion version;

    @Override
    public Object getValue() {
        return this.version;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if ("1.2".equals(text)) {
            this.version = Soap12.getInstance();
        } else if ("1.1".equals(text)) {
            this.version = Soap11.getInstance();
        } else {
            super.setAsText(text);
        }
    }
}

