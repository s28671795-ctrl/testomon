/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.databinding;

import java.util.List;
import org.apache.cxf.interceptor.Fault;

public interface WrapperHelper {
    public Object createWrapperObject(List<?> var1) throws Fault;

    public List<Object> getWrapperParts(Object var1) throws Fault;

    public String getSignature();
}

