/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.saml.bean;

import java.util.ArrayList;
import java.util.List;

public class AttributeBean {
    private String simpleName;
    private String qualifiedName;
    private String nameFormat;
    private List<Object> attributeValues;

    public AttributeBean() {
        this.attributeValues = new ArrayList<Object>();
    }

    public AttributeBean(String simpleName, String qualifiedName, List<Object> attributeValues) {
        this.simpleName = simpleName;
        this.qualifiedName = qualifiedName;
        this.attributeValues = attributeValues;
    }

    public String getSimpleName() {
        return this.simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    public String getNameFormat() {
        return this.nameFormat;
    }

    public void setNameFormat(String nameFormat) {
        this.nameFormat = nameFormat;
    }

    public String getQualifiedName() {
        return this.qualifiedName;
    }

    public void setQualifiedName(String qualifiedName) {
        this.qualifiedName = qualifiedName;
    }

    public List<Object> getAttributeValues() {
        return this.attributeValues;
    }

    public void setAttributeValues(List<Object> attributeValues) {
        this.attributeValues = attributeValues;
    }

    public void addAttributeValue(Object attributeValue) {
        if (this.attributeValues == null) {
            this.attributeValues = new ArrayList<Object>();
        }
        this.attributeValues.add(attributeValue);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttributeBean)) {
            return false;
        }
        AttributeBean that = (AttributeBean)o;
        if (this.attributeValues == null && that.attributeValues != null) {
            return false;
        }
        if (this.attributeValues != null && !this.attributeValues.equals(that.attributeValues)) {
            return false;
        }
        if (this.qualifiedName == null && that.qualifiedName != null) {
            return false;
        }
        if (this.qualifiedName != null && !this.qualifiedName.equals(that.qualifiedName)) {
            return false;
        }
        if (this.nameFormat == null && that.nameFormat != null) {
            return false;
        }
        if (this.nameFormat != null && !this.nameFormat.equals(that.nameFormat)) {
            return false;
        }
        if (this.simpleName == null && that.simpleName != null) {
            return false;
        }
        return this.simpleName == null || this.simpleName.equals(that.simpleName);
    }

    public int hashCode() {
        int result = 0;
        if (this.simpleName != null) {
            result = 31 * result + this.simpleName.hashCode();
        }
        if (this.qualifiedName != null) {
            result = 31 * result + this.qualifiedName.hashCode();
        }
        if (this.nameFormat != null) {
            result = 31 * result + this.nameFormat.hashCode();
        }
        if (this.attributeValues != null) {
            result = 31 * result + this.attributeValues.hashCode();
        }
        return result;
    }
}

