/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core;

import org.joda.time.DateTime;
import org.opensaml.saml.common.SAMLVersion;
import org.opensaml.saml.common.SignableSAMLObject;

public interface ResponseAbstractType
extends SignableSAMLObject {
    public static final String INRESPONSETO_ATTRIB_NAME = "InResponseTo";
    public static final String MAJORVERSION_ATTRIB_NAME = "MajorVersion";
    public static final String MINORVERSION_ATTRIB_NAME = "MinorVersion";
    public static final String ISSUEINSTANT_ATTRIB_NAME = "IssueInstant";
    public static final String RECIPIENT_ATTRIB_NAME = "Recipient";
    public static final String ID_ATTRIB_NAME = "ResponseID";

    public String getInResponseTo();

    public void setInResponseTo(String var1);

    public String getID();

    public void setID(String var1);

    public SAMLVersion getVersion();

    public void setVersion(SAMLVersion var1);

    public DateTime getIssueInstant();

    public void setIssueInstant(DateTime var1);

    public String getRecipient();

    public void setRecipient(String var1);
}

