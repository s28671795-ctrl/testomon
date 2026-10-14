/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2cb;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBase64Binary;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.soap.soap11.ActorBearing;
import org.opensaml.soap.soap11.MustUnderstandBearing;

public interface ChannelBindings
extends XSBase64Binary,
MustUnderstandBearing,
ActorBearing,
SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "ChannelBindings";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:protocol:ext:channel-binding", "ChannelBindings", "cb");
    public static final String TYPE_LOCAL_NAME = "ChannelBindingsType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:protocol:ext:channel-binding", "ChannelBindingsType", "cb");
    public static final String TYPE_ATTRIB_NAME = "Type";

    public String getType();

    public void setType(String var1);
}

