/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdquery;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.ext.saml2mdquery.ActionNamespace;
import org.opensaml.saml.ext.saml2mdquery.QueryDescriptorType;

public interface AuthzDecisionQueryDescriptorType
extends QueryDescriptorType {
    public static final String TYPE_LOCAL_NAME = "AuthzDecisionQueryDescriptorType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:metadata:ext:query", "AuthzDecisionQueryDescriptorType", "query");

    public List<ActionNamespace> getActionNamespaces();
}

