/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.message.token;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.xml.namespace.QName;
import org.apache.wss4j.common.bsp.BSPEnforcer;
import org.apache.wss4j.common.bsp.BSPRule;
import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.principal.WSUsernameTokenPrincipalImpl;
import org.apache.wss4j.common.util.DOM2Writer;
import org.apache.wss4j.common.util.DateUtil;
import org.apache.wss4j.common.util.KeyUtils;
import org.apache.wss4j.common.util.UsernameTokenUtil;
import org.apache.wss4j.common.util.WSCurrentTimeSource;
import org.apache.wss4j.common.util.WSTimeSource;
import org.apache.wss4j.dom.util.WSSecurityUtil;
import org.apache.xml.security.utils.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

public class UsernameToken {
    public static final String BASE64_ENCODING = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary";
    public static final String PASSWORD_TYPE = "passwordType";
    public static final int DEFAULT_ITERATION = 1000;
    public static final QName TOKEN = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "UsernameToken");
    private static final Logger LOG = LoggerFactory.getLogger(UsernameToken.class);
    private Element element;
    private Element elementUsername;
    private Element elementPassword;
    private Element elementNonce;
    private Element elementCreated;
    private Element elementSalt;
    private Element elementIteration;
    private String passwordType;
    private boolean hashed = true;
    private String rawPassword;
    private boolean passwordsAreEncoded;
    private Instant created;

    public UsernameToken(Element elem, boolean allowNamespaceQualifiedPasswordTypes, BSPEnforcer bspEnforcer) throws WSSecurityException {
        String createdString;
        int iterInt;
        String iter;
        this.element = elem;
        QName el = new QName(this.element.getNamespaceURI(), this.element.getLocalName());
        if (!el.equals(TOKEN)) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY_TOKEN, "badElement", new Object[]{TOKEN, el});
        }
        this.elementUsername = org.apache.wss4j.common.util.XMLUtils.getDirectChildElement(this.element, "Username", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
        this.elementPassword = org.apache.wss4j.common.util.XMLUtils.getDirectChildElement(this.element, "Password", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
        this.elementNonce = org.apache.wss4j.common.util.XMLUtils.getDirectChildElement(this.element, "Nonce", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
        this.elementCreated = org.apache.wss4j.common.util.XMLUtils.getDirectChildElement(this.element, "Created", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
        this.elementSalt = org.apache.wss4j.common.util.XMLUtils.getDirectChildElement(this.element, "Salt", "http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd");
        this.elementIteration = org.apache.wss4j.common.util.XMLUtils.getDirectChildElement(this.element, "Iteration", "http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd");
        if (this.elementUsername == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY_TOKEN, "badUsernameToken", new Object[]{"Username is missing"});
        }
        this.checkBSPCompliance(bspEnforcer);
        this.hashed = false;
        if (this.elementSalt != null) {
            if (this.elementPassword != null || this.elementIteration == null) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY_TOKEN, "badUsernameToken", new Object[]{"Password is missing"});
            }
            return;
        }
        if (this.elementIteration != null && (iter = org.apache.wss4j.common.util.XMLUtils.getElementText(this.elementIteration)) != null && ((iterInt = Integer.parseInt(iter)) < 0 || iterInt > 10000)) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY_TOKEN, "badUsernameToken", new Object[]{"Iteration is missing"});
        }
        if (this.elementPassword != null) {
            if (this.elementPassword.hasAttributeNS(null, "Type")) {
                this.passwordType = this.elementPassword.getAttributeNS(null, "Type");
            } else if (this.elementPassword.hasAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Type")) {
                if (allowNamespaceQualifiedPasswordTypes) {
                    this.passwordType = this.elementPassword.getAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Type");
                } else {
                    throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY_TOKEN, "badUsernameToken", new Object[]{"The Password Type is not allowed to be namespace qualified"});
                }
            }
        }
        if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest".equals(this.passwordType)) {
            this.hashed = true;
            if (this.elementNonce == null || this.elementCreated == null) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY_TOKEN, "badUsernameToken", new Object[]{"Nonce or Created is missing"});
            }
        }
        if (this.elementCreated != null && (createdString = this.getCreated()) != null && !"".equals(createdString)) {
            try {
                this.created = ZonedDateTime.parse(createdString).toInstant();
            }
            catch (DateTimeParseException e) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, (Exception)e);
            }
        }
    }

    public UsernameToken(boolean milliseconds, Document doc) {
        this(milliseconds, doc, "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest");
    }

    public UsernameToken(boolean milliseconds, Document doc, String pwType) {
        this(milliseconds, doc, new WSCurrentTimeSource(), pwType);
    }

    public UsernameToken(boolean milliseconds, Document doc, WSTimeSource timeSource, String pwType) {
        this.element = doc.createElementNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "wsse:UsernameToken");
        this.elementUsername = doc.createElementNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "wsse:Username");
        this.elementUsername.appendChild(doc.createTextNode(""));
        this.element.appendChild(this.elementUsername);
        if (pwType != null) {
            this.elementPassword = doc.createElementNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "wsse:Password");
            this.elementPassword.appendChild(doc.createTextNode(""));
            this.element.appendChild(this.elementPassword);
            this.passwordType = pwType;
            if (this.passwordType.equals("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest")) {
                this.addNonce(doc);
                this.addCreated(milliseconds, timeSource, doc);
            } else {
                this.hashed = false;
            }
        }
    }

    public void addWSSENamespace() {
        org.apache.wss4j.common.util.XMLUtils.setNamespace(this.element, "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "wsse");
    }

    public void addWSUNamespace() {
        this.element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
    }

    public void addNonce(Document doc) {
        if (this.elementNonce != null) {
            return;
        }
        byte[] nonceValue = null;
        try {
            nonceValue = WSSecurityUtil.generateNonce(16);
        }
        catch (WSSecurityException ex) {
            LOG.debug(ex.getMessage(), ex);
            return;
        }
        this.elementNonce = doc.createElementNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "wsse:Nonce");
        this.elementNonce.appendChild(doc.createTextNode(XMLUtils.encodeToString(nonceValue)));
        this.elementNonce.setAttributeNS(null, "EncodingType", BASE64_ENCODING);
        this.element.appendChild(this.elementNonce);
    }

    public void addCreated(boolean milliseconds, Document doc) {
        this.addCreated(milliseconds, new WSCurrentTimeSource(), doc);
    }

    public void addCreated(boolean milliseconds, WSTimeSource timeSource, Document doc) {
        if (this.elementCreated != null) {
            return;
        }
        this.elementCreated = doc.createElementNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "wsu:Created");
        Instant currentTime = timeSource.now();
        DateTimeFormatter formatter = DateUtil.getDateTimeFormatter(milliseconds);
        this.elementCreated.appendChild(doc.createTextNode(currentTime.atZone(ZoneOffset.UTC).format(formatter)));
        this.element.appendChild(this.elementCreated);
    }

    public byte[] addSalt(Document doc, byte[] saltValue, boolean mac) {
        if (saltValue == null) {
            saltValue = UsernameTokenUtil.generateSalt(mac);
        }
        this.elementSalt = doc.createElementNS("http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", "wsse11:Salt");
        org.apache.wss4j.common.util.XMLUtils.setNamespace(this.element, "http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", "wsse11");
        this.elementSalt.appendChild(doc.createTextNode(XMLUtils.encodeToString(saltValue)));
        this.element.appendChild(this.elementSalt);
        return saltValue;
    }

    public void addIteration(Document doc, int iteration) {
        String text = "" + iteration;
        this.elementIteration = doc.createElementNS("http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", "wsse11:Iteration");
        org.apache.wss4j.common.util.XMLUtils.setNamespace(this.element, "http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", "wsse11");
        this.elementIteration.appendChild(doc.createTextNode(text));
        this.element.appendChild(this.elementIteration);
    }

    public String getName() {
        return org.apache.wss4j.common.util.XMLUtils.getElementText(this.elementUsername);
    }

    public void setName(String name) {
        Text node = this.getFirstNode(this.elementUsername);
        node.setData(name);
    }

    public String getNonce() {
        return org.apache.wss4j.common.util.XMLUtils.getElementText(this.elementNonce);
    }

    public String getCreated() {
        return org.apache.wss4j.common.util.XMLUtils.getElementText(this.elementCreated);
    }

    public Instant getCreatedDate() {
        return this.created;
    }

    public String getPassword() {
        String password = org.apache.wss4j.common.util.XMLUtils.getElementText(this.elementPassword);
        if (password == null && this.elementPassword != null) {
            return "";
        }
        return password;
    }

    public boolean containsPasswordElement() {
        return this.elementPassword != null;
    }

    public byte[] getSalt() throws WSSecurityException {
        String salt = org.apache.wss4j.common.util.XMLUtils.getElementText(this.elementSalt);
        if (salt != null) {
            return XMLUtils.decode(salt);
        }
        return null;
    }

    public int getIteration() {
        String iter = org.apache.wss4j.common.util.XMLUtils.getElementText(this.elementIteration);
        if (iter != null) {
            return Integer.parseInt(iter);
        }
        return 1000;
    }

    public boolean isHashed() {
        return this.hashed;
    }

    public String getPasswordType() {
        return this.passwordType;
    }

    public void setPassword(String pwd) {
        if (pwd == null) {
            if (this.passwordType != null) {
                throw new IllegalArgumentException("pwd == null but a password is needed");
            }
            return;
        }
        this.rawPassword = pwd;
        Text node = this.getFirstNode(this.elementPassword);
        try {
            if (this.hashed) {
                if (this.passwordsAreEncoded) {
                    node.setData(UsernameToken.doPasswordDigest(this.getNonce(), this.getCreated(), XMLUtils.decode(pwd)));
                } else {
                    node.setData(UsernameToken.doPasswordDigest(this.getNonce(), this.getCreated(), pwd));
                }
            } else {
                node.setData(pwd);
            }
            if (this.passwordType != null) {
                this.elementPassword.setAttributeNS(null, "Type", this.passwordType);
            }
        }
        catch (Exception e) {
            LOG.debug(e.getMessage(), e);
        }
    }

    public void setRawPassword(CallbackHandler callbackHandler) throws WSSecurityException {
        if (callbackHandler == null) {
            LOG.debug("CallbackHandler is null");
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION);
        }
        WSPasswordCallback pwCb = new WSPasswordCallback(this.getName(), this.getPassword(), this.getPasswordType(), 2);
        try {
            callbackHandler.handle(new Callback[]{pwCb});
        }
        catch (IOException | UnsupportedCallbackException e) {
            LOG.debug(e.getMessage(), e);
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION, e);
        }
        this.rawPassword = pwCb.getPassword();
    }

    public void setPasswordsAreEncoded(boolean passwordsAreEncoded) {
        this.passwordsAreEncoded = passwordsAreEncoded;
    }

    public boolean getPasswordsAreEncoded() {
        return this.passwordsAreEncoded;
    }

    public static String doPasswordDigest(String nonce, String created, byte[] password) {
        String passwdDigest = null;
        try {
            byte[] b1 = nonce != null ? XMLUtils.decode(nonce) : new byte[]{};
            byte[] b2 = created != null ? created.getBytes(StandardCharsets.UTF_8) : new byte[]{};
            byte[] b3 = password;
            byte[] b4 = new byte[b1.length + b2.length + b3.length];
            int offset = 0;
            System.arraycopy(b1, 0, b4, offset, b1.length);
            System.arraycopy(b2, 0, b4, offset += b1.length, b2.length);
            System.arraycopy(b3, 0, b4, offset += b2.length, b3.length);
            byte[] digestBytes = KeyUtils.generateDigest(b4);
            passwdDigest = XMLUtils.encodeToString(digestBytes);
        }
        catch (Exception e) {
            LOG.debug(e.getMessage(), e);
        }
        return passwdDigest;
    }

    public static String doPasswordDigest(String nonce, String created, String password) {
        String passwdDigest = null;
        try {
            passwdDigest = UsernameToken.doPasswordDigest(nonce, created, password.getBytes(StandardCharsets.UTF_8));
        }
        catch (Exception e) {
            LOG.debug(e.getMessage(), e);
        }
        return passwdDigest;
    }

    private Text getFirstNode(Element e) {
        Node node = e.getFirstChild();
        return node != null && 3 == node.getNodeType() ? (Text)node : null;
    }

    public Element getElement() {
        return this.element;
    }

    public String toString() {
        return DOM2Writer.nodeToString(this.element);
    }

    public String getID() {
        return this.element.getAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id");
    }

    public void setID(String id) {
        this.element.setAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "wsu:Id", id);
    }

    public byte[] getDerivedKey(BSPEnforcer bspEnforcer) throws WSSecurityException {
        if (this.rawPassword == null) {
            LOG.debug("The raw password was null");
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION);
        }
        if (this.elementSalt == null) {
            bspEnforcer.handleBSPRule(BSPRule.R4217);
        }
        if (this.elementIteration == null) {
            bspEnforcer.handleBSPRule(BSPRule.R4218);
        } else {
            String iter = org.apache.wss4j.common.util.XMLUtils.getElementText(this.elementIteration);
            if (iter == null || Integer.parseInt(iter) < 1000) {
                bspEnforcer.handleBSPRule(BSPRule.R4218);
            }
        }
        int iteration = this.getIteration();
        byte[] salt = this.getSalt();
        if (this.passwordsAreEncoded) {
            return UsernameTokenUtil.generateDerivedKey(XMLUtils.decode(this.rawPassword), salt, iteration);
        }
        return UsernameTokenUtil.generateDerivedKey(this.rawPassword, salt, iteration);
    }

    public boolean isDerivedKey() throws WSSecurityException {
        return this.elementSalt != null && this.elementIteration != null;
    }

    public Principal createPrincipal() throws WSSecurityException {
        WSUsernameTokenPrincipalImpl principal = new WSUsernameTokenPrincipalImpl(this.getName(), this.isHashed());
        String nonce = this.getNonce();
        if (nonce != null) {
            principal.setNonce(XMLUtils.decode(nonce));
        }
        principal.setPassword(this.getPassword());
        principal.setCreatedTime(this.getCreated());
        return principal;
    }

    public boolean verifyCreated(int timeToLive, int futureTimeToLive) {
        return DateUtil.verifyCreated(this.created, timeToLive, futureTimeToLive);
    }

    public int hashCode() {
        String created;
        String nonce;
        String passwordType;
        String password;
        int result = 17;
        String username = this.getName();
        if (username != null) {
            result = 31 * result + username.hashCode();
        }
        if ((password = this.getPassword()) != null) {
            result = 31 * result + password.hashCode();
        }
        if ((passwordType = this.getPasswordType()) != null) {
            result = 31 * result + passwordType.hashCode();
        }
        if ((nonce = this.getNonce()) != null) {
            result = 31 * result + nonce.hashCode();
        }
        if ((created = this.getCreated()) != null) {
            result = 31 * result + created.hashCode();
        }
        try {
            byte[] salt = this.getSalt();
            if (salt != null) {
                result = 31 * result + Arrays.hashCode(salt);
            }
        }
        catch (WSSecurityException ex) {
            LOG.debug(ex.getMessage(), ex);
        }
        result = 31 * result + Integer.valueOf(this.getIteration()).hashCode();
        return result;
    }

    public boolean equals(Object object) {
        if (!(object instanceof UsernameToken)) {
            return false;
        }
        UsernameToken usernameToken = (UsernameToken)object;
        if (!this.compare(usernameToken.getName(), this.getName())) {
            return false;
        }
        if (!this.compare(usernameToken.getPassword(), this.getPassword())) {
            return false;
        }
        if (!this.compare(usernameToken.getPasswordType(), this.getPasswordType())) {
            return false;
        }
        if (!this.compare(usernameToken.getNonce(), this.getNonce())) {
            return false;
        }
        if (!this.compare(usernameToken.getCreated(), this.getCreated())) {
            return false;
        }
        try {
            byte[] salt = usernameToken.getSalt();
            if (!Arrays.equals(salt, this.getSalt())) {
                return false;
            }
        }
        catch (WSSecurityException ex) {
            LOG.debug(ex.getMessage(), ex);
        }
        int iteration = usernameToken.getIteration();
        return iteration == this.getIteration();
    }

    private boolean compare(String item1, String item2) {
        if (item1 == null && item2 != null) {
            return false;
        }
        return item1 == null || item1.equals(item2);
    }

    private void checkBSPCompliance(BSPEnforcer bspEnforcer) throws WSSecurityException {
        List<Element> nonceElements;
        List<Element> createdElements;
        Element passwordChild;
        String type;
        List<Element> passwordElements = WSSecurityUtil.getDirectChildElements(this.element, "Password", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
        if (passwordElements.size() > 1) {
            LOG.debug("The Username Token had more than one password element");
            bspEnforcer.handleBSPRule(BSPRule.R4222);
        }
        if (passwordElements.size() == 1 && ((type = (passwordChild = passwordElements.get(0)).getAttributeNS(null, "Type")) == null || "".equals(type))) {
            LOG.debug("The Username Token password does not have a Type attribute");
            bspEnforcer.handleBSPRule(BSPRule.R4201);
        }
        if ((createdElements = WSSecurityUtil.getDirectChildElements(this.element, "Created", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd")).size() > 1) {
            LOG.debug("The Username Token has more than one created element");
            bspEnforcer.handleBSPRule(BSPRule.R4223);
        }
        if ((nonceElements = WSSecurityUtil.getDirectChildElements(this.element, "Nonce", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd")).size() > 1) {
            LOG.debug("The Username Token has more than one nonce element");
            bspEnforcer.handleBSPRule(BSPRule.R4225);
        }
        if (nonceElements.size() == 1) {
            Element nonce = nonceElements.get(0);
            String encodingType = nonce.getAttributeNS(null, "EncodingType");
            if (encodingType == null || "".equals(encodingType)) {
                bspEnforcer.handleBSPRule(BSPRule.R4220);
            } else if (!BASE64_ENCODING.equals(encodingType)) {
                LOG.debug("The Username Token's nonce element has a bad encoding type");
                bspEnforcer.handleBSPRule(BSPRule.R4221);
            }
        }
    }
}

