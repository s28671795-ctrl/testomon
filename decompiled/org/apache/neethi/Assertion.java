/*
 * Decompiled with CFR 0.152.
 */
package org.apache.neethi;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.apache.neethi.PolicyComponent;

public interface Assertion
extends PolicyComponent {
    public QName getName();

    public boolean isOptional();

    public boolean isIgnorable();

    @Override
    public void serialize(XMLStreamWriter var1) throws XMLStreamException;

    public PolicyComponent normalize();
}

