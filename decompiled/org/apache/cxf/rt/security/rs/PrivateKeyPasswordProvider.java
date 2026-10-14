/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.rt.security.rs;

import java.util.Properties;

public interface PrivateKeyPasswordProvider {
    public char[] getPassword(Properties var1);
}

