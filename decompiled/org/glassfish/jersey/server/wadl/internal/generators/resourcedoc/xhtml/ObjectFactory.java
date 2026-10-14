/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.wadl.internal.generators.resourcedoc.xhtml;

import javax.xml.bind.annotation.XmlRegistry;
import org.glassfish.jersey.server.wadl.internal.generators.resourcedoc.xhtml.XhtmlElementType;
import org.glassfish.jersey.server.wadl.internal.generators.resourcedoc.xhtml.XhtmlValueType;

@XmlRegistry
public class ObjectFactory {
    public XhtmlElementType createXhtmlElementType() {
        return new XhtmlElementType();
    }

    public XhtmlValueType createXhtmlCodeType() {
        return new XhtmlValueType();
    }
}

