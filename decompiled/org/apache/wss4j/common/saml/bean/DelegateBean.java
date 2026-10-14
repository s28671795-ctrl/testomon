/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.saml.bean;

import java.time.Instant;
import java.util.Date;
import org.apache.wss4j.common.saml.bean.NameIDBean;
import org.joda.time.DateTime;

public class DelegateBean {
    private DateTime delegationInstant;
    private String confirmationMethod;
    private NameIDBean nameIDBean;

    public DateTime getDelegationInstant() {
        return this.delegationInstant;
    }

    public void setDelegationInstant(DateTime delegationInstant) {
        this.delegationInstant = delegationInstant;
    }

    public void setDelegationInstant(Instant delegationInstant) {
        this.delegationInstant = delegationInstant != null ? new DateTime(Date.from(delegationInstant)) : null;
    }

    public String getConfirmationMethod() {
        return this.confirmationMethod;
    }

    public void setConfirmationMethod(String confirmationMethod) {
        this.confirmationMethod = confirmationMethod;
    }

    public NameIDBean getNameIDBean() {
        return this.nameIDBean;
    }

    public void setNameIDBean(NameIDBean nameIDBean) {
        this.nameIDBean = nameIDBean;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DelegateBean)) {
            return false;
        }
        DelegateBean that = (DelegateBean)o;
        if (this.delegationInstant == null && that.delegationInstant != null) {
            return false;
        }
        if (this.delegationInstant != null && !this.delegationInstant.equals(that.delegationInstant)) {
            return false;
        }
        if (this.confirmationMethod == null && that.confirmationMethod != null) {
            return false;
        }
        if (this.confirmationMethod != null && !this.confirmationMethod.equals(that.confirmationMethod)) {
            return false;
        }
        if (this.nameIDBean == null && that.nameIDBean != null) {
            return false;
        }
        return this.nameIDBean == null || this.nameIDBean.equals(that.nameIDBean);
    }

    public int hashCode() {
        int result = 0;
        if (this.delegationInstant != null) {
            result = 31 * result + this.delegationInstant.hashCode();
        }
        if (this.confirmationMethod != null) {
            result = 31 * result + this.confirmationMethod.hashCode();
        }
        if (this.nameIDBean != null) {
            result = 31 * result + this.nameIDBean.hashCode();
        }
        return result;
    }
}

