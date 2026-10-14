/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.validate;

import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.validate.Credential;

public interface Validator {
    public Credential validate(Credential var1, RequestData var2) throws WSSecurityException;
}

