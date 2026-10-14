/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.saml.builder;

import java.util.ArrayList;
import java.util.Collections;
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
import org.apache.wss4j.common.saml.bean.DelegateBean;
import org.apache.wss4j.common.saml.bean.KeyInfoBean;
import org.apache.wss4j.common.saml.bean.NameIDBean;
import org.apache.wss4j.common.saml.bean.ProxyRestrictionBean;
import org.apache.wss4j.common.saml.bean.SubjectBean;
import org.apache.wss4j.common.saml.bean.SubjectConfirmationDataBean;
import org.apache.wss4j.common.saml.bean.SubjectLocalityBean;
import org.apache.wss4j.common.saml.builder.SAML1ComponentBuilder;
import org.apache.xml.security.stax.impl.util.IDGenerator;
import org.joda.time.DateTime;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.XMLObjectBuilderFactory;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.schema.XSString;
import org.opensaml.core.xml.schema.impl.XSStringBuilder;
import org.opensaml.saml.common.SAMLObjectBuilder;
import org.opensaml.saml.common.SAMLVersion;
import org.opensaml.saml.ext.saml2delrestrict.Delegate;
import org.opensaml.saml.ext.saml2delrestrict.DelegationRestrictionType;
import org.opensaml.saml.saml2.core.Action;
import org.opensaml.saml.saml2.core.Advice;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.AssertionIDRef;
import org.opensaml.saml.saml2.core.AssertionURIRef;
import org.opensaml.saml.saml2.core.Attribute;
import org.opensaml.saml.saml2.core.AttributeStatement;
import org.opensaml.saml.saml2.core.AttributeValue;
import org.opensaml.saml.saml2.core.Audience;
import org.opensaml.saml.saml2.core.AudienceRestriction;
import org.opensaml.saml.saml2.core.AuthnContext;
import org.opensaml.saml.saml2.core.AuthnContextClassRef;
import org.opensaml.saml.saml2.core.AuthnStatement;
import org.opensaml.saml.saml2.core.AuthzDecisionStatement;
import org.opensaml.saml.saml2.core.Conditions;
import org.opensaml.saml.saml2.core.DecisionTypeEnumeration;
import org.opensaml.saml.saml2.core.Evidence;
import org.opensaml.saml.saml2.core.Issuer;
import org.opensaml.saml.saml2.core.KeyInfoConfirmationDataType;
import org.opensaml.saml.saml2.core.NameID;
import org.opensaml.saml.saml2.core.OneTimeUse;
import org.opensaml.saml.saml2.core.ProxyRestriction;
import org.opensaml.saml.saml2.core.Subject;
import org.opensaml.saml.saml2.core.SubjectConfirmation;
import org.opensaml.saml.saml2.core.SubjectConfirmationData;
import org.opensaml.saml.saml2.core.SubjectLocality;
import org.opensaml.security.SecurityException;
import org.opensaml.xmlsec.signature.KeyInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public final class SAML2ComponentBuilder {
    private static final transient Logger LOG = LoggerFactory.getLogger(SAML2ComponentBuilder.class);
    private static volatile SAMLObjectBuilder<Assertion> assertionBuilder;
    private static volatile SAMLObjectBuilder<Issuer> issuerBuilder;
    private static volatile SAMLObjectBuilder<Subject> subjectBuilder;
    private static volatile SAMLObjectBuilder<NameID> nameIdBuilder;
    private static volatile SAMLObjectBuilder<SubjectConfirmation> subjectConfirmationBuilder;
    private static volatile SAMLObjectBuilder<OneTimeUse> oneTimeUseBuilder;
    private static volatile SAMLObjectBuilder<ProxyRestriction> proxyRestrictionBuilder;
    private static volatile SAMLObjectBuilder<Conditions> conditionsBuilder;
    private static volatile SAMLObjectBuilder<Advice> adviceBuilder;
    private static volatile SAMLObjectBuilder<AssertionIDRef> assertionIDRefBuilder;
    private static volatile SAMLObjectBuilder<AssertionURIRef> assertionURIRefBuilder;
    private static volatile SAMLObjectBuilder<SubjectConfirmationData> subjectConfirmationDataBuilder;
    private static volatile SAMLObjectBuilder<KeyInfoConfirmationDataType> keyInfoConfirmationDataBuilder;
    private static volatile SAMLObjectBuilder<AuthnStatement> authnStatementBuilder;
    private static volatile SAMLObjectBuilder<AuthnContext> authnContextBuilder;
    private static volatile SAMLObjectBuilder<AuthnContextClassRef> authnContextClassRefBuilder;
    private static volatile SAMLObjectBuilder<AttributeStatement> attributeStatementBuilder;
    private static volatile SAMLObjectBuilder<Attribute> attributeBuilder;
    private static volatile XSStringBuilder stringBuilder;
    private static volatile SAMLObjectBuilder<AudienceRestriction> audienceRestrictionBuilder;
    private static volatile SAMLObjectBuilder<DelegationRestrictionType> delegationRestrictionBuilder;
    private static volatile SAMLObjectBuilder<Audience> audienceBuilder;
    private static volatile SAMLObjectBuilder<Delegate> delegateBuilder;
    private static volatile SAMLObjectBuilder<AuthzDecisionStatement> authorizationDecisionStatementBuilder;
    private static volatile SAMLObjectBuilder<Action> actionElementBuilder;
    private static volatile XMLObjectBuilderFactory builderFactory;
    private static volatile SAMLObjectBuilder<SubjectLocality> subjectLocalityBuilder;

    private SAML2ComponentBuilder() {
    }

    public static Assertion createAssertion() {
        if (assertionBuilder == null && (assertionBuilder = (SAMLObjectBuilder)builderFactory.getBuilder(Assertion.DEFAULT_ELEMENT_NAME)) == null) {
            throw new IllegalStateException("OpenSaml engine not initialized. Please make sure to initialize the OpenSaml engine prior using it");
        }
        Assertion assertion = (Assertion)assertionBuilder.buildObject(Assertion.DEFAULT_ELEMENT_NAME, Assertion.TYPE_NAME);
        assertion.setID(IDGenerator.generateID("_"));
        assertion.setVersion(SAMLVersion.VERSION_20);
        assertion.setIssueInstant(new DateTime());
        return assertion;
    }

    public static Issuer createIssuer(String issuerValue, String issuerFormat, String issuerQualifier) {
        if (issuerBuilder == null) {
            issuerBuilder = (SAMLObjectBuilder)builderFactory.getBuilder(Issuer.DEFAULT_ELEMENT_NAME);
        }
        Issuer issuer = issuerBuilder.buildObject();
        issuer.setValue(issuerValue);
        issuer.setFormat(issuerFormat);
        issuer.setNameQualifier(issuerQualifier);
        return issuer;
    }

    public static Conditions createConditions(ConditionsBean conditionsBean) {
        if (conditionsBuilder == null) {
            conditionsBuilder = (SAMLObjectBuilder)builderFactory.getBuilder(Conditions.DEFAULT_ELEMENT_NAME);
        }
        Conditions conditions = conditionsBuilder.buildObject();
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
                AudienceRestriction audienceRestriction = SAML2ComponentBuilder.createAudienceRestriction(audienceRestrictionBean);
                conditions.getAudienceRestrictions().add(audienceRestriction);
            }
        }
        if (conditionsBean.isOneTimeUse()) {
            conditions.getConditions().add(SAML2ComponentBuilder.createOneTimeUse());
        }
        if (conditionsBean.getProxyRestriction() != null) {
            conditions.getConditions().add(SAML2ComponentBuilder.createProxyRestriction(conditionsBean.getProxyRestriction()));
        }
        if (conditionsBean.getDelegates() != null && !conditionsBean.getDelegates().isEmpty()) {
            DelegationRestrictionType delegationRestriction = SAML2ComponentBuilder.createDelegationRestriction(conditionsBean.getDelegates());
            conditions.getConditions().add(delegationRestriction);
        }
        return conditions;
    }

    public static Advice createAdvice(AdviceBean adviceBean) throws WSSecurityException {
        if (adviceBuilder == null) {
            adviceBuilder = (SAMLObjectBuilder)builderFactory.getBuilder(Advice.DEFAULT_ELEMENT_NAME);
        }
        Advice advice = adviceBuilder.buildObject();
        if (!adviceBean.getIdReferences().isEmpty()) {
            if (assertionIDRefBuilder == null) {
                assertionIDRefBuilder = (SAMLObjectBuilder)builderFactory.getBuilder(AssertionIDRef.DEFAULT_ELEMENT_NAME);
            }
            for (String ref : adviceBean.getIdReferences()) {
                AssertionIDRef assertionIdRef = assertionIDRefBuilder.buildObject();
                assertionIdRef.setAssertionID(ref);
                advice.getAssertionIDReferences().add(assertionIdRef);
            }
        }
        if (!adviceBean.getUriReferences().isEmpty()) {
            if (assertionURIRefBuilder == null) {
                assertionURIRefBuilder = (SAMLObjectBuilder)builderFactory.getBuilder(AssertionURIRef.DEFAULT_ELEMENT_NAME);
            }
            for (String ref : adviceBean.getUriReferences()) {
                AssertionURIRef assertionURIRef = assertionURIRefBuilder.buildObject();
                assertionURIRef.setAssertionURI(ref);
                advice.getAssertionURIReferences().add(assertionURIRef);
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

    public static AudienceRestriction createAudienceRestriction(AudienceRestrictionBean audienceRestrictionBean) {
        if (audienceRestrictionBuilder == null) {
            audienceRestrictionBuilder = (SAMLObjectBuilder)builderFactory.getBuilder(AudienceRestriction.DEFAULT_ELEMENT_NAME);
        }
        if (audienceBuilder == null) {
            audienceBuilder = (SAMLObjectBuilder)builderFactory.getBuilder(Audience.DEFAULT_ELEMENT_NAME);
        }
        AudienceRestriction audienceRestriction = audienceRestrictionBuilder.buildObject();
        for (String audienceURI : audienceRestrictionBean.getAudienceURIs()) {
            Audience audience = audienceBuilder.buildObject();
            audience.setAudienceURI(audienceURI);
            audienceRestriction.getAudiences().add(audience);
        }
        return audienceRestriction;
    }

    public static DelegationRestrictionType createDelegationRestriction(List<DelegateBean> delegates) {
        if (delegationRestrictionBuilder == null) {
            delegationRestrictionBuilder = (SAMLObjectBuilder)builderFactory.getBuilder(DelegationRestrictionType.TYPE_NAME);
        }
        DelegationRestrictionType delegationRestriction = delegationRestrictionBuilder.buildObject();
        if (delegateBuilder == null) {
            delegateBuilder = (SAMLObjectBuilder)builderFactory.getBuilder(Delegate.DEFAULT_ELEMENT_NAME);
        }
        for (DelegateBean delegateBean : delegates) {
            Delegate delegate = delegateBuilder.buildObject();
            delegate.setConfirmationMethod(delegateBean.getConfirmationMethod());
            delegate.setDelegationInstant(delegateBean.getDelegationInstant());
            if (delegateBean.getNameIDBean() == null) {
                throw new IllegalStateException("The value of NameIDBean in DelegateBean may not be null");
            }
            NameID nameID = SAML2ComponentBuilder.createNameID(delegateBean.getNameIDBean());
            delegate.setNameID(nameID);
            delegationRestriction.getDelegates().add(delegate);
        }
        return delegationRestriction;
    }

    public static OneTimeUse createOneTimeUse() {
        if (oneTimeUseBuilder == null) {
            oneTimeUseBuilder = (SAMLObjectBuilder)builderFactory.getBuilder(OneTimeUse.DEFAULT_ELEMENT_NAME);
        }
        return oneTimeUseBuilder.buildObject();
    }

    public static ProxyRestriction createProxyRestriction(ProxyRestrictionBean proxyRestrictionBean) {
        if (proxyRestrictionBuilder == null) {
            proxyRestrictionBuilder = (SAMLObjectBuilder)builderFactory.getBuilder(ProxyRestriction.DEFAULT_ELEMENT_NAME);
        }
        ProxyRestriction proxyRestriction = proxyRestrictionBuilder.buildObject();
        if (proxyRestrictionBean.getCount() > 0) {
            proxyRestriction.setProxyCount(proxyRestrictionBean.getCount());
        }
        if (!proxyRestrictionBean.getAudienceURIs().isEmpty()) {
            if (audienceBuilder == null) {
                audienceBuilder = (SAMLObjectBuilder)builderFactory.getBuilder(Audience.DEFAULT_ELEMENT_NAME);
            }
            for (String audienceURI : proxyRestrictionBean.getAudienceURIs()) {
                Audience audience = audienceBuilder.buildObject();
                audience.setAudienceURI(audienceURI);
                proxyRestriction.getAudiences().add(audience);
            }
        }
        return proxyRestriction;
    }

    public static List<AuthnStatement> createAuthnStatement(List<AuthenticationStatementBean> authBeans) {
        ArrayList<AuthnStatement> authnStatements = new ArrayList<AuthnStatement>();
        if (authnStatementBuilder == null) {
            authnStatementBuilder = (SAMLObjectBuilder)builderFactory.getBuilder(AuthnStatement.DEFAULT_ELEMENT_NAME);
        }
        if (authnContextBuilder == null) {
            authnContextBuilder = (SAMLObjectBuilder)builderFactory.getBuilder(AuthnContext.DEFAULT_ELEMENT_NAME);
        }
        if (authnContextClassRefBuilder == null) {
            authnContextClassRefBuilder = (SAMLObjectBuilder)builderFactory.getBuilder(AuthnContextClassRef.DEFAULT_ELEMENT_NAME);
        }
        if (subjectLocalityBuilder == null) {
            subjectLocalityBuilder = (SAMLObjectBuilder)builderFactory.getBuilder(SubjectLocality.DEFAULT_ELEMENT_NAME);
        }
        if (authBeans != null && !authBeans.isEmpty()) {
            for (AuthenticationStatementBean statementBean : authBeans) {
                AuthnStatement authnStatement = authnStatementBuilder.buildObject();
                DateTime authInstant = statementBean.getAuthenticationInstant();
                if (authInstant == null) {
                    authInstant = new DateTime();
                }
                authnStatement.setAuthnInstant(authInstant);
                DateTime sessionNotOnOrAfter = statementBean.getSessionNotOnOrAfter();
                if (sessionNotOnOrAfter != null) {
                    authnStatement.setSessionNotOnOrAfter(sessionNotOnOrAfter);
                }
                if (statementBean.getSessionIndex() != null) {
                    authnStatement.setSessionIndex(statementBean.getSessionIndex());
                }
                AuthnContextClassRef authnContextClassRef = authnContextClassRefBuilder.buildObject();
                authnContextClassRef.setAuthnContextClassRef(SAML2ComponentBuilder.transformAuthenticationMethod(statementBean.getAuthenticationMethod()));
                AuthnContext authnContext = authnContextBuilder.buildObject();
                authnContext.setAuthnContextClassRef(authnContextClassRef);
                authnStatement.setAuthnContext(authnContext);
                SubjectLocalityBean subjectLocalityBean = statementBean.getSubjectLocality();
                if (subjectLocalityBean != null) {
                    SubjectLocality subjectLocality = subjectLocalityBuilder.buildObject();
                    subjectLocality.setDNSName(subjectLocalityBean.getDnsAddress());
                    subjectLocality.setAddress(subjectLocalityBean.getIpAddress());
                    authnStatement.setSubjectLocality(subjectLocality);
                }
                authnStatements.add(authnStatement);
            }
        }
        return authnStatements;
    }

    private static String transformAuthenticationMethod(String sourceMethod) {
        String transformedMethod = "";
        if ("Password".equalsIgnoreCase(sourceMethod)) {
            transformedMethod = "urn:oasis:names:tc:SAML:2.0:ac:classes:Password";
        } else if (sourceMethod != null && !"".equals(sourceMethod)) {
            return sourceMethod;
        }
        return transformedMethod;
    }

    public static Attribute createAttribute(String friendlyName, String name, String nameFormat, List<Object> values2) {
        if (stringBuilder == null) {
            stringBuilder = (XSStringBuilder)builderFactory.getBuilder(XSString.TYPE_NAME);
        }
        Attribute attribute = SAML2ComponentBuilder.createAttribute(friendlyName, name, nameFormat);
        for (Object value : values2) {
            if (value instanceof String) {
                XSString attributeValue = (XSString)stringBuilder.buildObject(AttributeValue.DEFAULT_ELEMENT_NAME, XSString.TYPE_NAME);
                attributeValue.setValue((String)value);
                attribute.getAttributeValues().add(attributeValue);
                continue;
            }
            if (!(value instanceof XMLObject)) continue;
            attribute.getAttributeValues().add((XMLObject)value);
        }
        return attribute;
    }

    public static Subject createSaml2Subject(SubjectBean subjectBean) throws SecurityException, WSSecurityException {
        String confirmationMethodStr;
        if (subjectBuilder == null) {
            subjectBuilder = (SAMLObjectBuilder)builderFactory.getBuilder(Subject.DEFAULT_ELEMENT_NAME);
        }
        Subject subject = subjectBuilder.buildObject();
        NameID nameID = SAML2ComponentBuilder.createNameID(subjectBean);
        subject.setNameID(nameID);
        SubjectConfirmationData subjectConfData = null;
        if (subjectBean.getKeyInfo() != null || subjectBean.getSubjectConfirmationData() != null) {
            subjectConfData = SAML2ComponentBuilder.createSubjectConfirmationData(subjectBean.getSubjectConfirmationData(), subjectBean.getKeyInfo());
        }
        NameID subjectConfNameId = null;
        if (subjectBean.getSubjectConfirmationNameID() != null) {
            subjectConfNameId = SAML2ComponentBuilder.createNameID(subjectBean.getSubjectConfirmationNameID());
        }
        if ((confirmationMethodStr = subjectBean.getSubjectConfirmationMethod()) == null) {
            confirmationMethodStr = "urn:oasis:names:tc:SAML:2.0:cm:sender-vouches";
        }
        SubjectConfirmation subjectConfirmation = SAML2ComponentBuilder.createSubjectConfirmation(confirmationMethodStr, subjectConfData, subjectConfNameId);
        subject.getSubjectConfirmations().add(subjectConfirmation);
        return subject;
    }

    public static SubjectConfirmationData createSubjectConfirmationData(SubjectConfirmationDataBean subjectConfirmationDataBean, KeyInfoBean keyInfoBean) throws SecurityException, WSSecurityException {
        SubjectConfirmationData subjectConfirmationData = null;
        KeyInfo keyInfo = null;
        if (keyInfoBean == null) {
            if (subjectConfirmationDataBuilder == null) {
                subjectConfirmationDataBuilder = (SAMLObjectBuilder)builderFactory.getBuilder(SubjectConfirmationData.DEFAULT_ELEMENT_NAME);
            }
            subjectConfirmationData = subjectConfirmationDataBuilder.buildObject();
        } else {
            if (keyInfoConfirmationDataBuilder == null) {
                keyInfoConfirmationDataBuilder = (SAMLObjectBuilder)builderFactory.getBuilder(KeyInfoConfirmationDataType.TYPE_NAME);
            }
            subjectConfirmationData = keyInfoConfirmationDataBuilder.buildObject();
            keyInfo = SAML1ComponentBuilder.createKeyInfo(keyInfoBean);
            ((KeyInfoConfirmationDataType)subjectConfirmationData).getKeyInfos().add(keyInfo);
        }
        if (subjectConfirmationDataBean != null) {
            if (subjectConfirmationDataBean.getInResponseTo() != null) {
                subjectConfirmationData.setInResponseTo(subjectConfirmationDataBean.getInResponseTo());
            }
            if (subjectConfirmationDataBean.getRecipient() != null) {
                subjectConfirmationData.setRecipient(subjectConfirmationDataBean.getRecipient());
            }
            if (subjectConfirmationDataBean.getAddress() != null) {
                subjectConfirmationData.setAddress(subjectConfirmationDataBean.getAddress());
            }
            if (subjectConfirmationDataBean.getNotAfter() != null) {
                subjectConfirmationData.setNotOnOrAfter(subjectConfirmationDataBean.getNotAfter());
            }
            if (subjectConfirmationDataBean.getNotBefore() != null) {
                subjectConfirmationData.setNotBefore(subjectConfirmationDataBean.getNotBefore());
            }
            if (subjectConfirmationDataBean.getAny() != null) {
                List<XMLObject> unknownObjects = subjectConfirmationData.getUnknownXMLObjects();
                for (Object obj : subjectConfirmationDataBean.getAny()) {
                    if (obj == null) {
                        LOG.warn("Ignore <null> object in SubjectConfirmationData.any");
                        continue;
                    }
                    if (obj instanceof XMLObject) {
                        unknownObjects.add((XMLObject)obj);
                        continue;
                    }
                    if (obj instanceof AttributeStatementBean) {
                        unknownObjects.addAll(SAML2ComponentBuilder.createAttributeStatement(Collections.singletonList((AttributeStatementBean)obj)));
                        continue;
                    }
                    LOG.warn("Ignore object of the unsupported type {} in SubjectConfirmationData.any", (Object)obj.getClass());
                }
            }
        }
        return subjectConfirmationData;
    }

    public static SubjectConfirmation createSubjectConfirmation(String method2, SubjectConfirmationData subjectConfirmationData, NameID subjectConfirmationNameId) {
        if (subjectConfirmationBuilder == null) {
            subjectConfirmationBuilder = (SAMLObjectBuilder)builderFactory.getBuilder(SubjectConfirmation.DEFAULT_ELEMENT_NAME);
        }
        SubjectConfirmation subjectConfirmation = subjectConfirmationBuilder.buildObject();
        subjectConfirmation.setMethod(method2);
        subjectConfirmation.setSubjectConfirmationData(subjectConfirmationData);
        subjectConfirmation.setNameID(subjectConfirmationNameId);
        return subjectConfirmation;
    }

    public static NameID createNameID(SubjectBean subject) {
        NameIDBean nameIDBean = new NameIDBean();
        nameIDBean.setNameIDFormat(subject.getSubjectNameIDFormat());
        nameIDBean.setNameQualifier(subject.getSubjectNameQualifier());
        nameIDBean.setSPNameQualifier(subject.getSubjectNameSPNameQualifier());
        nameIDBean.setSPProvidedID(subject.getSubjectNameSPProvidedID());
        nameIDBean.setNameValue(subject.getSubjectName());
        return SAML2ComponentBuilder.createNameID(nameIDBean);
    }

    public static NameID createNameID(NameIDBean nameIDBean) {
        if (nameIdBuilder == null) {
            nameIdBuilder = (SAMLObjectBuilder)builderFactory.getBuilder(NameID.DEFAULT_ELEMENT_NAME);
        }
        NameID nameID = nameIdBuilder.buildObject();
        nameID.setNameQualifier(nameIDBean.getNameQualifier());
        nameID.setFormat(nameIDBean.getNameIDFormat());
        nameID.setValue(nameIDBean.getNameValue());
        nameID.setSPNameQualifier(nameIDBean.getSPNameQualifier());
        nameID.setSPProvidedID(nameIDBean.getSPProvidedID());
        return nameID;
    }

    public static List<AttributeStatement> createAttributeStatement(List<AttributeStatementBean> attributeData) {
        ArrayList<AttributeStatement> attributeStatements = new ArrayList<AttributeStatement>();
        if (attributeStatementBuilder == null) {
            attributeStatementBuilder = (SAMLObjectBuilder)builderFactory.getBuilder(AttributeStatement.DEFAULT_ELEMENT_NAME);
        }
        if (attributeData != null && !attributeData.isEmpty()) {
            for (AttributeStatementBean statementBean : attributeData) {
                AttributeStatement attributeStatement = attributeStatementBuilder.buildObject();
                for (AttributeBean values2 : statementBean.getSamlAttributes()) {
                    List<Object> attributeValues = values2.getAttributeValues();
                    Attribute samlAttribute = SAML2ComponentBuilder.createAttribute(values2.getSimpleName(), values2.getQualifiedName(), values2.getNameFormat(), attributeValues);
                    attributeStatement.getAttributes().add(samlAttribute);
                }
                attributeStatements.add(attributeStatement);
            }
        }
        return attributeStatements;
    }

    public static Attribute createAttribute(String friendlyName, String name, String nameFormat) {
        if (attributeBuilder == null) {
            attributeBuilder = (SAMLObjectBuilder)builderFactory.getBuilder(Attribute.DEFAULT_ELEMENT_NAME);
        }
        Attribute attribute = attributeBuilder.buildObject();
        attribute.setFriendlyName(friendlyName);
        if (nameFormat == null) {
            attribute.setNameFormat("urn:oasis:names:tc:SAML:2.0:attrname-format:uri");
        } else {
            attribute.setNameFormat(nameFormat);
        }
        attribute.setName(name);
        return attribute;
    }

    public static List<AuthzDecisionStatement> createAuthorizationDecisionStatement(List<AuthDecisionStatementBean> decisionData) {
        ArrayList<AuthzDecisionStatement> authDecisionStatements = new ArrayList<AuthzDecisionStatement>();
        if (authorizationDecisionStatementBuilder == null) {
            authorizationDecisionStatementBuilder = (SAMLObjectBuilder)builderFactory.getBuilder(AuthzDecisionStatement.DEFAULT_ELEMENT_NAME);
        }
        if (decisionData != null && !decisionData.isEmpty()) {
            for (AuthDecisionStatementBean decisionStatementBean : decisionData) {
                AuthzDecisionStatement authDecision = authorizationDecisionStatementBuilder.buildObject();
                authDecision.setResource(decisionStatementBean.getResource());
                authDecision.setDecision(SAML2ComponentBuilder.transformDecisionType(decisionStatementBean.getDecision()));
                for (ActionBean actionBean : decisionStatementBean.getActions()) {
                    Action actionElement = SAML2ComponentBuilder.createSamlAction(actionBean);
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

    public static Action createSamlAction(ActionBean actionBean) {
        if (actionElementBuilder == null) {
            actionElementBuilder = (SAMLObjectBuilder)builderFactory.getBuilder(Action.DEFAULT_ELEMENT_NAME);
        }
        Action actionElement = actionElementBuilder.buildObject();
        actionElement.setNamespace(actionBean.getActionNamespace());
        if (actionBean.getActionNamespace() == null) {
            actionElement.setNamespace("urn:oasis:names:tc:SAML:1.0:action:rwedc-negation");
        }
        actionElement.setAction(actionBean.getContents());
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

