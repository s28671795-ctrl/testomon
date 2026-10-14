/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core;

import java.util.List;
import org.joda.time.DateTime;
import org.opensaml.saml.common.SAMLVersion;
import org.opensaml.saml.common.SignableSAMLObject;
import org.opensaml.saml.saml1.core.RespondWith;

public interface RequestAbstractType
extends SignableSAMLObject {
    public static final String MAJORVERSION_ATTRIB_NAME = "MajorVersion";
    public static final String MINORVERSION_ATTRIB_NAME = "MinorVersion";
    public static final String ISSUEINSTANT_ATTRIB_NAME = "IssueInstant";
    public static final String ID_ATTRIB_NAME = "RequestID";

    public SAMLVersion getVersion();

    public void setVersion(SAMLVersion var1);

    public DateTime getIssueInstant();

    public void setIssueInstant(DateTime var1);

    public String getID();

    public void setID(String var1);

    public List<RespondWith> getRespondWiths();
}

