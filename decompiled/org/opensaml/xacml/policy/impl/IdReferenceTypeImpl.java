/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import org.opensaml.core.xml.schema.impl.XSStringImpl;
import org.opensaml.xacml.policy.IdReferenceType;

public class IdReferenceTypeImpl
extends XSStringImpl
implements IdReferenceType {
    private String earliestVersion;
    private String latestVersion;
    private String version;

    protected IdReferenceTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getEarliestVersion() {
        return this.earliestVersion;
    }

    @Override
    public String getLatestVersion() {
        return this.latestVersion;
    }

    @Override
    public String getVersion() {
        return this.version;
    }

    @Override
    public void setEarliestVersion(String newEarliestVersion) {
        this.earliestVersion = this.prepareForAssignment(this.earliestVersion, newEarliestVersion);
    }

    @Override
    public void setLatestVersion(String newLastestVersion) {
        this.latestVersion = this.prepareForAssignment(this.latestVersion, newLastestVersion);
    }

    @Override
    public void setVersion(String newVersion) {
        this.version = this.prepareForAssignment(this.version, newVersion);
    }
}

