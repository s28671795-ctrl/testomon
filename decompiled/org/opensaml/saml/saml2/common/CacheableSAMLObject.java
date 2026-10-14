/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.common;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface CacheableSAMLObject
extends SAMLObject {
    public static final String CACHE_DURATION_ATTRIB_NAME = "cacheDuration";
    public static final QName CACHE_DURATION_ATTRIB_QNAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "cacheDuration", "md");

    public Long getCacheDuration();

    public void setCacheDuration(Long var1);
}

