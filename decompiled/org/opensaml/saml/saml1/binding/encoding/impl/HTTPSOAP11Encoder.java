/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.soap.soap11.encoder.http.impl.HTTPSOAP11Encoder
 */
package org.opensaml.saml.saml1.binding.encoding.impl;

import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.common.binding.encoding.SAMLMessageEncoder;

public class HTTPSOAP11Encoder
extends org.opensaml.soap.soap11.encoder.http.impl.HTTPSOAP11Encoder<SAMLObject>
implements SAMLMessageEncoder {
    protected String getSOAPAction() {
        return "http://www.oasis-open.org/committees/security";
    }

    @Override
    public String getBindingURI() {
        return "urn:oasis:names:tc:SAML:1.0:bindings:SOAP-binding";
    }
}

