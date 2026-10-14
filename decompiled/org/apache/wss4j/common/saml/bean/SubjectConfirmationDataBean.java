/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.saml.bean;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.joda.time.DateTime;

public class SubjectConfirmationDataBean {
    private String recipient;
    private String address;
    private String inResponseTo;
    private DateTime notBefore;
    private DateTime notAfter;
    private List<Object> any;

    public String getRecipient() {
        return this.recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInResponseTo() {
        return this.inResponseTo;
    }

    public void setInResponseTo(String inResponseTo) {
        this.inResponseTo = inResponseTo;
    }

    public DateTime getNotBefore() {
        return this.notBefore;
    }

    public void setNotBefore(DateTime notBefore) {
        this.notBefore = notBefore;
    }

    public void setNotBefore(Instant notBefore) {
        this.notBefore = notBefore != null ? new DateTime(Date.from(notBefore)) : null;
    }

    public DateTime getNotAfter() {
        return this.notAfter;
    }

    public void setNotAfter(DateTime notAfter) {
        this.notAfter = notAfter;
    }

    public void setNotAfter(Instant notAfter) {
        this.notAfter = notAfter != null ? new DateTime(Date.from(notAfter)) : null;
    }

    public List<Object> getAny() {
        return this.any;
    }

    public void setAny(List<Object> any) {
        this.any = any;
    }

    public void addAny(Object obj) {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        this.any.add(obj);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubjectConfirmationDataBean)) {
            return false;
        }
        SubjectConfirmationDataBean that = (SubjectConfirmationDataBean)o;
        if (this.recipient == null && that.recipient != null) {
            return false;
        }
        if (this.recipient != null && !this.recipient.equals(that.recipient)) {
            return false;
        }
        if (this.address == null && that.address != null) {
            return false;
        }
        if (this.address != null && !this.address.equals(that.address)) {
            return false;
        }
        if (this.inResponseTo == null && that.inResponseTo != null) {
            return false;
        }
        if (this.inResponseTo != null && !this.inResponseTo.equals(that.inResponseTo)) {
            return false;
        }
        if (this.notBefore == null && that.notBefore != null) {
            return false;
        }
        if (this.notBefore != null && !this.notBefore.equals(that.notBefore)) {
            return false;
        }
        if (this.notAfter == null && that.notAfter != null) {
            return false;
        }
        if (this.notAfter != null && !this.notAfter.equals(that.notAfter)) {
            return false;
        }
        if (this.any == null && that.any != null) {
            return false;
        }
        return this.any == null || this.any.equals(that.any);
    }

    public int hashCode() {
        int result = 0;
        if (this.recipient != null) {
            result = this.recipient.hashCode();
        }
        if (this.address != null) {
            result = 31 * result + this.address.hashCode();
        }
        if (this.inResponseTo != null) {
            result = 31 * result + this.inResponseTo.hashCode();
        }
        if (this.notBefore != null) {
            result = 31 * result + this.notBefore.hashCode();
        }
        if (this.notAfter != null) {
            result = 31 * result + this.notAfter.hashCode();
        }
        if (this.any != null) {
            result = 31 * result + this.any.hashCode();
        }
        return result;
    }
}

