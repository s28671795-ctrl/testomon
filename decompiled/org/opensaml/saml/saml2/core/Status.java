/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.core.StatusCode;
import org.opensaml.saml.saml2.core.StatusDetail;
import org.opensaml.saml.saml2.core.StatusMessage;

public interface Status
extends SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Status";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "Status", "saml2p");
    public static final String TYPE_LOCAL_NAME = "StatusType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "StatusType", "saml2p");

    public StatusCode getStatusCode();

    public void setStatusCode(StatusCode var1);

    public StatusMessage getStatusMessage();

    public void setStatusMessage(StatusMessage var1);

    public StatusDetail getStatusDetail();

    public void setStatusDetail(StatusDetail var1);
}

