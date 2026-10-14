/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdquery;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.ext.saml2mdquery.QueryDescriptorType;
import org.opensaml.saml.saml2.metadata.AttributeConsumingService;

public interface AttributeQueryDescriptorType
extends QueryDescriptorType {
    public static final String TYPE_LOCAL_NAME = "AttributeQueryDescriptorType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:metadata:ext:query", "AttributeQueryDescriptorType", "query");

    public List<AttributeConsumingService> getAttributeConsumingServices();
}

