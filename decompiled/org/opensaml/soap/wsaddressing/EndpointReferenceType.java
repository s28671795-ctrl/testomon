/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wsaddressing;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.ElementExtensibleXMLObject;
import org.opensaml.soap.wsaddressing.Address;
import org.opensaml.soap.wsaddressing.Metadata;
import org.opensaml.soap.wsaddressing.ReferenceParameters;
import org.opensaml.soap.wsaddressing.WSAddressingObject;

public interface EndpointReferenceType
extends AttributeExtensibleXMLObject,
ElementExtensibleXMLObject,
WSAddressingObject {
    public static final String TYPE_LOCAL_NAME = "EndpointReferenceType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2005/08/addressing", "EndpointReferenceType", "wsa");

    public Address getAddress();

    public void setAddress(Address var1);

    public Metadata getMetadata();

    public void setMetadata(Metadata var1);

    public ReferenceParameters getReferenceParameters();

    public void setReferenceParameters(ReferenceParameters var1);
}

