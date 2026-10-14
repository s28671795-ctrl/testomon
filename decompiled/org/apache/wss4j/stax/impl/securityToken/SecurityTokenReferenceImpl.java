/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.securityToken;

import java.security.Key;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Deque;
import java.util.Map;
import org.apache.wss4j.stax.ext.WSInboundSecurityContext;
import org.apache.wss4j.stax.securityToken.SecurityTokenReference;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.impl.securityToken.AbstractInboundSecurityToken;
import org.apache.xml.security.stax.securityToken.InboundSecurityToken;
import org.apache.xml.security.stax.securityToken.SecurityTokenConstants;

public class SecurityTokenReferenceImpl
extends AbstractInboundSecurityToken
implements SecurityTokenReference {
    private final InboundSecurityToken inboundSecurityToken;
    private final Deque<XMLSecEvent> xmlSecEvents;

    public SecurityTokenReferenceImpl(InboundSecurityToken inboundSecurityToken, Deque<XMLSecEvent> xmlSecEvents, WSInboundSecurityContext wsInboundSecurityContext, String id, SecurityTokenConstants.KeyIdentifier keyIdentifier) {
        super(wsInboundSecurityContext, id, keyIdentifier, true);
        this.inboundSecurityToken = inboundSecurityToken;
        this.xmlSecEvents = xmlSecEvents;
    }

    @Override
    public Deque<XMLSecEvent> getXmlSecEvents() {
        return this.xmlSecEvents;
    }

    @Override
    public boolean isAsymmetric() throws XMLSecurityException {
        return this.inboundSecurityToken.isAsymmetric();
    }

    @Override
    public Map<String, Key> getSecretKey() throws XMLSecurityException {
        return this.inboundSecurityToken.getSecretKey();
    }

    @Override
    protected Key getKey(String algorithmURI, XMLSecurityConstants.AlgorithmUsage algorithmUsage, String correlationID) throws XMLSecurityException {
        return this.inboundSecurityToken.getSecretKey(algorithmURI, algorithmUsage, correlationID);
    }

    @Override
    public PublicKey getPublicKey() throws XMLSecurityException {
        return this.inboundSecurityToken.getPublicKey();
    }

    @Override
    protected PublicKey getPubKey(String algorithmURI, XMLSecurityConstants.AlgorithmUsage algorithmUsage, String correlationID) throws XMLSecurityException {
        return this.inboundSecurityToken.getPublicKey(algorithmURI, algorithmUsage, correlationID);
    }

    @Override
    public X509Certificate[] getX509Certificates() throws XMLSecurityException {
        return this.inboundSecurityToken.getX509Certificates();
    }

    @Override
    public void verify() throws XMLSecurityException {
        this.inboundSecurityToken.verify();
    }

    @Override
    public InboundSecurityToken getKeyWrappingToken() throws XMLSecurityException {
        return (InboundSecurityToken)this.inboundSecurityToken.getKeyWrappingToken();
    }

    @Override
    public boolean isIncludedInMessage() {
        return this.inboundSecurityToken.isIncludedInMessage();
    }

    @Override
    public SecurityTokenConstants.TokenType getTokenType() {
        return this.inboundSecurityToken.getTokenType();
    }
}

