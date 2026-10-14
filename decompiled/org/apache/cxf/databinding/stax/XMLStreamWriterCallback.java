/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.databinding.stax;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.apache.cxf.interceptor.Fault;

public interface XMLStreamWriterCallback {
    public void write(XMLStreamWriter var1) throws Fault, XMLStreamException;
}

