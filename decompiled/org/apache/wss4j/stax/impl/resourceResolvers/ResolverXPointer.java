/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.resourceResolvers;

import javax.xml.namespace.QName;
import javax.xml.stream.events.Attribute;
import org.apache.wss4j.stax.ext.WSSConstants;
import org.apache.xml.security.stax.ext.ResourceResolver;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.ext.stax.XMLSecStartElement;

public class ResolverXPointer
extends org.apache.xml.security.stax.impl.resourceResolvers.ResolverXPointer {
    public ResolverXPointer() {
    }

    public ResolverXPointer(String uri) {
        super(uri);
    }

    @Override
    public ResourceResolver newInstance(String uri, String baseURI) {
        return new ResolverXPointer(uri);
    }

    @Override
    public boolean matches(XMLSecStartElement xmlSecStartElement) {
        return this.matches(xmlSecStartElement, XMLSecurityConstants.ATT_NULL_Id);
    }

    @Override
    public boolean matches(XMLSecStartElement xmlSecStartElement, QName idAttributeNS) {
        if (this.getId() == null) {
            if (!this.isRootNodeOccured()) {
                this.setRootNodeOccured(true);
                return true;
            }
            return false;
        }
        Attribute attribute = xmlSecStartElement.getAttributeByName(WSSConstants.ATT_WSU_ID);
        if (attribute != null && attribute.getValue().equals(this.getId())) {
            return true;
        }
        return super.matches(xmlSecStartElement, idAttributeNS);
    }
}

