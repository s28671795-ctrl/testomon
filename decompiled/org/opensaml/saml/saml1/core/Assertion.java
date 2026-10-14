/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core;

import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import org.joda.time.DateTime;
import org.opensaml.saml.common.SAMLVersion;
import org.opensaml.saml.common.SignableSAMLObject;
import org.opensaml.saml.saml1.core.Advice;
import org.opensaml.saml.saml1.core.AttributeStatement;
import org.opensaml.saml.saml1.core.AuthenticationStatement;
import org.opensaml.saml.saml1.core.AuthorizationDecisionStatement;
import org.opensaml.saml.saml1.core.Conditions;
import org.opensaml.saml.saml1.core.Evidentiary;
import org.opensaml.saml.saml1.core.Statement;
import org.opensaml.saml.saml1.core.SubjectStatement;

public interface Assertion
extends SignableSAMLObject,
Evidentiary {
    @Nonnull
    @NotEmpty
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Assertion";
    @Nonnull
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "Assertion", "saml1");
    @Nonnull
    @NotEmpty
    public static final String TYPE_LOCAL_NAME = "AssertionType";
    @Nonnull
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AssertionType", "saml1");
    @Nonnull
    @NotEmpty
    public static final String MAJORVERSION_ATTRIB_NAME = "MajorVersion";
    @Nonnull
    @NotEmpty
    public static final String MINORVERSION_ATTRIB_NAME = "MinorVersion";
    @Nonnull
    @NotEmpty
    public static final String ASSERTIONID_ATTRIB_NAME = "AssertionID";
    @Nonnull
    @NotEmpty
    public static final String ISSUER_ATTRIB_NAME = "Issuer";
    @Nonnull
    @NotEmpty
    public static final String ISSUEINSTANT_ATTRIB_NAME = "IssueInstant";
    @Nonnull
    @NotEmpty
    public static final String ID_ATTRIB_NAME = "AssertionID";

    @Nullable
    public String getID();

    public void setID(@Nullable String var1);

    public int getMajorVersion();

    public int getMinorVersion();

    public void setVersion(@Nullable SAMLVersion var1);

    @Nullable
    public String getIssuer();

    public void setIssuer(@Nullable String var1);

    @Nullable
    public DateTime getIssueInstant();

    public void setIssueInstant(@Nullable DateTime var1);

    @Nullable
    public Conditions getConditions();

    public void setConditions(@Nullable Conditions var1);

    @Nullable
    public Advice getAdvice();

    public void setAdvice(@Nullable Advice var1);

    @Nonnull
    @NonnullElements
    public List<Statement> getStatements();

    @Nonnull
    @NonnullElements
    public List<Statement> getStatements(@Nonnull QName var1);

    @Nonnull
    @NonnullElements
    public List<SubjectStatement> getSubjectStatements();

    @Nonnull
    @NonnullElements
    public List<AuthenticationStatement> getAuthenticationStatements();

    @Nonnull
    @NonnullElements
    public List<AuthorizationDecisionStatement> getAuthorizationDecisionStatements();

    @Nonnull
    @NonnullElements
    public List<AttributeStatement> getAttributeStatements();
}

