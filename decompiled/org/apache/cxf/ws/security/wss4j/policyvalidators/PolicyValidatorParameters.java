/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j.policyvalidators;

import java.util.Collection;
import java.util.List;
import org.apache.cxf.message.Message;
import org.apache.cxf.ws.policy.AssertionInfoMap;
import org.apache.wss4j.dom.WSDataRef;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.apache.wss4j.dom.handler.WSHandlerResult;
import org.w3c.dom.Element;

public class PolicyValidatorParameters {
    private AssertionInfoMap assertionInfoMap;
    private Message message;
    private Element soapBody;
    private Element soapHeader;
    private WSHandlerResult results;
    private List<WSSecurityEngineResult> signedResults;
    private List<WSSecurityEngineResult> encryptedResults;
    private List<WSSecurityEngineResult> usernameTokenResults;
    private List<WSSecurityEngineResult> samlResults;
    private Element timestampElement;
    private boolean utWithCallbacks;
    private Collection<WSDataRef> signed;
    private Collection<WSDataRef> encrypted;

    public Message getMessage() {
        return this.message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Element getSoapBody() {
        return this.soapBody;
    }

    public void setSoapBody(Element soapBody) {
        this.soapBody = soapBody;
    }

    public WSHandlerResult getResults() {
        return this.results;
    }

    public void setResults(WSHandlerResult results) {
        this.results = results;
    }

    public List<WSSecurityEngineResult> getSignedResults() {
        return this.signedResults;
    }

    public void setSignedResults(List<WSSecurityEngineResult> signedResults) {
        this.signedResults = signedResults;
    }

    public List<WSSecurityEngineResult> getEncryptedResults() {
        return this.encryptedResults;
    }

    public void setEncryptedResults(List<WSSecurityEngineResult> encryptedResults) {
        this.encryptedResults = encryptedResults;
    }

    public AssertionInfoMap getAssertionInfoMap() {
        return this.assertionInfoMap;
    }

    public void setAssertionInfoMap(AssertionInfoMap assertionInfoMap) {
        this.assertionInfoMap = assertionInfoMap;
    }

    public List<WSSecurityEngineResult> getUsernameTokenResults() {
        return this.usernameTokenResults;
    }

    public void setUsernameTokenResults(List<WSSecurityEngineResult> usernameTokenResults) {
        this.usernameTokenResults = usernameTokenResults;
    }

    public List<WSSecurityEngineResult> getSamlResults() {
        return this.samlResults;
    }

    public void setSamlResults(List<WSSecurityEngineResult> samlResults) {
        this.samlResults = samlResults;
    }

    public Element getTimestampElement() {
        return this.timestampElement;
    }

    public void setTimestampElement(Element timestampElement) {
        this.timestampElement = timestampElement;
    }

    public boolean isUtWithCallbacks() {
        return this.utWithCallbacks;
    }

    public void setUtWithCallbacks(boolean utWithCallbacks) {
        this.utWithCallbacks = utWithCallbacks;
    }

    public Element getSoapHeader() {
        return this.soapHeader;
    }

    public void setSoapHeader(Element soapHeader) {
        this.soapHeader = soapHeader;
    }

    public Collection<WSDataRef> getSigned() {
        return this.signed;
    }

    public void setSigned(Collection<WSDataRef> signed) {
        this.signed = signed;
    }

    public Collection<WSDataRef> getEncrypted() {
        return this.encrypted;
    }

    public void setEncrypted(Collection<WSDataRef> encrypted) {
        this.encrypted = encrypted;
    }
}

