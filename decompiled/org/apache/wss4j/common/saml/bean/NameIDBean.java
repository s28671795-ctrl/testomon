/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.saml.bean;

public class NameIDBean {
    private String nameValue;
    private String nameIDFormat = "urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified";
    private String nameQualifier;
    private String spNameQualifier;
    private String spProvidedID;

    public NameIDBean() {
    }

    public NameIDBean(String nameValue, String nameQualifier, String nameIDFormat) {
        this.setNameValue(nameValue);
        this.setNameQualifier(nameQualifier);
        this.setNameIDFormat(nameIDFormat);
    }

    public String getNameValue() {
        return this.nameValue;
    }

    public void setNameValue(String nameValue) {
        this.nameValue = nameValue;
    }

    public String getNameIDFormat() {
        return this.nameIDFormat;
    }

    public void setNameIDFormat(String nameIDFormat) {
        this.nameIDFormat = nameIDFormat;
    }

    public String getNameQualifier() {
        return this.nameQualifier;
    }

    public void setNameQualifier(String nameQualifier) {
        this.nameQualifier = nameQualifier;
    }

    public String getSPNameQualifier() {
        return this.spNameQualifier;
    }

    public void setSPNameQualifier(String spNameQualifier) {
        this.spNameQualifier = spNameQualifier;
    }

    public String getSPProvidedID() {
        return this.spProvidedID;
    }

    public void setSPProvidedID(String spProvidedID) {
        this.spProvidedID = spProvidedID;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NameIDBean)) {
            return false;
        }
        NameIDBean that = (NameIDBean)o;
        if (this.nameValue == null && that.nameValue != null) {
            return false;
        }
        if (this.nameValue != null && !this.nameValue.equals(that.nameValue)) {
            return false;
        }
        if (this.nameIDFormat == null && that.nameIDFormat != null) {
            return false;
        }
        if (this.nameIDFormat != null && !this.nameIDFormat.equals(that.nameIDFormat)) {
            return false;
        }
        if (this.nameQualifier == null && that.nameQualifier != null) {
            return false;
        }
        if (this.nameQualifier != null && !this.nameQualifier.equals(that.nameQualifier)) {
            return false;
        }
        if (this.spNameQualifier == null && that.spNameQualifier != null) {
            return false;
        }
        if (this.spNameQualifier != null && !this.spNameQualifier.equals(that.spNameQualifier)) {
            return false;
        }
        if (this.spProvidedID == null && that.spProvidedID != null) {
            return false;
        }
        return this.spProvidedID == null || this.spProvidedID.equals(that.spProvidedID);
    }

    public int hashCode() {
        int result = 0;
        if (this.nameValue != null) {
            result = this.nameValue.hashCode();
        }
        if (this.nameIDFormat != null) {
            result = 31 * result + this.nameIDFormat.hashCode();
        }
        if (this.nameQualifier != null) {
            result = 31 * result + this.nameQualifier.hashCode();
        }
        if (this.spNameQualifier != null) {
            result = 31 * result + this.spNameQualifier.hashCode();
        }
        if (this.spProvidedID != null) {
            result = 31 * result + this.spProvidedID.hashCode();
        }
        return result;
    }
}

