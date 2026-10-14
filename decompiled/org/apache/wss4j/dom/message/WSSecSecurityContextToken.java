/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.message;

import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.dom.engine.WSSConfig;
import org.apache.wss4j.dom.message.WSSecHeader;
import org.apache.wss4j.dom.message.token.SecurityContextToken;
import org.apache.wss4j.dom.util.WSSecurityUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class WSSecSecurityContextToken {
    private SecurityContextToken sct;
    private String sctId;
    private String identifier;
    private byte[] secret;
    private int wscVersion = 2;
    private WSSConfig wssConfig;
    private final WSSecHeader securityHeader;
    private final Document doc;

    public WSSecSecurityContextToken(WSSecHeader securityHeader, WSSConfig config) {
        this.securityHeader = securityHeader;
        this.doc = securityHeader != null && securityHeader.getSecurityHeaderElement() != null ? securityHeader.getSecurityHeaderElement().getOwnerDocument() : null;
        this.wssConfig = config;
    }

    public WSSecSecurityContextToken(Document doc, WSSConfig config) {
        this.securityHeader = null;
        this.doc = doc;
        this.wssConfig = config;
    }

    public void prepare(Crypto crypto) throws WSSecurityException {
        if (this.sct == null) {
            if (this.identifier != null) {
                this.sct = new SecurityContextToken(this.wscVersion, this.doc, this.identifier);
            } else {
                this.sct = new SecurityContextToken(this.wscVersion, this.doc);
                this.identifier = this.sct.getIdentifier();
            }
        }
        if (this.sctId == null) {
            this.sctId = this.getWsConfig().getIdAllocator().createId("sctId-", this.sct);
        }
        this.sct.setID(this.sctId);
    }

    public void prependSCTElementToHeader() throws WSSecurityException {
        Element secHeaderElement = this.securityHeader.getSecurityHeaderElement();
        WSSecurityUtil.prependChildElement(secHeaderElement, this.sct.getElement());
    }

    public SecurityContextToken getSct() {
        return this.sct;
    }

    public void setSct(SecurityContextToken sct) {
        this.sct = sct;
    }

    public byte[] getSecret() {
        return this.secret;
    }

    protected void setSecret(byte[] ephemeralKey) {
        this.secret = ephemeralKey;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getSctId() {
        if (this.sct != null) {
            return this.sct.getID();
        }
        return this.sctId;
    }

    public void setSctId(String sctId) {
        this.sctId = sctId;
    }

    public void setWscVersion(int wscVersion) {
        this.wscVersion = wscVersion;
    }

    private WSSConfig getWsConfig() {
        if (this.wssConfig == null) {
            this.wssConfig = WSSConfig.getNewInstance();
        }
        return this.wssConfig;
    }
}

