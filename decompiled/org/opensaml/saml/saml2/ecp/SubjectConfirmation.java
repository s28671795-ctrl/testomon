/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.ecp;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.core.SubjectConfirmationData;
import org.opensaml.soap.soap11.ActorBearing;
import org.opensaml.soap.soap11.MustUnderstandBearing;

public interface SubjectConfirmation
extends SAMLObject,
MustUnderstandBearing,
ActorBearing {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "SubjectConfirmation";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:profiles:SSO:ecp", "SubjectConfirmation", "ecp");
    public static final String TYPE_LOCAL_NAME = "SubjectConfirmationType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:profiles:SSO:ecp", "SubjectConfirmationType", "ecp");
    public static final String METHOD_ATTRIB_NAME = "Method";

    public String getMethod();

    public void setMethod(String var1);

    public SubjectConfirmationData getSubjectConfirmationData();

    public void setSubjectConfirmationData(SubjectConfirmationData var1);
}

