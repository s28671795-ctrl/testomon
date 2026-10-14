/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.saml.bean;

import org.apache.wss4j.common.saml.bean.KeyInfoBean;
import org.apache.wss4j.common.saml.bean.NameIDBean;
import org.apache.wss4j.common.saml.bean.SubjectConfirmationDataBean;

public class SubjectBean {
    private final NameIDBean nameID = new NameIDBean();
    private String subjectConfirmationMethod;
    private KeyInfoBean keyInfo;
    private SubjectConfirmationDataBean subjectConfirmationData;
    private NameIDBean subjectConfirmationNameID;

    public SubjectBean() {
    }

    public SubjectBean(String subjectName, String subjectNameQualifier, String subjectConfirmationMethod) {
        this.nameID.setNameValue(subjectName);
        this.nameID.setNameQualifier(subjectNameQualifier);
        this.subjectConfirmationMethod = subjectConfirmationMethod;
    }

    public SubjectBean(String subjectName, String subjectNameQualifier, String subjectConfirmationMethod, String subjectNameIDFormat) {
        this(subjectName, subjectNameQualifier, subjectConfirmationMethod);
        this.nameID.setNameIDFormat(subjectNameIDFormat);
    }

    public SubjectBean(String subjectName, String subjectNameQualifier, String subjectConfirmationMethod, String subjectNameIDFormat, String subjectNameSPProvidedID) {
        this(subjectName, subjectNameQualifier, subjectConfirmationMethod, subjectNameIDFormat);
        this.nameID.setSPProvidedID(subjectNameSPProvidedID);
    }

    public String getSubjectName() {
        return this.nameID.getNameValue();
    }

    public void setSubjectName(String subjectName) {
        this.nameID.setNameValue(subjectName);
    }

    public String getSubjectNameQualifier() {
        return this.nameID.getNameQualifier();
    }

    public void setSubjectNameQualifier(String subjectNameQualifier) {
        this.nameID.setNameQualifier(subjectNameQualifier);
    }

    public String getSubjectConfirmationMethod() {
        return this.subjectConfirmationMethod;
    }

    public void setSubjectConfirmationMethod(String subjectConfirmationMethod) {
        this.subjectConfirmationMethod = subjectConfirmationMethod;
    }

    public String getSubjectNameIDFormat() {
        return this.nameID.getNameIDFormat();
    }

    public void setSubjectNameIDFormat(String subjectNameIDFormat) {
        this.nameID.setNameIDFormat(subjectNameIDFormat);
    }

    public String getSubjectNameSPNameQualifier() {
        return this.nameID.getSPNameQualifier();
    }

    public void setSubjectNameSPNameQualifier(String subjectNameSPNameQualifier) {
        this.nameID.setSPNameQualifier(subjectNameSPNameQualifier);
    }

    public String getSubjectNameSPProvidedID() {
        return this.nameID.getSPProvidedID();
    }

    public void setSubjectNameSPProvidedID(String subjectNameSPProvidedID) {
        this.nameID.setSPProvidedID(subjectNameSPProvidedID);
    }

    public KeyInfoBean getKeyInfo() {
        return this.keyInfo;
    }

    public void setKeyInfo(KeyInfoBean keyInfo) {
        this.keyInfo = keyInfo;
    }

    public SubjectConfirmationDataBean getSubjectConfirmationData() {
        return this.subjectConfirmationData;
    }

    public void setSubjectConfirmationData(SubjectConfirmationDataBean subjectConfirmationData) {
        this.subjectConfirmationData = subjectConfirmationData;
    }

    public NameIDBean getSubjectConfirmationNameID() {
        return this.subjectConfirmationNameID;
    }

    public void setSubjectConfirmationNameID(NameIDBean subjectConfirmationNameID) {
        this.subjectConfirmationNameID = subjectConfirmationNameID;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubjectBean)) {
            return false;
        }
        SubjectBean that = (SubjectBean)o;
        if (!this.nameID.equals(that.nameID)) {
            return false;
        }
        if (this.subjectConfirmationMethod == null && that.subjectConfirmationMethod != null) {
            return false;
        }
        if (this.subjectConfirmationMethod != null && !this.subjectConfirmationMethod.equals(that.subjectConfirmationMethod)) {
            return false;
        }
        if (this.keyInfo == null && that.keyInfo != null) {
            return false;
        }
        if (this.keyInfo != null && !this.keyInfo.equals(that.keyInfo)) {
            return false;
        }
        if (this.subjectConfirmationData == null && that.subjectConfirmationData != null) {
            return false;
        }
        if (this.subjectConfirmationData != null && !this.subjectConfirmationData.equals(that.subjectConfirmationData)) {
            return false;
        }
        if (this.subjectConfirmationNameID == null && that.subjectConfirmationNameID != null) {
            return false;
        }
        return this.subjectConfirmationNameID == null || this.subjectConfirmationNameID.equals(that.subjectConfirmationNameID);
    }

    public int hashCode() {
        int result = this.nameID.hashCode();
        if (this.subjectConfirmationMethod != null) {
            result = 31 * result + this.subjectConfirmationMethod.hashCode();
        }
        if (this.keyInfo != null) {
            result = 31 * result + this.keyInfo.hashCode();
        }
        if (this.subjectConfirmationData != null) {
            result = 31 * result + this.subjectConfirmationData.hashCode();
        }
        if (this.subjectConfirmationNameID != null) {
            result = 31 * result + this.subjectConfirmationNameID.hashCode();
        }
        return result;
    }
}

