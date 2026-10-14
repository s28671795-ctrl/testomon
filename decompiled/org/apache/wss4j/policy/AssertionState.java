/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy;

import org.apache.neethi.Assertion;

public class AssertionState {
    private State state = State.INIT;
    private boolean asserted;
    private boolean logged;
    private Assertion assertion;
    private StringBuilder errorMessage = new StringBuilder();

    public AssertionState(Assertion assertion, boolean initialAssertionState) {
        this.assertion = assertion;
        this.asserted = initialAssertionState;
    }

    public Assertion getAssertion() {
        return this.assertion;
    }

    public boolean isHardFailure() {
        return this.state == State.HARD_FAILURE;
    }

    public synchronized void setAsserted(boolean asserted) {
        if (this.state == State.HARD_FAILURE) {
            return;
        }
        if (!asserted) {
            this.state = State.HARD_FAILURE;
        }
        this.asserted = asserted;
    }

    public boolean isAsserted() {
        return this.asserted;
    }

    public void setErrorMessage(String errorMessage) {
        if (this.errorMessage.length() > 0) {
            this.errorMessage.append("\n");
        }
        this.errorMessage.append(errorMessage);
    }

    public String getErrorMessage() {
        if (this.errorMessage.length() == 0) {
            return "Assertion " + this.assertion.getName() + " not satisfied";
        }
        return this.errorMessage.toString();
    }

    protected void clearErrorMessage() {
        this.errorMessage.delete(0, this.errorMessage.length());
    }

    public boolean isLogged() {
        return this.logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public static enum State {
        INIT,
        HARD_FAILURE;

    }
}

