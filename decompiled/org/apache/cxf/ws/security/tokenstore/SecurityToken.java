/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.tokenstore;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StringReader;
import java.security.Key;
import java.security.Principal;
import java.security.cert.X509Certificate;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.Map;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.security.SecurityContext;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.cxf.staxutils.W3CDOMStreamWriter;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.token.Reference;
import org.apache.wss4j.common.util.DOM2Writer;
import org.apache.wss4j.common.util.XMLUtils;
import org.w3c.dom.Element;

public class SecurityToken
implements Serializable {
    public static final String BOOTSTRAP_TOKEN_ID = "bootstrap_security_token_id";
    private static final long serialVersionUID = -8220267049304000696L;
    private String id;
    private String wsuId;
    private transient Element token;
    private String tokenStr;
    private transient Element attachedReference;
    private transient Element unattachedReference;
    private Map<String, Object> properties;
    private transient byte[] secret;
    private transient Key key;
    private Instant created;
    private Instant expires;
    private String issuerAddress;
    private String encrKeySha1Value;
    private int tokenHash;
    private String transformedTokenIdentifier;
    private String tokenType;
    private X509Certificate x509cert;
    private transient Crypto crypto;
    private Principal principal;
    private transient SecurityContext securityContext;

    public SecurityToken() {
    }

    public SecurityToken(String id) {
        this.id = XMLUtils.getIDFromReference(id);
    }

    public SecurityToken(String id, Instant created, Instant expires) {
        this.id = XMLUtils.getIDFromReference(id);
        this.created = created;
        this.expires = expires;
    }

    public SecurityToken(String id, Element tokenElem, Instant created, Instant expires) {
        this.id = XMLUtils.getIDFromReference(id);
        this.token = SecurityToken.cloneElement(tokenElem);
        this.created = created;
        this.expires = expires;
    }

    public SecurityToken(String id, Element tokenElem, Element lifetimeElem) {
        this.id = XMLUtils.getIDFromReference(id);
        this.token = SecurityToken.cloneElement(tokenElem);
        if (lifetimeElem != null) {
            this.processLifeTime(lifetimeElem);
        }
    }

    private static Element cloneElement(Element el) {
        try {
            W3CDOMStreamWriter writer = new W3CDOMStreamWriter();
            writer.setNsRepairing(true);
            StaxUtils.copy(el, (XMLStreamWriter)writer);
            return writer.getDocument().getDocumentElement();
        }
        catch (Exception exception) {
            return el;
        }
    }

    private void processLifeTime(Element lifetimeElem) {
        try {
            Element createdElem = DOMUtils.getFirstChildWithName(lifetimeElem, "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Created");
            this.created = createdElem == null ? Instant.now() : ZonedDateTime.parse(DOMUtils.getContent(createdElem)).toInstant();
            Element expiresElem = DOMUtils.getFirstChildWithName(lifetimeElem, "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Expires");
            if (expiresElem != null) {
                this.expires = ZonedDateTime.parse(DOMUtils.getContent(expiresElem)).toInstant();
            }
        }
        catch (DateTimeParseException dateTimeParseException) {
            // empty catch block
        }
    }

    public Map<String, Object> getProperties() {
        return this.properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public Element getToken() {
        return this.token;
    }

    public void setToken(Element token) {
        if (token != null) {
            this.token = SecurityToken.cloneElement(token);
        }
    }

    public String getTransformedTokenIdentifier() {
        return this.transformedTokenIdentifier;
    }

    public void setTransformedTokenIdentifier(String transformedTokenIdentifier) {
        this.transformedTokenIdentifier = transformedTokenIdentifier;
    }

    public void setId(String id) {
        this.id = XMLUtils.getIDFromReference(id);
    }

    public String getId() {
        return this.id;
    }

    public byte[] getSecret() {
        return this.secret;
    }

    public void setSecret(byte[] secret) {
        this.secret = secret;
    }

    public Element getAttachedReference() {
        return this.attachedReference;
    }

    public void setAttachedReference(Element attachedReference) {
        if (attachedReference != null) {
            this.attachedReference = SecurityToken.cloneElement(attachedReference);
        }
    }

    public Element getUnattachedReference() {
        return this.unattachedReference;
    }

    public void setUnattachedReference(Element unattachedReference) {
        if (unattachedReference != null) {
            this.unattachedReference = SecurityToken.cloneElement(unattachedReference);
        }
    }

    public Instant getCreated() {
        return this.created;
    }

    public Instant getExpires() {
        return this.expires;
    }

    public boolean isExpired() {
        Instant now;
        return this.expires != null && this.expires.isBefore(now = Instant.now());
    }

    public boolean isAboutToExpire(long secondsToExpiry) {
        Instant now;
        return this.expires != null && secondsToExpiry > 0L && this.expires.isBefore(now = Instant.now().plusSeconds(secondsToExpiry));
    }

    public void setExpires(Instant expires) {
        this.expires = expires;
    }

    public String getIssuerAddress() {
        return this.issuerAddress;
    }

    public void setIssuerAddress(String issuerAddress) {
        this.issuerAddress = issuerAddress;
    }

    public void setSHA1(String sha) {
        this.encrKeySha1Value = sha;
    }

    public String getSHA1() {
        return this.encrKeySha1Value;
    }

    public String getTokenType() {
        return this.tokenType;
    }

    public void setTokenType(String s2) {
        this.tokenType = s2;
    }

    public void setWsuId(String wsuId) {
        this.wsuId = wsuId;
    }

    public String getWsuId() {
        String t2;
        if (this.wsuId != null) {
            return this.wsuId;
        }
        Element elem = this.getAttachedReference();
        if (elem != null && (t2 = SecurityToken.getIdFromSTR(elem)) != null) {
            return t2;
        }
        elem = this.getUnattachedReference();
        if (elem != null && (t2 = SecurityToken.getIdFromSTR(elem)) != null) {
            return t2;
        }
        return null;
    }

    public static String getIdFromSTR(Element str) {
        Element child = DOMUtils.getFirstElement(str);
        if (child == null) {
            return null;
        }
        if ("KeyInfo".equals(child.getLocalName()) && "http://www.w3.org/2000/09/xmldsig#".equals(child.getNamespaceURI())) {
            return DOMUtils.getContent(child);
        }
        if (Reference.TOKEN.getLocalPart().equals(child.getLocalName()) && Reference.TOKEN.getNamespaceURI().equals(child.getNamespaceURI())) {
            return child.getAttributeNS(null, "URI").substring(1);
        }
        return null;
    }

    public void setX509Certificate(X509Certificate cert, Crypto cpt) {
        this.x509cert = cert;
        this.crypto = cpt;
    }

    public X509Certificate getX509Certificate() {
        return this.x509cert;
    }

    public Crypto getCrypto() {
        return this.crypto;
    }

    public void setTokenHash(int hash) {
        this.tokenHash = hash;
    }

    public int getTokenHash() {
        return this.tokenHash;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }

    public Principal getPrincipal() {
        return this.principal;
    }

    public void setSecurityContext(SecurityContext securityContext) {
        this.securityContext = securityContext;
    }

    public SecurityContext getSecurityContext() {
        return this.securityContext;
    }

    public Key getKey() {
        return this.key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
        if (this.token != null && this.tokenStr == null) {
            this.tokenStr = DOM2Writer.nodeToString(this.token);
        }
        stream.defaultWriteObject();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException, XMLStreamException {
        in.defaultReadObject();
        if (this.token == null && this.tokenStr != null) {
            this.token = StaxUtils.read(new StringReader(this.tokenStr)).getDocumentElement();
        }
    }
}

