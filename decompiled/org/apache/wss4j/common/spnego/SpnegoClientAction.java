/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.spnego;

import java.security.PrivilegedAction;
import org.ietf.jgss.GSSContext;

public interface SpnegoClientAction
extends PrivilegedAction<byte[]> {
    public void setMutualAuth(boolean var1);

    public void setServiceName(String var1);

    public void setUserNameServiceForm(boolean var1);

    @Override
    public byte[] run();

    public GSSContext getContext();
}

