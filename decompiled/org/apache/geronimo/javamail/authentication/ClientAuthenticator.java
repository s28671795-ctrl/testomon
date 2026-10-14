/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.authentication;

import javax.mail.MessagingException;

public interface ClientAuthenticator {
    public byte[] evaluateChallenge(byte[] var1) throws MessagingException;

    public boolean hasInitialResponse();

    public boolean isComplete();

    public String getMechanismName();
}

