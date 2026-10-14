/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.assertion.impl;

import java.security.KeyException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.collection.LazyList;
import net.shibboleth.utilities.java.support.collection.Pair;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.assertion.AssertionValidationException;
import org.opensaml.saml.common.assertion.ValidationContext;
import org.opensaml.saml.common.assertion.ValidationResult;
import org.opensaml.saml.saml2.assertion.impl.AbstractSubjectConfirmationValidator;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.KeyInfoConfirmationDataType;
import org.opensaml.saml.saml2.core.SubjectConfirmation;
import org.opensaml.saml.saml2.core.SubjectConfirmationData;
import org.opensaml.xmlsec.keyinfo.KeyInfoSupport;
import org.opensaml.xmlsec.signature.DEREncodedKeyValue;
import org.opensaml.xmlsec.signature.KeyInfo;
import org.opensaml.xmlsec.signature.KeyValue;
import org.opensaml.xmlsec.signature.X509Certificate;
import org.opensaml.xmlsec.signature.X509Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ThreadSafe
public class HolderOfKeySubjectConfirmationValidator
extends AbstractSubjectConfirmationValidator {
    private Logger log = LoggerFactory.getLogger(HolderOfKeySubjectConfirmationValidator.class);

    @Override
    @Nonnull
    public String getServicedMethod() {
        return "urn:oasis:names:tc:SAML:2.0:cm:holder-of-key";
    }

    @Override
    @Nonnull
    protected ValidationResult doValidate(@Nonnull SubjectConfirmation confirmation, @Nonnull Assertion assertion, @Nonnull ValidationContext context) throws AssertionValidationException {
        if (!Objects.equals(confirmation.getMethod(), "urn:oasis:names:tc:SAML:2.0:cm:holder-of-key")) {
            return ValidationResult.INDETERMINATE;
        }
        this.log.debug("Attempting holder-of-key subject confirmation");
        if (!this.isValidConfirmationDataType(confirmation)) {
            String msg = String.format("Subject confirmation data is not of type '%s'", KeyInfoConfirmationDataType.TYPE_NAME);
            this.log.debug(msg);
            context.setValidationFailureMessage(msg);
            return ValidationResult.INVALID;
        }
        List<KeyInfo> possibleKeys = this.getSubjectConfirmationKeyInformation(confirmation, assertion, context);
        if (possibleKeys.isEmpty()) {
            String msg = String.format("No key information for holder of key subject confirmation in assertion '%s'", assertion.getID());
            this.log.debug(msg);
            context.setValidationFailureMessage(msg);
            return ValidationResult.INVALID;
        }
        Pair<PublicKey, java.security.cert.X509Certificate> keyCertPair = null;
        try {
            keyCertPair = this.getKeyAndCertificate(context);
        }
        catch (IllegalArgumentException e) {
            this.log.warn("Problem with the validation context presenter key/cert params: {}", (Object)e.getMessage());
            context.setValidationFailureMessage("Unable to obtain presenter key/cert params from validation context");
            return ValidationResult.INDETERMINATE;
        }
        if (keyCertPair.getFirst() == null && keyCertPair.getSecond() == null) {
            this.log.debug("Neither the presenter's certificate nor its public key were provided");
            context.setValidationFailureMessage("Neither the presenter's certificate nor its public key were provided");
            return ValidationResult.INDETERMINATE;
        }
        for (KeyInfo keyInfo : possibleKeys) {
            if (this.matchesKeyValue(keyCertPair.getFirst(), keyInfo)) {
                this.log.debug("Successfully matched public key in subject confirmation data to supplied key param");
                context.getDynamicParameters().put("saml2.SubjectConfirmation.HoK.ConfirmedKeyInfo", keyInfo);
                return ValidationResult.VALID;
            }
            if (!this.matchesX509Certificate(keyCertPair.getSecond(), keyInfo)) continue;
            this.log.debug("Successfully matched certificate in subject confirmation data to supplied cert param");
            context.getDynamicParameters().put("saml2.SubjectConfirmation.HoK.ConfirmedKeyInfo", keyInfo);
            return ValidationResult.VALID;
        }
        return ValidationResult.INVALID;
    }

    protected boolean isValidConfirmationDataType(@Nonnull SubjectConfirmation confirmation) throws AssertionValidationException {
        QName confirmationDataSchemaType = confirmation.getSubjectConfirmationData().getSchemaType();
        if (confirmationDataSchemaType != null && !confirmationDataSchemaType.equals(KeyInfoConfirmationDataType.TYPE_NAME)) {
            this.log.debug("SubjectConfirmationData xsi:type was non-null and did not match {}", (Object)KeyInfoConfirmationDataType.TYPE_NAME);
            return false;
        }
        this.log.debug("SubjectConfirmationData xsi:type was either null or matched {}", (Object)KeyInfoConfirmationDataType.TYPE_NAME);
        return true;
    }

    @Nonnull
    protected Pair<PublicKey, java.security.cert.X509Certificate> getKeyAndCertificate(@Nonnull ValidationContext context) throws AssertionValidationException {
        PublicKey presenterKey = null;
        try {
            presenterKey = (PublicKey)context.getStaticParameters().get("saml2.SubjectConfirmation.HoK.PresenterKey");
        }
        catch (ClassCastException e) {
            throw new IllegalArgumentException(String.format("The value of the static validation parameter '%s' was not of the required type '%s'", "saml2.SubjectConfirmation.HoK.PresenterKey", PublicKey.class.getName()));
        }
        java.security.cert.X509Certificate presenterCert = null;
        try {
            presenterCert = (java.security.cert.X509Certificate)context.getStaticParameters().get("saml2.SubjectConfirmation.HoK.PresenterCertificate");
            if (presenterCert != null) {
                if (presenterKey != null) {
                    if (!presenterKey.equals(presenterCert.getPublicKey())) {
                        throw new IllegalArgumentException("Presenter's certificate contains a different public key than the one explicitly given");
                    }
                } else {
                    presenterKey = presenterCert.getPublicKey();
                }
            }
        }
        catch (ClassCastException e) {
            throw new IllegalArgumentException(String.format("The value of the static validation parameter '%s' was not of the required type '%s'", "saml2.SubjectConfirmation.HoK.PresenterCertificate", java.security.cert.X509Certificate.class.getName()));
        }
        return new Pair<PublicKey, java.security.cert.X509Certificate>(presenterKey, presenterCert);
    }

    @Nonnull
    protected List<KeyInfo> getSubjectConfirmationKeyInformation(@Nonnull SubjectConfirmation confirmation, @Nonnull Assertion assertion, @Nonnull ValidationContext context) throws AssertionValidationException {
        SubjectConfirmationData confirmationData = confirmation.getSubjectConfirmationData();
        LazyList<KeyInfo> keyInfos = new LazyList<KeyInfo>();
        for (XMLObject object : confirmationData.getUnknownXMLObjects(KeyInfo.DEFAULT_ELEMENT_NAME)) {
            if (object == null) continue;
            keyInfos.add((KeyInfo)object);
        }
        this.log.debug("Found '{}' KeyInfo children of SubjectConfirmationData", (Object)keyInfos.size());
        return keyInfos;
    }

    protected boolean matchesKeyValue(@Nullable PublicKey key, @Nonnull KeyInfo keyInfo) throws AssertionValidationException {
        if (key == null) {
            this.log.debug("Presenter PublicKey was null, skipping KeyValue match");
            return false;
        }
        if (this.matchesKeyValue(key, keyInfo.getKeyValues())) {
            return true;
        }
        if (this.matchesDEREncodedKeyValue(key, keyInfo.getDEREncodedKeyValues())) {
            return true;
        }
        this.log.debug("Failed to match either a KeyInfo KeyValue or DEREncodedKeyValue against supplied PublicKey param");
        return false;
    }

    protected boolean matchesKeyValue(@Nonnull PublicKey key, @Nullable List<KeyValue> keyValues) {
        if (keyValues == null || keyValues.isEmpty()) {
            this.log.debug("KeyInfo contained no KeyValue children");
            return false;
        }
        this.log.debug("Attempting to match KeyInfo KeyValue to supplied PublicKey param of type: {}", (Object)key.getAlgorithm());
        for (KeyValue keyValue : keyValues) {
            try {
                PublicKey kiPublicKey = KeyInfoSupport.getKey(keyValue);
                if (!Objects.equals(key, kiPublicKey)) continue;
                this.log.debug("Matched KeyValue PublicKey");
                return true;
            }
            catch (KeyException e) {
                this.log.warn("KeyInfo contained KeyValue that can not be parsed", e);
            }
        }
        this.log.debug("Failed to match any KeyValue");
        return false;
    }

    protected boolean matchesDEREncodedKeyValue(@Nonnull PublicKey key, @Nullable List<DEREncodedKeyValue> derEncodedKeyValues) {
        if (derEncodedKeyValues == null || derEncodedKeyValues.isEmpty()) {
            this.log.debug("KeyInfo contained no DEREncodedKeyValue children");
            return false;
        }
        this.log.debug("Attempting to match KeyInfo DEREncodedKeyValue to supplied PublicKey param of type: {}", (Object)key.getAlgorithm());
        for (DEREncodedKeyValue derEncodedKeyValue : derEncodedKeyValues) {
            try {
                PublicKey kiPublicKey = KeyInfoSupport.getKey(derEncodedKeyValue);
                if (!Objects.equals(key, kiPublicKey)) continue;
                this.log.debug("Matched DEREncodedKeyValue PublicKey");
                return true;
            }
            catch (KeyException e) {
                this.log.warn("KeyInfo contained DEREncodedKeyValue that can not be parsed", e);
            }
        }
        this.log.debug("Failed to match any DEREncodedKeyValue");
        return false;
    }

    protected boolean matchesX509Certificate(@Nullable java.security.cert.X509Certificate cert, @Nonnull KeyInfo keyInfo) throws AssertionValidationException {
        if (cert == null) {
            this.log.debug("Presenter X509Certificate was null, skipping certificate match");
            return false;
        }
        List<X509Data> x509Datas = keyInfo.getX509Datas();
        if (x509Datas == null || x509Datas.isEmpty()) {
            this.log.debug("KeyInfo contained no X509Data children, skipping certificate match");
            return false;
        }
        this.log.debug("Attempting to match KeyInfo X509Data to supplied X509Certificate param");
        for (X509Data data : x509Datas) {
            List<X509Certificate> xmlCertificates = data.getX509Certificates();
            if (xmlCertificates == null || xmlCertificates.isEmpty()) {
                this.log.debug("X509Data contained no X509Certificate children, skipping certificate match");
                continue;
            }
            for (X509Certificate xmlCertificate : xmlCertificates) {
                try {
                    java.security.cert.X509Certificate kiCert = KeyInfoSupport.getCertificate(xmlCertificate);
                    if (!Objects.equals(cert, kiCert)) continue;
                    this.log.debug("Matched X509Certificate");
                    return true;
                }
                catch (CertificateException e) {
                    this.log.warn("KeyInfo contained Certificate value that can not be parsed", e);
                }
            }
        }
        this.log.debug("Failed to match a KeyInfo X509Data against supplied X509Certificate param");
        return false;
    }
}

