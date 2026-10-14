/*
 * Decompiled with CFR 0.152.
 */
package org.apache.neethi;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public interface PolicyComponent {
    public void serialize(XMLStreamWriter var1) throws XMLStreamException;

    public short getType();

    public boolean equal(PolicyComponent var1);
}

