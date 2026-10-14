/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdquery;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSURI;
import org.opensaml.saml.common.SAMLObject;

public interface ActionNamespace
extends XSURI,
SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "ActionNamespace";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:metadata:ext:query", "ActionNamespace", "query");
}

