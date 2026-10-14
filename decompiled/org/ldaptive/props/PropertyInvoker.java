/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.props;

import java.util.Set;

public interface PropertyInvoker {
    public void setProperty(Object var1, String var2, String var3);

    public boolean hasProperty(String var1);

    public Set<String> getProperties();
}

