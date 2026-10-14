/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.validate;

import org.apache.wss4j.binding.wsu10.TimestampType;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.stax.validate.TokenContext;
import org.apache.wss4j.stax.validate.Validator;

public interface TimestampValidator
extends Validator {
    public void validate(TimestampType var1, TokenContext var2) throws WSSecurityException;
}

