/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.saml.bean;

import java.util.ArrayList;
import java.util.List;
import org.apache.wss4j.common.saml.bean.AttributeBean;
import org.apache.wss4j.common.saml.bean.SubjectBean;

public class AttributeStatementBean {
    private SubjectBean subject;
    private List<AttributeBean> attributeBeans;

    public AttributeStatementBean() {
        this.attributeBeans = new ArrayList<AttributeBean>();
    }

    public AttributeStatementBean(SubjectBean subject, List<AttributeBean> attributeBeans) {
        this.subject = subject;
        this.attributeBeans = attributeBeans;
    }

    public List<AttributeBean> getSamlAttributes() {
        return this.attributeBeans;
    }

    public void setSamlAttributes(List<AttributeBean> attributeBeans) {
        this.attributeBeans = attributeBeans;
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
        if (!(o instanceof AttributeStatementBean)) {
            return false;
        }
        AttributeStatementBean that = (AttributeStatementBean)o;
        if (this.attributeBeans == null && that.attributeBeans != null) {
            return false;
        }
        if (this.attributeBeans != null && !this.attributeBeans.equals(that.attributeBeans)) {
            return false;
        }
        if (this.subject == null && that.subject != null) {
            return false;
        }
        return this.subject == null || this.subject.equals(that.subject);
    }

    public int hashCode() {
        int result = this.subject != null ? this.subject.hashCode() : 0;
        result = 31 * result + (this.attributeBeans != null ? this.attributeBeans.hashCode() : 0);
        return result;
    }
}

