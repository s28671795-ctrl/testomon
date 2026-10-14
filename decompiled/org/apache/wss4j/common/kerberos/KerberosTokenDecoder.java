/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.kerberos;

import javax.security.auth.Subject;
import org.apache.wss4j.common.kerberos.KerberosTokenDecoderException;

public interface KerberosTokenDecoder {
    public void setToken(byte[] var1);

    public void setSubject(Subject var1);

    public byte[] getSessionKey() throws KerberosTokenDecoderException;

    public void clear();
}

