/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.metadata.RequestedAttribute;
import org.opensaml.saml.saml2.metadata.ServiceDescription;
import org.opensaml.saml.saml2.metadata.ServiceName;

public interface AttributeConsumingService
extends SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "AttributeConsumingService";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "AttributeConsumingService", "md");
    public static final String TYPE_LOCAL_NAME = "AttributeConsumingServiceType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "AttributeConsumingServiceType", "md");
    public static final String INDEX_ATTRIB_NAME = "index";
    public static final String IS_DEFAULT_ATTRIB_NAME = "isDefault";

    public int getIndex();

    public void setIndex(int var1);

    public Boolean isDefault();

    public XSBooleanValue isDefaultXSBoolean();

    public void setIsDefault(Boolean var1);

    public void setIsDefault(XSBooleanValue var1);

    public List<ServiceName> getNames();

    public List<ServiceDescription> getDescriptions();

    public List<RequestedAttribute> getRequestAttributes();
}

