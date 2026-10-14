/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.xml.DOMTypeSupport;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.XMLObjectBuilder;
import org.w3c.dom.Element;

public abstract class AbstractXMLObjectBuilder<XMLObjectType extends XMLObject>
implements XMLObjectBuilder<XMLObjectType> {
    @Override
    @Nonnull
    public XMLObjectType buildObject(@Nonnull QName objectName) {
        return this.buildObject(objectName.getNamespaceURI(), objectName.getLocalPart(), objectName.getPrefix());
    }

    @Override
    @Nonnull
    public XMLObjectType buildObject(@Nonnull QName objectName, @Nullable QName schemaType) {
        return this.buildObject(objectName.getNamespaceURI(), objectName.getLocalPart(), objectName.getPrefix(), schemaType);
    }

    @Override
    @Nonnull
    public abstract XMLObjectType buildObject(@Nullable String var1, @Nonnull @NotEmpty String var2, @Nullable String var3);

    @Override
    @Nonnull
    public XMLObjectType buildObject(@Nullable String namespaceURI, @Nonnull String localName, @Nullable String namespacePrefix, @Nullable QName schemaType) {
        XMLObjectType xmlObject = this.buildObject(namespaceURI, localName, namespacePrefix);
        ((AbstractXMLObject)xmlObject).setSchemaType(schemaType);
        return xmlObject;
    }

    @Override
    @Nonnull
    public XMLObjectType buildObject(@Nonnull Element element) {
        String localName = element.getLocalName();
        String nsURI = element.getNamespaceURI();
        String nsPrefix = element.getPrefix();
        QName schemaType = DOMTypeSupport.getXSIType(element);
        XMLObjectType xmlObject = this.buildObject(nsURI, localName, nsPrefix, schemaType);
        return xmlObject;
    }
}

