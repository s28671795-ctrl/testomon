/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.message;

import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.util.UsernameTokenUtil;
import org.apache.wss4j.common.util.WSCurrentTimeSource;
import org.apache.wss4j.common.util.WSTimeSource;
import org.apache.wss4j.dom.message.WSSecBase;
import org.apache.wss4j.dom.message.WSSecHeader;
import org.apache.wss4j.dom.message.token.UsernameToken;
import org.apache.wss4j.dom.util.WSSecurityUtil;
import org.apache.xml.security.utils.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class WSSecUsernameToken
extends WSSecBase {
    private static final Logger LOG = LoggerFactory.getLogger(WSSecUsernameToken.class);
    private String passwordType = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest";
    private UsernameToken ut;
    private boolean nonce;
    private boolean created;
    private boolean useDerivedKey;
    private boolean useMac;
    private byte[] saltValue;
    private int iteration = 1000;
    private boolean passwordsAreEncoded;
    private boolean precisionInMilliSeconds = true;
    private WSTimeSource wsTimeSource = new WSCurrentTimeSource();

    public WSSecUsernameToken(WSSecHeader securityHeader) {
        super(securityHeader);
    }

    public WSSecUsernameToken(Document doc) {
        super(doc);
    }

    public void setPasswordType(String pwType) {
        this.passwordType = pwType;
    }

    public void addNonce() {
        this.nonce = true;
    }

    public void addCreated() {
        this.created = true;
    }

    public void addDerivedKey(boolean useMac, byte[] saltValue, int iteration) {
        this.passwordType = null;
        this.useDerivedKey = true;
        this.useMac = useMac;
        this.saltValue = saltValue;
        if (iteration > 0) {
            this.iteration = iteration;
        }
    }

    public byte[] getDerivedKey() throws WSSecurityException {
        if (this.ut == null || !this.useDerivedKey) {
            return null;
        }
        if (this.passwordsAreEncoded) {
            return UsernameTokenUtil.generateDerivedKey(XMLUtils.decode(this.password), this.saltValue, this.iteration);
        }
        return UsernameTokenUtil.generateDerivedKey(this.password, this.saltValue, this.iteration);
    }

    public void setPasswordsAreEncoded(boolean passwordsAreEncoded) {
        this.passwordsAreEncoded = passwordsAreEncoded;
    }

    public boolean getPasswordsAreEncoded() {
        return this.passwordsAreEncoded;
    }

    public String getId() {
        if (this.ut == null) {
            return null;
        }
        return this.ut.getID();
    }

    public void prepare() {
        this.ut = new UsernameToken(this.precisionInMilliSeconds, this.getDocument(), this.wsTimeSource, this.passwordType);
        this.ut.setPasswordsAreEncoded(this.passwordsAreEncoded);
        this.ut.setName(this.user);
        if (this.useDerivedKey) {
            this.saltValue = this.ut.addSalt(this.getDocument(), this.saltValue, this.useMac);
            this.ut.addIteration(this.getDocument(), this.iteration);
        } else {
            this.ut.setPassword(this.password);
        }
        if (this.nonce) {
            this.ut.addNonce(this.getDocument());
        }
        if (this.created) {
            this.ut.addCreated(this.precisionInMilliSeconds, this.wsTimeSource, this.getDocument());
        }
        this.ut.setID(this.getIdAllocator().createId("UsernameToken-", this.ut));
        if (this.addWSUNamespace) {
            this.ut.addWSUNamespace();
        }
    }

    public void prependToHeader() {
        Element securityHeaderElement = this.getSecurityHeader().getSecurityHeaderElement();
        WSSecurityUtil.prependChildElement(securityHeaderElement, this.ut.getElement());
    }

    public void appendToHeader() {
        Element securityHeaderElement = this.getSecurityHeader().getSecurityHeaderElement();
        securityHeaderElement.appendChild(this.ut.getElement());
    }

    public Document build() {
        LOG.debug("Begin add username token...");
        this.prepare();
        this.prependToHeader();
        return this.getDocument();
    }

    public Element getUsernameTokenElement() {
        return this.ut.getElement();
    }

    public boolean isPrecisionInMilliSeconds() {
        return this.precisionInMilliSeconds;
    }

    public void setPrecisionInMilliSeconds(boolean precisionInMilliSeconds) {
        this.precisionInMilliSeconds = precisionInMilliSeconds;
    }

    public WSTimeSource getWsTimeSource() {
        return this.wsTimeSource;
    }

    public void setWsTimeSource(WSTimeSource wsTimeSource) {
        this.wsTimeSource = wsTimeSource;
    }
}

