/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata;

import javax.xml.namespace.QName;
import org.opensaml.saml.saml2.metadata.IndexedEndpoint;

public interface AssertionConsumerService
extends IndexedEndpoint {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "AssertionConsumerService";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "AssertionConsumerService", "md");
}

