/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.xml.bind.annotation.adapters.XmlAdapter
 */
package org.jooq.migrations.xml;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.jooq.ContentType;

public class ContentTypeAdapter
extends XmlAdapter<String, ContentType> {
    public ContentType unmarshal(String v) throws Exception {
        return ContentType.valueOf(v);
    }

    public String marshal(ContentType v) throws Exception {
        return v.name();
    }
}

