/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.spnego;

import java.security.PrivilegedAction;
import org.ietf.jgss.GSSContext;

public interface SpnegoServiceAction
extends PrivilegedAction<byte[]> {
    public void setTicket(byte[] var1);

    public void setServiceName(String var1);

    public void setUsernameServiceNameForm(boolean var1);

    @Override
    public byte[] run();

    public GSSContext getContext();
}

