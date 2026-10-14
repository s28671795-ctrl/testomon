/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.jaxb;

import java.util.Collection;
import javax.xml.namespace.QName;

public interface JAXBBeanInfo {
    public boolean isElement();

    public Collection<QName> getTypeNames();

    public String getElementNamespaceURI(Object var1);

    public String getElementLocalName(Object var1);
}

