/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.saml1.core.Assertion;
import org.opensaml.saml.saml1.core.ResponseAbstractType;
import org.opensaml.saml.saml1.core.Status;

public interface Response
extends ResponseAbstractType {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Response";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "Response", "saml1p");
    public static final String TYPE_LOCAL_NAME = "ResponseAbstractType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "ResponseAbstractType", "saml1p");

    public Status getStatus();

    public void setStatus(Status var1);

    public List<Assertion> getAssertions();
}

