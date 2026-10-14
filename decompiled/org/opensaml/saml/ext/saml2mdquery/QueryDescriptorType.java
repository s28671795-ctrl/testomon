/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdquery;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.saml.saml2.metadata.NameIDFormat;
import org.opensaml.saml.saml2.metadata.RoleDescriptor;

public interface QueryDescriptorType
extends RoleDescriptor {
    public static final String TYPE_LOCAL_NAME = "QueryDescriptorType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:metadata:ext:query", "QueryDescriptorType", "query");
    public static final String WANT_ASSERTIONS_SIGNED_ATTRIB_NAME = "WantAssertionsSigned";

    public Boolean getWantAssertionsSigned();

    public XSBooleanValue getWantAssertionsSignedXSBoolean();

    public void setWantAssertionsSigned(Boolean var1);

    public void setWantAssertionsSigned(XSBooleanValue var1);

    public List<NameIDFormat> getNameIDFormat();
}

