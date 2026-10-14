/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.common;

import javax.xml.namespace.QName;
import org.joda.time.DateTime;
import org.opensaml.saml.common.SAMLObject;

public interface TimeBoundSAMLObject
extends SAMLObject {
    public static final String VALID_UNTIL_ATTRIB_NAME = "validUntil";
    public static final QName VALID_UNTIL_ATTRIB_QNAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "validUntil", "md");

    public boolean isValid();

    public DateTime getValidUntil();

    public void setValidUntil(DateTime var1);
}

