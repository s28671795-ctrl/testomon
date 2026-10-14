/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.saml;

import java.util.ArrayList;
import java.util.List;
import javax.security.auth.callback.Callback;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.saml.bean.AdviceBean;
import org.apache.wss4j.common.saml.bean.AttributeStatementBean;
import org.apache.wss4j.common.saml.bean.AuthDecisionStatementBean;
import org.apache.wss4j.common.saml.bean.AuthenticationStatementBean;
import org.apache.wss4j.common.saml.bean.ConditionsBean;
import org.apache.wss4j.common.saml.bean.SubjectBean;
import org.apache.wss4j.common.saml.bean.Version;
import org.opensaml.saml.common.SAMLVersion;
import org.w3c.dom.Element;

public class SAMLCallback
implements Callback {
    private SAMLVersion samlVersion;
    private SubjectBean subject;
    private String issuer;
    private String issuerFormat;
    private String issuerQualifier;
    private ConditionsBean conditions;
    private AdviceBean advice;
    private List<AuthenticationStatementBean> authenticationStatementData = new ArrayList<AuthenticationStatementBean>();
    private List<AttributeStatementBean> attributeStatementData = new ArrayList<AttributeStatementBean>();
    private List<AuthDecisionStatementBean> authDecisionStatementData = new ArrayList<AuthDecisionStatementBean>();
    private Element assertionElement;
    private boolean signAssertion;
    private String issuerKeyName;
    private String issuerKeyPassword;
    private Crypto issuerCrypto;
    private boolean sendKeyValue;
    private String canonicalizationAlgorithm;
    private String signatureAlgorithm;
    private String signatureDigestAlgorithm;

    public List<AuthenticationStatementBean> getAuthenticationStatementData() {
        return this.authenticationStatementData;
    }

    public void setAuthenticationStatementData(List<AuthenticationStatementBean> authenticationStatementData) {
        this.authenticationStatementData = authenticationStatementData;
    }

    public List<AttributeStatementBean> getAttributeStatementData() {
        return this.attributeStatementData;
    }

    public void setAttributeStatementData(List<AttributeStatementBean> attributeStatementData) {
        this.attributeStatementData = attributeStatementData;
    }

    public List<AuthDecisionStatementBean> getAuthDecisionStatementData() {
        return this.authDecisionStatementData;
    }

    public void setAuthDecisionStatementData(List<AuthDecisionStatementBean> authDecisionStatementData) {
        this.authDecisionStatementData = authDecisionStatementData;
    }

    public SubjectBean getSubject() {
        if (this.subject != null) {
            return this.subject;
        }
        if (this.authenticationStatementData != null) {
            for (AuthenticationStatementBean authenticationStatementBean : this.authenticationStatementData) {
                if (authenticationStatementBean.getSubject() == null) continue;
                return authenticationStatementBean.getSubject();
            }
        }
        if (this.attributeStatementData != null) {
            for (AttributeStatementBean attributeStatementBean : this.attributeStatementData) {
                if (attributeStatementBean.getSubject() == null) continue;
                return attributeStatementBean.getSubject();
            }
        }
        if (this.authDecisionStatementData != null) {
            for (AuthDecisionStatementBean authDecisionStatementBean : this.authDecisionStatementData) {
                if (authDecisionStatementBean.getSubject() == null) continue;
                return authDecisionStatementBean.getSubject();
            }
        }
        return null;
    }

    public void setSubject(SubjectBean subject) {
        this.subject = subject;
    }

    public String getIssuer() {
        return this.issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public ConditionsBean getConditions() {
        return this.conditions;
    }

    public void setConditions(ConditionsBean conditions) {
        this.conditions = conditions;
    }

    @Deprecated
    public void setSamlVersion(SAMLVersion samlVersion) {
        this.samlVersion = samlVersion;
    }

    public SAMLVersion getSamlVersion() {
        return this.samlVersion;
    }

    public void setSamlVersion(Version samlVersion) {
        if (samlVersion == Version.SAML_20) {
            this.samlVersion = SAMLVersion.VERSION_20;
        } else if (samlVersion == Version.SAML_11) {
            this.samlVersion = SAMLVersion.VERSION_11;
        } else if (samlVersion == Version.SAML_10) {
            this.samlVersion = SAMLVersion.VERSION_10;
        }
    }

    public void setAssertionElement(Element assertionElement) {
        this.assertionElement = assertionElement;
    }

    public Element getAssertionElement() {
        return this.assertionElement;
    }

    public boolean isSignAssertion() {
        return this.signAssertion;
    }

    public void setSignAssertion(boolean signAssertion) {
        this.signAssertion = signAssertion;
    }

    public String getIssuerKeyName() {
        return this.issuerKeyName;
    }

    public void setIssuerKeyName(String issuerKeyName) {
        this.issuerKeyName = issuerKeyName;
    }

    public String getIssuerKeyPassword() {
        return this.issuerKeyPassword;
    }

    public void setIssuerKeyPassword(String issuerKeyPassword) {
        this.issuerKeyPassword = issuerKeyPassword;
    }

    public Crypto getIssuerCrypto() {
        return this.issuerCrypto;
    }

    public void setIssuerCrypto(Crypto issuerCrypto) {
        this.issuerCrypto = issuerCrypto;
    }

    public boolean isSendKeyValue() {
        return this.sendKeyValue;
    }

    public void setSendKeyValue(boolean sendKeyValue) {
        this.sendKeyValue = sendKeyValue;
    }

    public String getCanonicalizationAlgorithm() {
        return this.canonicalizationAlgorithm;
    }

    public void setCanonicalizationAlgorithm(String canonicalizationAlgorithm) {
        this.canonicalizationAlgorithm = canonicalizationAlgorithm;
    }

    public String getSignatureAlgorithm() {
        return this.signatureAlgorithm;
    }

    public void setSignatureAlgorithm(String signatureAlgorithm) {
        this.signatureAlgorithm = signatureAlgorithm;
    }

    public String getSignatureDigestAlgorithm() {
        return this.signatureDigestAlgorithm;
    }

    public void setSignatureDigestAlgorithm(String signatureDigestAlgorithm) {
        this.signatureDigestAlgorithm = signatureDigestAlgorithm;
    }

    public AdviceBean getAdvice() {
        return this.advice;
    }

    public void setAdvice(AdviceBean advice) {
        this.advice = advice;
    }

    public String getIssuerFormat() {
        return this.issuerFormat;
    }

    public void setIssuerFormat(String issuerFormat) {
        this.issuerFormat = issuerFormat;
    }

    public String getIssuerQualifier() {
        return this.issuerQualifier;
    }

    public void setIssuerQualifier(String issuerQualifier) {
        this.issuerQualifier = issuerQualifier;
    }
}

