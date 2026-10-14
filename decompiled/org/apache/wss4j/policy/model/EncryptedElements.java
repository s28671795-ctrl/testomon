/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.model;

import java.util.List;
import javax.xml.namespace.QName;
import org.apache.neethi.Policy;
import org.apache.wss4j.policy.SPConstants;
import org.apache.wss4j.policy.model.AbstractSecurityAssertion;
import org.apache.wss4j.policy.model.RequiredElements;
import org.apache.wss4j.policy.model.XPath;

public class EncryptedElements
extends RequiredElements {
    public EncryptedElements(SPConstants.SPVersion version, String xPathVersion, List<XPath> xPaths) {
        super(version, xPathVersion, xPaths);
    }

    @Override
    public QName getName() {
        return this.getVersion().getSPConstants().getEncryptedElements();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof EncryptedElements)) {
            return false;
        }
        return super.equals(object);
    }

    @Override
    public int hashCode() {
        int result = 17;
        return 31 * result + super.hashCode();
    }

    @Override
    protected AbstractSecurityAssertion cloneAssertion(Policy nestedPolicy) {
        return new EncryptedElements(this.getVersion(), this.getXPathVersion(), this.getXPaths());
    }
}

