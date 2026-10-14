/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.saml.bean;

import java.util.ArrayList;
import java.util.List;
import org.apache.wss4j.common.saml.bean.ActionBean;
import org.apache.wss4j.common.saml.bean.SubjectBean;

public class AuthDecisionStatementBean {
    private SubjectBean subject;
    private Decision decision;
    private String resource;
    private List<ActionBean> actionBeans;
    private Object evidence;

    public AuthDecisionStatementBean() {
        this.actionBeans = new ArrayList<ActionBean>();
    }

    public AuthDecisionStatementBean(Decision decision, String resource, SubjectBean subject, Object evidence, List<ActionBean> actionBeans) {
        this.decision = decision;
        this.resource = resource;
        this.subject = subject;
        this.evidence = evidence;
        this.actionBeans = actionBeans;
    }

    public String getResource() {
        return this.resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public List<ActionBean> getActions() {
        return this.actionBeans;
    }

    public void setActions(List<ActionBean> actionBeans) {
        this.actionBeans = actionBeans;
    }

    public Decision getDecision() {
        return this.decision;
    }

    public void setDecision(Decision decision) {
        this.decision = decision;
    }

    public Object getEvidence() {
        return this.evidence;
    }

    public void setEvidence(Object evidence) {
        this.evidence = evidence;
    }

    public SubjectBean getSubject() {
        return this.subject;
    }

    public void setSubject(SubjectBean subject) {
        this.subject = subject;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AuthDecisionStatementBean)) {
            return false;
        }
        AuthDecisionStatementBean that = (AuthDecisionStatementBean)o;
        if (this.subject == null && that.subject != null) {
            return false;
        }
        if (this.subject != null && !this.subject.equals(that.subject)) {
            return false;
        }
        if (this.decision == null && that.decision != null) {
            return false;
        }
        if (this.decision != null && !this.decision.equals((Object)that.decision)) {
            return false;
        }
        if (this.evidence == null && that.evidence != null) {
            return false;
        }
        if (this.evidence != null && !this.evidence.equals(that.evidence)) {
            return false;
        }
        if (this.actionBeans == null && that.actionBeans != null) {
            return false;
        }
        if (this.actionBeans != null && !this.actionBeans.equals(that.actionBeans)) {
            return false;
        }
        if (this.resource == null && that.resource != null) {
            return false;
        }
        return this.resource == null || this.resource.equals(that.resource);
    }

    public int hashCode() {
        int result = this.subject != null ? this.subject.hashCode() : 0;
        result = 31 * result + (this.decision != null ? this.decision.hashCode() : 0);
        result = 31 * result + (this.evidence != null ? this.evidence.hashCode() : 0);
        result = 31 * result + (this.actionBeans != null ? this.actionBeans.hashCode() : 0);
        result = 31 * result + (this.resource != null ? this.resource.hashCode() : 0);
        return result;
    }

    public static enum Decision {
        PERMIT,
        INDETERMINATE,
        DENY;

    }
}

