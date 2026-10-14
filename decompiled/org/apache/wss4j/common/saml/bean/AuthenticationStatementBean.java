/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.saml.bean;

import org.apache.wss4j.common.saml.bean.SubjectBean;
import org.apache.wss4j.common.saml.bean.SubjectLocalityBean;
import org.joda.time.DateTime;

public class AuthenticationStatementBean {
    private SubjectBean subject;
    private DateTime sessionNotOnOrAfter;
    private DateTime authenticationInstant;
    private String authenticationMethod;
    private SubjectLocalityBean subjectLocality;
    private String sessionIndex;

    public AuthenticationStatementBean() {
    }

    public AuthenticationStatementBean(SubjectBean subject, String authenticationMethod, DateTime authenticationInstant, DateTime sessionNotOnOrAfter) {
        this.subject = subject;
        this.authenticationMethod = authenticationMethod;
        this.authenticationInstant = authenticationInstant;
        this.sessionNotOnOrAfter = sessionNotOnOrAfter;
    }

    public SubjectBean getSubject() {
        return this.subject;
    }

    public void setSubject(SubjectBean subject) {
        this.subject = subject;
    }

    public String getAuthenticationMethod() {
        return this.authenticationMethod;
    }

    public void setAuthenticationMethod(String authenticationMethod) {
        this.authenticationMethod = authenticationMethod;
    }

    public DateTime getAuthenticationInstant() {
        return this.authenticationInstant;
    }

    public void setAuthenticationInstant(DateTime authenticationInstant) {
        this.authenticationInstant = authenticationInstant;
    }

    public DateTime getSessionNotOnOrAfter() {
        return this.sessionNotOnOrAfter;
    }

    public void setSessionNotOnOrAfter(DateTime sessionNotOnOrAfter) {
        this.sessionNotOnOrAfter = sessionNotOnOrAfter;
    }

    public final SubjectLocalityBean getSubjectLocality() {
        return this.subjectLocality;
    }

    public final void setSubjectLocality(SubjectLocalityBean subjectLocality) {
        this.subjectLocality = subjectLocality;
    }

    public final String getSessionIndex() {
        return this.sessionIndex;
    }

    public final void setSessionIndex(String sessionIndex) {
        this.sessionIndex = sessionIndex;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AuthenticationStatementBean)) {
            return false;
        }
        AuthenticationStatementBean that = (AuthenticationStatementBean)o;
        if (this.authenticationInstant == null && that.authenticationInstant != null) {
            return false;
        }
        if (this.authenticationInstant != null && !this.authenticationInstant.equals(that.authenticationInstant)) {
            return false;
        }
        if (this.sessionNotOnOrAfter == null && that.sessionNotOnOrAfter != null) {
            return false;
        }
        if (this.sessionNotOnOrAfter != null && !this.sessionNotOnOrAfter.equals(that.sessionNotOnOrAfter)) {
            return false;
        }
        if (this.authenticationMethod == null && that.authenticationMethod != null) {
            return false;
        }
        if (this.authenticationMethod != null && !this.authenticationMethod.equals(that.authenticationMethod)) {
            return false;
        }
        if (this.subject == null && that.subject != null) {
            return false;
        }
        if (this.subject != null && !this.subject.equals(that.subject)) {
            return false;
        }
        if (this.subjectLocality == null && that.subjectLocality != null) {
            return false;
        }
        if (this.subjectLocality != null && !this.subjectLocality.equals(that.subjectLocality)) {
            return false;
        }
        if (this.sessionIndex == null && that.sessionIndex != null) {
            return false;
        }
        return this.sessionIndex == null || this.sessionIndex.equals(that.sessionIndex);
    }

    public int hashCode() {
        int result = this.subject != null ? this.subject.hashCode() : 0;
        result = 31 * result + (this.authenticationInstant != null ? this.authenticationInstant.hashCode() : 0);
        result = 31 * result + (this.sessionNotOnOrAfter != null ? this.sessionNotOnOrAfter.hashCode() : 0);
        result = 31 * result + (this.authenticationMethod != null ? this.authenticationMethod.hashCode() : 0);
        result = 31 * result + (this.subjectLocality != null ? this.subjectLocality.hashCode() : 0);
        result = 31 * result + (this.sessionIndex != null ? this.sessionIndex.hashCode() : 0);
        return result;
    }
}

