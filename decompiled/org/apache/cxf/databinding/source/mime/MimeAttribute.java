/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.databinding.source.mime;

import javax.xml.namespace.QName;

public final class MimeAttribute {
    public static final QName MIME_QNAME = new QName("http://www.w3.org/2005/05/xmlmime", "expectedContentTypes");
    private String value;

    public void setValue(String v) {
        this.value = v;
    }

    public String getValue() {
        return this.value;
    }
}

