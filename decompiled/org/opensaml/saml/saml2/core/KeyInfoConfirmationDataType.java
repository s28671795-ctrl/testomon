/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.saml2.core.SubjectConfirmationData;

public interface KeyInfoConfirmationDataType
extends SubjectConfirmationData {
    public static final String TYPE_LOCAL_NAME = "KeyInfoConfirmationDataType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "KeyInfoConfirmationDataType", "saml2");

    public List<XMLObject> getKeyInfos();
}

