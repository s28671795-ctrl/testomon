/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.stax;

import javax.xml.namespace.QName;
import org.apache.neethi.Policy;

public class OperationPolicy {
    private QName operationName;
    private String operationAction;
    private Policy policy;
    private String soapMessageVersionNamespace;

    public OperationPolicy(QName operationName) {
        this.operationName = operationName;
    }

    public QName getOperationName() {
        return this.operationName;
    }

    public void setOperationName(QName operationName) {
        this.operationName = operationName;
    }

    public String getOperationAction() {
        return this.operationAction;
    }

    public void setOperationAction(String operationAction) {
        this.operationAction = operationAction;
    }

    public Policy getPolicy() {
        return this.policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }

    public String getSoapMessageVersionNamespace() {
        return this.soapMessageVersionNamespace;
    }

    public void setSoapMessageVersionNamespace(String soapMessageVersionNamespace) {
        this.soapMessageVersionNamespace = soapMessageVersionNamespace;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof OperationPolicy)) {
            return false;
        }
        OperationPolicy other = (OperationPolicy)obj;
        return this.getOperationName().equals(other.getOperationName());
    }

    public int hashCode() {
        int hashcode = 17;
        if (this.getOperationName() != null) {
            hashcode *= 31 * this.getOperationName().hashCode();
        }
        return hashcode;
    }
}

