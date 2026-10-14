/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsdl;

import org.apache.cxf.wsdl.TExtensibilityElementImpl;

public class JAXBExtensibilityElement
extends TExtensibilityElementImpl {
    Object value;

    JAXBExtensibilityElement(Object o) {
        this.value = o;
    }

    public Object getValue() {
        return this.value;
    }
}

