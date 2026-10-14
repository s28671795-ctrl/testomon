/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.ctx;

import javax.xml.namespace.QName;
import org.opensaml.xacml.XACMLObject;
import org.opensaml.xacml.ctx.StatusCodeType;
import org.opensaml.xacml.ctx.StatusDetailType;
import org.opensaml.xacml.ctx.StatusMessageType;

public interface StatusType
extends XACMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Status";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:context:schema:os", "Status", "xacml-context");
    public static final String TYPE_LOCAL_NAME = "StatusType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:context:schema:os", "StatusType", "xacml-context");

    public StatusCodeType getStatusCode();

    public StatusDetailType getStatusDetail();

    public StatusMessageType getStatusMessage();

    public void setStatusCode(StatusCodeType var1);

    public void setStatusDetail(StatusDetailType var1);

    public void setStatusMessage(StatusMessageType var1);
}

