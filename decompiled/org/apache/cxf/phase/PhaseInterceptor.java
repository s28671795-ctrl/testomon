/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.phase;

import java.util.Collection;
import java.util.Set;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.message.Message;

public interface PhaseInterceptor<T extends Message>
extends Interceptor<T> {
    public Set<String> getAfter();

    public Set<String> getBefore();

    public String getId();

    public String getPhase();

    public Collection<PhaseInterceptor<? extends Message>> getAdditionalInterceptors();
}

