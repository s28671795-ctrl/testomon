/*
 * Decompiled with CFR 0.152.
 */
package org.w3._2004._08.xop.include;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.w3._2004._08.xop.include.Include;

@XmlRegistry
public class ObjectFactory {
    private static final QName _Include_QNAME = new QName("http://www.w3.org/2004/08/xop/include", "Include");

    public Include createInclude() {
        return new Include();
    }

    @XmlElementDecl(namespace="http://www.w3.org/2004/08/xop/include", name="Include")
    public JAXBElement<Include> createInclude(Include value) {
        return new JAXBElement<Include>(_Include_QNAME, Include.class, null, value);
    }
}

