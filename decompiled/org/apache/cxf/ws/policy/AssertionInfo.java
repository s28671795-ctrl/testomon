/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy;

import org.apache.neethi.Assertion;

public class AssertionInfo {
    private boolean asserted;
    private final Assertion assertion;
    private String errorMessage;

    public AssertionInfo(Assertion a) {
        this.assertion = a;
    }

    public boolean isAsserted() {
        return this.asserted;
    }

    public void setAsserted(boolean a) {
        this.asserted = a;
    }

    public void setNotAsserted(String message) {
        this.asserted = false;
        this.errorMessage = message;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public Assertion getAssertion() {
        return this.assertion;
    }

    public String toString() {
        return this.assertion.getName() + ":" + this.asserted;
    }
}

