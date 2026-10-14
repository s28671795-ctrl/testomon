/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.saml.builder;

import java.util.ArrayList;
import java.util.List;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.saml.OpenSAMLUtil;
import org.apache.wss4j.common.saml.bean.ActionBean;
import org.apache.wss4j.common.saml.bean.AdviceBean;
import org.apache.wss4j.common.saml.bean.AttributeBean;
import org.apache.wss4j.common.saml.bean.AttributeStatementBean;
import org.apache.wss4j.common.saml.bean.AudienceRestrictionBean;
import org.apache.wss4j.common.saml.bean.AuthDecisionStatementBean;
import org.apache.wss4j.common.saml.bean.AuthenticationStatementBean;
import org.apache.wss4j.common.saml.bean.ConditionsBean;
import org.apache.wss4j.common.saml.bean.KeyInfoBean;
import org.apache.wss4j.common.saml.bean.SubjectBean;
import org.apache.wss4j.common.saml.bean.SubjectLocalityBean;
import org.apache.xml.security.stax.impl.util.IDGenerator;
import org.joda.time.DateTime;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.XMLObjectBuilderFactory;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.schema.XSString;
import org.opensaml.core.xml.schema.impl.XSStringBuilder;
import org.opensaml.saml.common.SAMLObjectBuilder;
import org.opensaml.saml.common.SAMLVersion;
import org.opensaml.saml.saml1.core.Action;
import org.opensaml.saml.saml1.core.Advice;
import org.opensaml.saml.saml1.core.Assertion;
import org.opensaml.saml.saml1.core.AssertionIDReference;
import org.opensaml.saml.saml1.core.Attribute;
import org.opensaml.saml.saml1.core.AttributeStatement;
import org.opensaml.saml.saml1.core.AttributeValue;
import org.opensaml.saml.saml1.core.Audience;
import org.opensaml.saml.saml1.core.AudienceRestrictionCondition;
import org.opensaml.saml.saml1.core.AuthenticationStatement;
import org.opensaml.saml.saml1.core.AuthorizationDecisionStatement;
import org.opensaml.saml.saml1.core.Conditions;
import org.opensaml.saml.saml1.core.ConfirmationMethod;
import org.opensaml.saml.saml1.core.DecisionTypeEnumeration;
import org.opensaml.saml.saml1.core.Evidence;
import org.opensaml.saml.saml1.core.NameIdentifier;
import org.opensaml.saml.saml1.core.Subject;
import org.opensaml.saml.saml1.core.SubjectConfirmation;
import org.opensaml.saml.saml1.core.SubjectLocality;
import org.opensaml.security.SecurityException;
import org.opensaml.security.credential.BasicCredential;
import org.opensaml.security.x509.BasicX509Credential;
import org.opensaml.xmlsec.keyinfo.impl.BasicKeyInfoGeneratorFactory;
import org.opensaml.xmlsec.keyinfo.impl.X509KeyInfoGeneratorFactory;
import org.opensaml.xmlsec.signature.KeyInfo;
import org.w3c.dom.Element;

public final class SAML1ComponentBuilder {
    private static volatile SAMLObjectBuilder<Assertion> assertionV1Builder;
    private static volatile SAMLObjectBuilder<Conditions> conditionsV1Builder;
    private static volatile SAMLObjectBuilder<Advice> adviceV1Builder;
    private static volatile SAMLObjectBuilder<AssertionIDReference> assertionIDReferenceBuilder;
    private static volatile SAMLObjectBuilder<AudienceRestrictionCondition> audienceRestrictionV1Builder;
    private static volatile SAMLObjectBuilder<Audience> audienceV1Builder;
    private static volatile SAMLObjectBuilder<AuthenticationStatement> authenticationStatementV1Builder;
    private static volatile SAMLObjectBuilder<Subject> subjectV1Builder;
    private static volatile SAMLObjectBuilder<NameIdentifier> nameIdentifierV1Builder;
    private static volatile SAMLObjectBuilder<SubjectConfirmation> subjectConfirmationV1Builder;
    private static volatile SAMLObjectBuilder<ConfirmationMethod> confirmationMethodV1Builder;
    private static volatile SAMLObjectBuilder<AttributeStatement> attributeStatementV1Builder;
    private static volatile SAMLObjectBuilder<Attribute> attributeV1Builder;
    private static volatile XSStringBuilder stringBuilder;
    private static volatile SAMLObjectBuilder<AuthorizationDecisionStatement> authorizationDecisionStatementV1Builder;
    private static volatile SAMLObjectBuilder<Action> actionElementV1Builder;
    private static volatile XMLObjectBuilderFactory builderFactory;
    private static volatile SAMLObjectBuilder<SubjectLocality> subjectLocalityBuilder;

    private SAML1ComponentBuilder() {
    }

    public static Assertion createSamlv1Assertion(String issuer) {
        if (assertionV1Builder == null && (assertionV1Builder = (SAMLObjectBuilder)builderFactory.getBuilder(Assertion.DEFAULT_ELEMENT_NAME)) == null) {
            throw new IllegalStateException("OpenSaml engine not initialized. Please make sure to initialize the OpenSaml engine prior using it");
        }
        Assertion assertion = (Assertion)assertionV1Builder.buildObject(Assertion.DEFAULT_ELEMENT_NAME, Assertion.TYPE_NAME);
        assertion.setVersion(SAMLVersion.VERSION_11);
        assertion.setIssuer(issuer);
        assertion.setIssueInstant(new DateTime());
        assertion.setID(IDGenerator.generateID("_"));
        return assertion;
    }

    public static Subject createSaml1v1Subject(SubjectBean subjectBean) throws SecurityException, WSSecurityException {
        if (subjectV1Builder == null) {
            subjectV1Builder = (SAMLObjectBuilder)builderFactory.getBuilder(Subject.DEFAULT_ELEMENT_NAME);
        }
        if (nameIdentifierV1Builder == null) {
            nameIdentifierV1Builder = (SAMLObjectBuilder)builderFactory.getBuilder(NameIdentifier.DEFAULT_ELEMENT_NAME);
        }
        if (subjectConfirmationV1Builder == null) {
            subjectConfirmationV1Builder = (SAMLObjectBuilder)builderFactory.getBuilder(SubjectConfirmation.DEFAULT_ELEMENT_NAME);
        }
        if (confirmationMethodV1Builder == null) {
            confirmationMethodV1Builder = (SAMLObjectBuilder)builderFactory.getBuilder(ConfirmationMethod.DEFAULT_ELEMENT_NAME);
        }
        Subject subject = subjectV1Builder.buildObject();
        NameIdentifier nameIdentifier = nameIdentifierV1Builder.buildObject();
        SubjectConfirmation subjectConfirmation = subjectConfirmationV1Builder.buildObject();
        ConfirmationMethod confirmationMethod = confirmationMethodV1Builder.buildObject();
        nameIdentifier.setNameQualifier(subjectBean.getSubjectNameQualifier());
        nameIdentifier.setValue(subjectBean.getSubjectName());
        nameIdentifier.setFormat(subjectBean.getSubjectNameIDFormat());
        String confirmationMethodStr = subjectBean.getSubjectConfirmationMethod();
        if (confirmationMethodStr == null) {
            confirmationMethodStr = "urn:oasis:names:tc:SAML:1.0:cm:sender-vouches";
        }
        confirmationMethod.setConfirmationMethod(confirmationMethodStr);
        subjectConfirmation.getConfirmationMethods().add(confirmationMethod);
        if (subjectBean.getKeyInfo() != null) {
            KeyInfo keyInfo = SAML1ComponentBuilder.createKeyInfo(subjectBean.getKeyInfo());
            subjectConfirmation.setKeyInfo(keyInfo);
        }
        subject.setNameIdentifier(nameIdentifier);
        subject.setSubjectConfirmation(subjectConfirmation);
        return subject;
    }

    public static KeyInfo createKeyInfo(KeyInfoBean keyInfo) throws SecurityException, WSSecurityException {
        if (keyInfo.getElement() != null) {
            return (KeyInfo)OpenSAMLUtil.fromDom(keyInfo.getElement());
        }
        if (keyInfo.getCertificate() != null) {
            BasicX509Credential keyInfoCredential = new BasicX509Credential(keyInfo.getCertificate());
            X509KeyInfoGeneratorFactory kiFactory = new X509KeyInfoGeneratorFactory();
            KeyInfoBean.CERT_IDENTIFIER certIdentifier = keyInfo.getCertIdentifer();
            switch (certIdentifier) {
                case X509_CERT: {
                    kiFactory.setEmitEntityCertificate(true);
                    break;
                }
                case KEY_VALUE: {
                    kiFactory.setEmitPublicKeyValue(true);
                    break;
                }
                case X509_ISSUER_SERIAL: {
                    kiFactory.setEmitX509IssuerSerial(true);
                }
            }
            return kiFactory.newInstance().generate(keyInfoCredential);
        }
        if (keyInfo.getPublicKey() != null) {
            BasicCredential keyInfoCredential = new BasicCredential(keyInfo.getPublicKey());
            BasicKeyInfoGeneratorFactory kiFactory = new BasicKeyInfoGeneratorFactory();
            kiFactory.setEmitPublicKeyValue(true);
            return kiFactory.newInstance().generate(keyInfoCredential);
        }
        return null;
    }

    public static Conditions createSamlv1Conditions(ConditionsBean conditionsBean) {
        if (conditionsV1Builder == null) {
            conditionsV1Builder = (SAMLObjectBuilder)builderFactory.getBuilder(Conditions.DEFAULT_ELEMENT_NAME);
        }
        Conditions conditions = conditionsV1Builder.buildObject();
        if (conditionsBean == null) {
            DateTime newNotBefore = new DateTime();
            conditions.setNotBefore(newNotBefore);
            conditions.setNotOnOrAfter(newNotBefore.plusMinutes(5));
            return conditions;
        }
        long tokenPeriodSeconds = conditionsBean.getTokenPeriodSeconds();
        DateTime notBefore = conditionsBean.getNotBefore();
        DateTime notAfter = conditionsBean.getNotAfter();
        if (notBefore != null && notAfter != null) {
            if (notBefore.isAfter(notAfter)) {
                throw new IllegalStateException("The value of notBefore may not be after the value of notAfter");
            }
            conditions.setNotBefore(notBefore);
            conditions.setNotOnOrAfter(notAfter);
        } else {
            DateTime newNotBefore = new DateTime();
            conditions.setNotBefore(newNotBefore);
            if (tokenPeriodSeconds <= 0L) {
                tokenPeriodSeconds = 300L;
            }
            DateTime notOnOrAfter = new DateTime(newNotBefore.getMillis() + tokenPeriodSeconds * 1000L);
            conditions.setNotOnOrAfter(notOnOrAfter);
        }
        if (conditionsBean.getAudienceRestrictions() != null && !conditionsBean.getAudienceRestrictions().isEmpty()) {
            for (AudienceRestrictionBean audienceRestrictionBean : conditionsBean.getAudienceRestrictions()) {
                AudienceRestrictionCondition audienceRestriction = SAML1ComponentBuilder.createSamlv1AudienceRestriction(audienceRestrictionBean);
                conditions.getAudienceRestrictionConditions().add(audienceRestriction);
            }
        }
        return conditions;
    }

    public static Advice createAdvice(AdviceBean adviceBean) throws WSSecurityException {
        if (adviceV1Builder == null) {
            adviceV1Builder = (SAMLObjectBuilder)builderFactory.getBuilder(Advice.DEFAULT_ELEMENT_NAME);
        }
        Advice advice = adviceV1Builder.buildObject();
        if (!adviceBean.getIdReferences().isEmpty()) {
            if (assertionIDReferenceBuilder == null) {
                assertionIDReferenceBuilder = (SAMLObjectBuilder)builderFactory.getBuilder(AssertionIDReference.DEFAULT_ELEMENT_NAME);
            }
            for (String ref : adviceBean.getIdReferences()) {
                AssertionIDReference assertionIdReference = assertionIDReferenceBuilder.buildObject();
                assertionIdReference.setReference(ref);
                advice.getAssertionIDReferences().add(assertionIdReference);
            }
        }
        if (!adviceBean.getAssertions().isEmpty()) {
            for (Element assertionElement : adviceBean.getAssertions()) {
                XMLObject xmlObject = OpenSAMLUtil.fromDom(assertionElement);
                if (!(xmlObject instanceof Assertion)) continue;
                Assertion assertion = (Assertion)xmlObject;
                advice.getAssertions().add(assertion);
            }
        }
        return advice;
    }

    public static AudienceRestrictionCondition createSamlv1AudienceRestriction(AudienceRestrictionBean audienceRestrictionBean) {
        if (audienceRestrictionV1Builder == null) {
            audienceRestrictionV1Builder = (SAMLObjectBuilder)builderFactory.getBuilder(AudienceRestrictionCondition.DEFAULT_ELEMENT_NAME);
        }
        if (audienceV1Builder == null) {
            audienceV1Builder = (SAMLObjectBuilder)builderFactory.getBuilder(Audience.DEFAULT_ELEMENT_NAME);
        }
        AudienceRestrictionCondition audienceRestriction = audienceRestrictionV1Builder.buildObject();
        for (String audienceURI : audienceRestrictionBean.getAudienceURIs()) {
            Audience audience = audienceV1Builder.buildObject();
            audience.setUri(audienceURI);
            audienceRestriction.getAudiences().add(audience);
        }
        return audienceRestriction;
    }

    public static List<AuthenticationStatement> createSamlv1AuthenticationStatement(List<AuthenticationStatementBean> authBeans) throws SecurityException, WSSecurityException {
        ArrayList<AuthenticationStatement> authenticationStatements = new ArrayList<AuthenticationStatement>();
        if (authenticationStatementV1Builder == null) {
            authenticationStatementV1Builder = (SAMLObjectBuilder)builderFactory.getBuilder(AuthenticationStatement.DEFAULT_ELEMENT_NAME);
        }
        if (subjectLocalityBuilder == null) {
            subjectLocalityBuilder = (SAMLObjectBuilder)builderFactory.getBuilder(SubjectLocality.DEFAULT_ELEMENT_NAME);
        }
        if (authBeans != null && !authBeans.isEmpty()) {
            for (AuthenticationStatementBean statementBean : authBeans) {
                AuthenticationStatement authenticationStatement = (AuthenticationStatement)authenticationStatementV1Builder.buildObject(AuthenticationStatement.DEFAULT_ELEMENT_NAME, AuthenticationStatement.TYPE_NAME);
                Subject authSubject = SAML1ComponentBuilder.createSaml1v1Subject(statementBean.getSubject());
                authenticationStatement.setSubject(authSubject);
                if (statementBean.getAuthenticationInstant() != null) {
                    authenticationStatement.setAuthenticationInstant(statementBean.getAuthenticationInstant());
                } else {
                    authenticationStatement.setAuthenticationInstant(new DateTime());
                }
                authenticationStatement.setAuthenticationMethod(SAML1ComponentBuilder.transformAuthenticationMethod(statementBean.getAuthenticationMethod()));
                SubjectLocalityBean subjectLocalityBean = statementBean.getSubjectLocality();
                if (subjectLocalityBean != null) {
                    SubjectLocality subjectLocality = subjectLocalityBuilder.buildObject();
                    subjectLocality.setDNSAddress(subjectLocalityBean.getDnsAddress());
                    subjectLocality.setIPAddress(subjectLocalityBean.getIpAddress());
                    authenticationStatement.setSubjectLocality(subjectLocality);
                }
                authenticationStatements.add(authenticationStatement);
            }
        }
        return authenticationStatements;
    }

    private static String transformAuthenticationMethod(String sourceMethod) {
        String transformedMethod = "";
        if ("Password".equals(sourceMethod)) {
            transformedMethod = "urn:oasis:names:tc:SAML:1.0:am:password";
        } else if (sourceMethod != null && !"".equals(sourceMethod)) {
            return sourceMethod;
        }
        return transformedMethod;
    }

    public static List<AttributeStatement> createSamlv1AttributeStatement(List<AttributeStatementBean> attributeData) throws SecurityException, WSSecurityException {
        if (attributeStatementV1Builder == null) {
            attributeStatementV1Builder = (SAMLObjectBuilder)builderFactory.getBuilder(AttributeStatement.DEFAULT_ELEMENT_NAME);
        }
        ArrayList<AttributeStatement> attributeStatements = new ArrayList<AttributeStatement>();
        if (attributeData != null && !attributeData.isEmpty()) {
            for (AttributeStatementBean statementBean : attributeData) {
                AttributeStatement attributeStatement = attributeStatementV1Builder.buildObject();
                Subject attributeSubject = SAML1ComponentBuilder.createSaml1v1Subject(statementBean.getSubject());
                attributeStatement.setSubject(attributeSubject);
                for (AttributeBean values2 : statementBean.getSamlAttributes()) {
                    List<Object> attributeValues = values2.getAttributeValues();
                    Attribute samlAttribute = SAML1ComponentBuilder.createSamlv1Attribute(values2.getSimpleName(), values2.getQualifiedName(), attributeValues);
                    attributeStatement.getAttributes().add(samlAttribute);
                }
                attributeStatements.add(attributeStatement);
            }
        }
        return attributeStatements;
    }

    public static Attribute createSamlv1Attribute(String attributeName, String attributeUrn, List<Object> values2) {
        if (attributeV1Builder == null) {
            attributeV1Builder = (SAMLObjectBuilder)builderFactory.getBuilder(Attribute.DEFAULT_ELEMENT_NAME);
        }
        if (stringBuilder == null) {
            stringBuilder = (XSStringBuilder)builderFactory.getBuilder(XSString.TYPE_NAME);
        }
        Attribute attribute = attributeV1Builder.buildObject();
        attribute.setAttributeName(attributeName);
        attribute.setAttributeNamespace(attributeUrn);
        for (Object value : values2) {
            if (value instanceof String) {
                XSString attribute1 = (XSString)stringBuilder.buildObject(AttributeValue.DEFAULT_ELEMENT_NAME, XSString.TYPE_NAME);
                attribute1.setValue((String)value);
                attribute.getAttributeValues().add(attribute1);
                continue;
            }
            if (!(value instanceof XMLObject)) continue;
            attribute.getAttributeValues().add((XMLObject)value);
        }
        return attribute;
    }

    public static List<AuthorizationDecisionStatement> createSamlv1AuthorizationDecisionStatement(List<AuthDecisionStatementBean> decisionData) throws SecurityException, WSSecurityException {
        ArrayList<AuthorizationDecisionStatement> authDecisionStatements = new ArrayList<AuthorizationDecisionStatement>();
        if (authorizationDecisionStatementV1Builder == null) {
            authorizationDecisionStatementV1Builder = (SAMLObjectBuilder)builderFactory.getBuilder(AuthorizationDecisionStatement.DEFAULT_ELEMENT_NAME);
        }
        if (decisionData != null && !decisionData.isEmpty()) {
            for (AuthDecisionStatementBean decisionStatementBean : decisionData) {
                AuthorizationDecisionStatement authDecision = authorizationDecisionStatementV1Builder.buildObject();
                Subject authDecisionSubject = SAML1ComponentBuilder.createSaml1v1Subject(decisionStatementBean.getSubject());
                authDecision.setSubject(authDecisionSubject);
                authDecision.setResource(decisionStatementBean.getResource());
                authDecision.setDecision(SAML1ComponentBuilder.transformDecisionType(decisionStatementBean.getDecision()));
                for (ActionBean actionBean : decisionStatementBean.getActions()) {
                    Action actionElement = SAML1ComponentBuilder.createSamlv1Action(actionBean);
                    authDecision.getActions().add(actionElement);
                }
                if (decisionStatementBean.getEvidence() instanceof Evidence) {
                    authDecision.setEvidence((Evidence)decisionStatementBean.getEvidence());
                }
                authDecisionStatements.add(authDecision);
            }
        }
        return authDecisionStatements;
    }

    public static Action createSamlv1Action(ActionBean actionBean) {
        if (actionElementV1Builder == null) {
            actionElementV1Builder = (SAMLObjectBuilder)builderFactory.getBuilder(Action.DEFAULT_ELEMENT_NAME);
        }
        Action actionElement = actionElementV1Builder.buildObject();
        actionElement.setNamespace(actionBean.getActionNamespace());
        actionElement.setContents(actionBean.getContents());
        return actionElement;
    }

    private static DecisionTypeEnumeration transformDecisionType(AuthDecisionStatementBean.Decision decision) {
        DecisionTypeEnumeration decisionTypeEnum = DecisionTypeEnumeration.DENY;
        if (decision.equals((Object)AuthDecisionStatementBean.Decision.PERMIT)) {
            decisionTypeEnum = DecisionTypeEnumeration.PERMIT;
        } else if (decision.equals((Object)AuthDecisionStatementBean.Decision.INDETERMINATE)) {
            decisionTypeEnum = DecisionTypeEnumeration.INDETERMINATE;
        }
        return decisionTypeEnum;
    }

    static {
        builderFactory = XMLObjectProviderRegistrySupport.getBuilderFactory();
    }
}

