/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.xml.bind.JAXB
 */
package org.jooq.impl;

import jakarta.xml.bind.JAXB;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import org.jooq.XML;
import org.jooq.impl.AbstractConverter;

public class XMLtoJAXBConverter<U>
extends AbstractConverter<XML, U> {
    public XMLtoJAXBConverter(Class<U> toType) {
        super(XML.class, toType);
    }

    @Override
    public U from(XML t2) {
        if (t2 == null) {
            return null;
        }
        return (U)JAXB.unmarshal((Reader)new StringReader(t2.data()), this.toType());
    }

    @Override
    public XML to(U u) {
        if (u == null) {
            return null;
        }
        StringWriter w = new StringWriter();
        JAXB.marshal(u, (Writer)w);
        return XML.xml(w.toString());
    }
}

