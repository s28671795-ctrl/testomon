/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.net;

import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.net.URIException;

public interface URIComparator {
    public boolean compare(@Nullable String var1, @Nullable String var2) throws URIException;
}

