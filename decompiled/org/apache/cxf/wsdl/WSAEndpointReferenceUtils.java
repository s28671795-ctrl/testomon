/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsdl;

import org.apache.cxf.ws.addressing.AttributedURIType;
import org.apache.cxf.ws.addressing.EndpointReferenceType;
import org.apache.cxf.ws.addressing.MetadataType;
import org.apache.cxf.ws.addressing.ObjectFactory;

public final class WSAEndpointReferenceUtils {
    public static final String ANONYMOUS_ADDRESS = "http://www.w3.org/2005/08/addressing/anonymous";
    static final ObjectFactory WSA_OBJECT_FACTORY = new ObjectFactory();

    private WSAEndpointReferenceUtils() {
    }

    public static EndpointReferenceType createEndpointReferenceWithMetadata() {
        EndpointReferenceType reference = WSA_OBJECT_FACTORY.createEndpointReferenceType();
        reference.setMetadata(WSA_OBJECT_FACTORY.createMetadataType());
        return reference;
    }

    public static MetadataType getSetMetadata(EndpointReferenceType ref) {
        MetadataType mt = ref.getMetadata();
        if (null == mt) {
            mt = WSA_OBJECT_FACTORY.createMetadataType();
            ref.setMetadata(mt);
        }
        return mt;
    }

    public static void setAddress(EndpointReferenceType ref, String address) {
        AttributedURIType a = WSA_OBJECT_FACTORY.createAttributedURIType();
        a.setValue(address);
        ref.setAddress(a);
    }

    public static String getAddress(EndpointReferenceType ref) {
        AttributedURIType a = ref.getAddress();
        if (null != a) {
            return a.getValue();
        }
        return null;
    }

    public static EndpointReferenceType duplicate(EndpointReferenceType ref) {
        EndpointReferenceType reference = WSA_OBJECT_FACTORY.createEndpointReferenceType();
        reference.setMetadata(ref.getMetadata());
        reference.getAny().addAll(ref.getAny());
        reference.setAddress(ref.getAddress());
        return reference;
    }

    public static EndpointReferenceType getEndpointReference(String address) {
        EndpointReferenceType reference = WSA_OBJECT_FACTORY.createEndpointReferenceType();
        WSAEndpointReferenceUtils.setAddress(reference, address);
        return reference;
    }

    public static EndpointReferenceType getEndpointReference(AttributedURIType address) {
        EndpointReferenceType reference = WSA_OBJECT_FACTORY.createEndpointReferenceType();
        reference.setAddress(address);
        return reference;
    }

    public static EndpointReferenceType getAnonymousEndpointReference() {
        EndpointReferenceType reference = WSA_OBJECT_FACTORY.createEndpointReferenceType();
        WSAEndpointReferenceUtils.setAddress(reference, ANONYMOUS_ADDRESS);
        return reference;
    }
}

